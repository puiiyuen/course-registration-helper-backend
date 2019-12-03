/**
 * User Service
 *
 * @author Peiyuan
 * 2019-01-21
 */

package com.hci.user;

import com.hci.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * Login
     *
     * @param userId   User's Id
     * @param password User's password
     * @return Operation status
     */

    public Map<String, Object> login(String userId, String password) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (userMapper.isExist(userId) == null) {
                result.put("status", operationStatus.FAILED);
                result.put("userId", null);
                result.put("message", "User does not exist, please sign up a new account");
                return result;
            }
            String encryptedPassword = shaEncryption.passwordEncryption(password);
            String existUser = userMapper.existUser(userId, encryptedPassword);
            if (existUser != null) {
                result.put("userId", userId);
                result.put("status", operationStatus.SUCCESSFUL);
                result.put("message", "OK");
            } else {
                result.put("status", operationStatus.FAILED);
                result.put("userId", null);
                result.put("message", "Please check your A-Number and password if they are correct.");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("userId", null);
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            return result;
        }

    }


    public User getUserById(String userId) {
        try {
            return userMapper.getUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object createAccount(String userId, String nickname, String password, String major, String degree) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            String encryptedPassword = shaEncryption.passwordEncryption(password);

            if (userMapper.isExist(userId) != null) {
                result.put("userId", null);
                result.put("status", operationStatus.FAILED);
                result.put("message", "User existed, please use another A-Number");
                return result;
            }

            if (userMapper.createUser(userId, encryptedPassword) == 1 &&
                    userMapper.createAccount(userId, nickname, major, degree) == 1) {
                result.put("status", operationStatus.SUCCESSFUL);
                result.put("userId", userId);
                result.put("message", "OK");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                result.put("userId", null);
                result.put("status", operationStatus.FAILED);
                result.put("message", "Failed to sign up, please try again.");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            result.put("userId", null);
            result.put("status", operationStatus.SERVERERROR);
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            return result;
        }

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object createAccount(String userId, String nickname, String password, String degree) {
        return createAccount(userId, nickname, password, "undeclared", degree);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int changeSettings(int op, String oldValue, String newValue, String userId) {
        try {
            if (op == 1) {
                if (userMapper.changeNickname(newValue, userId) != 1) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                    return 1;
                }
            } else if (op == 2) {
                String encryptedOldPassword = shaEncryption.passwordEncryption(oldValue);
                String encryptedNewPassword = shaEncryption.passwordEncryption(newValue);
                if (userMapper.existUser(userId, encryptedOldPassword)!=null) {
                    if (userMapper.changePassword(encryptedNewPassword, userId) == 1) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    return 2;
                }
            } else if (op == 3) {
                if (userMapper.changeMajor(newValue, userId) != 1) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                    return 1;
                }
            } else if (op == 4) {
                if (userMapper.changeDegree(newValue, userId) != 1) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                    return 1;
                }
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            return 1;
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int changeSettings(int op, String newValue, String userId) {
        return changeSettings(op, "-", newValue, userId);
    }

}

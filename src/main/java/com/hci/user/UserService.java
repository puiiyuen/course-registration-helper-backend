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
            if (userMapper.isExsit(userId) == null) {
                result.put("status", operationStatus.FAILED);
                result.put("userId", null);
                return result;
            }
            String encryptedPassword = shaEncryption.passwordEncryption(password);
            User existUser = userMapper.existUser(userId, encryptedPassword);
            if (existUser != null) {
                result.put("userId", userId);
                result.put("status", operationStatus.SUCCESSFUL);
            } else {
                result.put("status", operationStatus.FAILED);
                result.put("userId", null);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("userId", null);
            return result;
        }

    }


//    public User getUserById(String userId) {
//        try {
//            return userMapper.getUserById(userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object createAccount(String userId, String nickname, String password, String major) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            if (userMapper.createUser(userId, password) == 1 &&
                    userMapper.createAccount(userId, nickname, major) == 1) {
                result.put("status",operationStatus.SUCCESSFUL);
                result.put("userId",userId);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                result.put("userId",null);
                result.put("status",operationStatus.FAILED);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            result.put("userId",null);
            result.put("status",operationStatus.SERVERERROR);
            return result;
        }

    }

//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
//    public Object modifyContact(String userId, String phone, String email) {
//        try {
//            if (userMapper.modifyContact(userId, phone, email) == 1) {
//                return operationStatus.SUCCESSFUL;
//            } else {
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
//                return operationStatus.FAILED;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
//            return operationStatus.SERVERERROR;
//        }
//    }

}

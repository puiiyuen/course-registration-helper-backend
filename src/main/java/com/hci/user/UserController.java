
/**
 * User Controller
 *
 * @author Peiyuan
 * 2019-11-16
 */

package com.hci.user;

import com.hci.courses.CoursesService;
import com.hci.utils.DevMode;
import com.hci.utils.OperationMessage;
import com.hci.utils.SessionCheck;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CoursesService coursesService;

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, Object> param, HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            String userId = param.get("userId").toString();
            String password = param.get("password").toString();
            boolean rememberMe = param.get("rememberMe") != null && (boolean) param.get("rememberMe");

            Map<String, Object> toLogin = userService.login(userId, password);

            if (toLogin.get("status").toString().equals(operationStatus.SUCCESSFUL)) {
                session.setAttribute("userId", userId);
            }

            if (rememberMe) {
                session.setMaxInactiveInterval(7 * 24 * 3600);
            }

            return toLogin;
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

    @PostMapping("/sign-up")
    public Object signUp(@RequestBody Map<String, Object> param) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (param.get("major") == null) {
                return userService.createAccount(param.get("userId").toString(), param.get("nickname").toString(),
                        param.get("password").toString(), param.get("degree").toString());
            } else {
                return userService.createAccount(param.get("userId").toString(), param.get("nickname").toString(),
                        param.get("password").toString(), param.get("major").toString(), param.get("degree").toString());
            }
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


    @GetMapping("/sign-up-major")
    public Object signUpMajor() {
        HashMap<String, Object> result = new HashMap<>();
        try {
            return coursesService.getDegreeMajor();
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("degreeMajor", "Cannot get degree & major list");
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            return result;
        }
    }


    @GetMapping("/online")
    public Object sessionCheck(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (session.getAttribute("userId") != null) {
                result.put("status", operationStatus.SUCCESSFUL);
                result.put("userId", session.getAttribute("userId").toString());
                result.put("message", session.getAttribute("userId").toString() + " is online");
            } else {
                result.put("status", operationStatus.FAILED);
                result.put("userId", null);
                result.put("message", "offline");
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

    @GetMapping("/user")
    public Object getOnlineUser(HttpSession session) {
        User fetch;
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)) {
                fetch = userService.getUserById(session.getAttribute("userId").toString());
                if (fetch == null) {
                    result.put("status", operationStatus.FAILED);
                    result.put("user", null);
                    result.put("message", "Cannot find the user, please check the user ID");
                }
                result.put("status", operationStatus.SUCCESSFUL);
                result.put("user", fetch);
                result.put("message", "OK");
            } else {
                result.put("status", operationStatus.FAILED);
                result.put("user", null);
                result.put("message", "User have not logged in. OR Session is timeout. Please log in");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("user", null);
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            String userId = session.getAttribute("userId").toString();
            session.invalidate();
            result.put("status", operationStatus.SUCCESSFUL);
            result.put("userId", userId);
            result.put("message", userId + " is logged out");
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


    @PostMapping("/settings")
    public Object changeSettings(@RequestBody Map<String, Object> param, HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        List<String> opStatusList = new ArrayList<>();
        try {
            if (SessionCheck.isOnline(session)) {
                String userId = session.getAttribute("userId").toString();
                if (param.get("nickname") != null) {
                    if (userService.changeSettings(1, param.get("nickname").toString(),userId) == 0) {
                        opStatusList.add("nickname change success");
                    } else {
                        opStatusList.add("nickname change success");
                    }
                }
                if (param.get("oldPassword") != null
                        && param.get("newPassword") != null) {
                    int changePass = userService.changeSettings(2, param.get("oldPassword").toString(),
                            param.get("newPassword").toString(),userId);
                    if (changePass == 0) {
                        opStatusList.add("password change success");
                    } else if (changePass == 2) {
                        opStatusList.add("old password does not match");
                    } else {
                        opStatusList.add("password change fail");
                    }
                }
                if (param.get("major") != null) {
                    if (userService.changeSettings(3, param.get("major").toString(),userId) == 0) {
                        opStatusList.add("major change success");
                    } else {
                        opStatusList.add("major change fail");
                    }
                }
                if (param.get("degree") != null) {
                    if (userService.changeSettings(4, param.get("degree").toString(),userId) == 0) {
                        opStatusList.add("degree change success");
                    } else {
                        opStatusList.add("degree change fail");
                    }
                }
                result.put("message", opStatusList);
                result.put("status", operationStatus.SUCCESSFUL);
            } else {
                result.put("message", OperationMessage.OFFLINE);
                result.put("status", operationStatus.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("userId", null);
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }

}

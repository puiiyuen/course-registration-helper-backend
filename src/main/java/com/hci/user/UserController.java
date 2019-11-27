
/**
 * User Controller
 *
 * @author Peiyuan
 * 2019-11-16
 */

package com.hci.user;

import com.hci.utils.DevMode;
import com.hci.utils.SessionCheck;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

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
                if (fetch == null){
                    result.put("status",operationStatus.FAILED);
                    result.put("user",null);
                    result.put("message","Cannot find the user, please check the user ID");
                }
                result.put("status",operationStatus.SUCCESSFUL);
                result.put("user",fetch);
                result.put("message","OK");
            } else {
                result.put("status",operationStatus.FAILED);
                result.put("user",null);
                result.put("message","User have not logged in. OR Session is timeout. Please log in");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",operationStatus.SERVERERROR);
            result.put("user",null);
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
}

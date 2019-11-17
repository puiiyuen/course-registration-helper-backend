
/**
 * User Controller
 *
 * @author Peiyuan
 * 2019-11-16
 */

package com.hci.user;

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

            Map<String, Object> toLogin = userService.login(userId, password);

            if (toLogin.get("status").toString().equals(operationStatus.SUCCESSFUL)) {
                session.setAttribute("userId", userId);
            }
            return toLogin;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("userId", null);
            return result;
        }
    }

    @PostMapping("/sign-up")
    public Object signUp(@RequestBody Map<String, Object> param) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            return userService.createAccount(param.get("userId").toString(), param.get("nickname").toString(),
                    param.get("password").toString(), param.get("major").toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("userId", null);
            return result;
        }
    }


//    @GetMapping("/session")
//    public Object sessionCheck(HttpSession session) {
//        try {
//            if (session.getAttribute("userId") != null) {
//                return operationStatus.IS_EXIST;
//            } else {
//                return operationStatus.NOT_EXIST;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return operationStatus.SERVERERROR;
//        }
//    }
//
//    @GetMapping("/user")
//    public User getOnlineUser(HttpSession session) {
//        User fetch = new User();
//        try {
//            if (session.getAttribute("userId") != null) {
//                fetch = userService.getUserById(Integer.parseInt(session.getAttribute("userId").toString()));
//                return fetch;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fetch.defaultUser();
//    }

    @GetMapping("/logout")
    public Object logout(HttpSession session) {
        try {
            session.invalidate();
            return operationStatus.SUCCESSFUL;
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR;
        }
    }
}

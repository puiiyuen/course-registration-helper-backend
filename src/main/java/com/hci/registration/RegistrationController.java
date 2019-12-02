package com.hci.registration;

import com.hci.utils.DevMode;
import com.hci.utils.OperationMessage;
import com.hci.utils.SessionCheck;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/info")
    public Object getBasicInfo(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)){
                String userId = session.getAttribute("userId").toString();
                result.put("basicGradeInfo",registrationService.getBasicInfo(userId));
                result.put("status",operationStatus.SUCCESSFUL);
                result.put("message",OperationMessage.OK);
            } else {
                result.put("message", OperationMessage.OFFLINE);
                result.put("status", operationStatus.FAILED);
                result.put("basicGradeInfo","Cannot get basic grade info");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            result.put("status", operationStatus.SERVERERROR);
            result.put("basicGradeInfo","Cannot get basic grade info");
        }
        return result;
    }

    @GetMapping("/list")
    public Object getCourseRegistrationList(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)) {
                String userId = session.getAttribute("userId").toString();
                result.put("courses", registrationService.getCourseRegistrationList(userId));
                result.put("message", OperationMessage.OK);
                result.put("status", operationStatus.SUCCESSFUL);
            } else {
                result.put("message", OperationMessage.OFFLINE);
                result.put("status", operationStatus.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("courses", "Cannot get course registration list");
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }

    @PostMapping("/add")
    public Object addCourses(@RequestBody Map<String, Object> param, HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)) {
                String userId = session.getAttribute("userId").toString();
                List<String> toAddCourses = (List<String>) param.get("courses");
                if (registrationService.addCourses(userId, toAddCourses)) {
                    result.put("message", OperationMessage.OK + " " + toAddCourses.size() + " courses added");
                    result.put("status", operationStatus.SUCCESSFUL);
                } else {
                    result.put("message", "Cannot add new courses: There is no course OR course has been already added");
                    result.put("status", operationStatus.FAILED);
                }
            } else {
                result.put("message", OperationMessage.OFFLINE);
                result.put("status", operationStatus.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }
}

package com.hci.registration;

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
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/info")
    public Object getBasicInfo(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)) {
                String userId = session.getAttribute("userId").toString();
                result.put("basicGradeInfo", registrationService.getBasicInfo(userId));
                result.put("status", operationStatus.SUCCESSFUL);
                result.put("message", OperationMessage.OK);
            } else {
                result.put("message", OperationMessage.OFFLINE);
                result.put("status", operationStatus.FAILED);
                result.put("basicGradeInfo", "Cannot get basic grade info");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            result.put("status", operationStatus.SERVERERROR);
            result.put("basicGradeInfo", "Cannot get basic grade info");
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

    @PostMapping("")
    public Object coursesRegistration(@RequestBody Map<String, Object> param, HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)) {
                String userId = session.getAttribute("userId").toString();
                List<Map<String, Object>> courses = (List<Map<String, Object>>) param.get("courses");
                List<String> opStatusList = new ArrayList<>();
                for (Map<String, Object> course : courses) {
                    int operation = course.get("op") == null ? 0 : (int) course.get("op");
                    String courseId = course.get("courseId").toString();
                    if (operation == 0) {
                        //add
                        if (registrationService.addCourses(userId, courseId)) {
                            opStatusList.add(courseId + ": add operation success");
                        } else {
                            opStatusList.add(courseId + ": add operation fail");
                        }
                    } else if (operation == 1) {
                        //in-progress
                        if (registrationService.completeCourses(userId, courseId, "-")) {
                            opStatusList.add(courseId + ": in-progress operation success");
                        } else {
                            opStatusList.add(courseId + ": in-progress operation fail");
                        }
                    } else if (operation == 2) {
                        //pass
                        if (registrationService.completeCourses(userId, courseId,
                                course.get("letterGrade").toString())) {
                            opStatusList.add(courseId + ": passed operation success");
                        } else {
                            opStatusList.add(courseId + ": passed operation fail");
                        }
                    } else if (operation == 3) {
                        //failed
                        if (registrationService.completeCourses(userId, courseId, "F")) {
                            opStatusList.add(courseId + ": fail operation success");
                        } else {
                            opStatusList.add(courseId + ": fail operation fail");
                        }
                    } else if (operation == 4) {
                        //drop
                        if (registrationService.dropCourses(userId, courseId)) {
                            opStatusList.add(courseId + ": drop operation success");
                        } else {
                            opStatusList.add(courseId + ": drop operation fail");
                        }
                    } else {
                        //cannot do
                        opStatusList.add(courseId + ": wrong operation code");
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
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }


}

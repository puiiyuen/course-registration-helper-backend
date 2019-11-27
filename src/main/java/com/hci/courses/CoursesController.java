package com.hci.courses;

import com.hci.utils.DevMode;
import com.hci.utils.SessionCheck;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/course")
public class CoursesController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping("/list")
    public Object getCourseList(HttpSession session){

        HashMap<String,Object> finalResult = new HashMap<>();
        try {
            if (SessionCheck.isOnline(session)){
                return coursesService.getCourseList();
            }
            finalResult.put("courses","Cannot get course list");
            finalResult.put("message","User have not logged in. OR Session is timeout. Please log in");
            finalResult.put("status", operationStatus.FAILED);
            return finalResult;
        } catch (Exception e){
            e.printStackTrace();
            finalResult.put("courses","Cannot get course list");
            if (DevMode.ON) {
                finalResult.put("message", e.toString());
            } else {
                finalResult.put("message", DevMode.unknownError);
            }
            finalResult.put("status", operationStatus.SERVERERROR);
            return finalResult;
        }
    }

}

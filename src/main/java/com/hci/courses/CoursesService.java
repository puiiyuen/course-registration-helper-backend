package com.hci.courses;

import com.hci.utils.DevMode;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    public Object getCourseList() {
        HashMap<String,Object> finalResult = new HashMap<>();
        List<HashMap<String, Object>> result = new ArrayList<>();
        try {
            List<Subject> subjectList = coursesMapper.getSubjectList();
            for (Subject subject : subjectList) {
                HashMap<String, Object> course = new HashMap<>();
                course.put("label", subject.getSubjectName());
                course.put("value", subject.getSubjectName());
                List<Courses> coursesList = coursesMapper.getCourseListBySubject(subject.getSubjectId());
                List<HashMap<String, Object>> items = new ArrayList<>();
                for (Courses courses: coursesList){
                    HashMap<String, Object> item = new HashMap<>();
                    item.put("value",courses);
                    item.put("label",courses.getCourseId()+" "+courses.getCourseName());
                    items.add(item);
                }
                course.put("children", items);
                result.add(course);
            }
            finalResult.put("courses",result);
            finalResult.put("message","OK");
            finalResult.put("status",operationStatus.SUCCESSFUL);
            return finalResult;
        } catch (Exception e) {
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

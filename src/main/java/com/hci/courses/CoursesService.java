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

    public Object getElectiveCourses() {
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<Subject> subjectList = coursesMapper.getSubjectList();
            for (Subject subject : subjectList) {
                HashMap<String, Object> course = new HashMap<>();
                course.put("label", subject.getSubjectName());
                course.put("value", subject.getSubjectId());
                List<Courses> coursesList = coursesMapper.getCourseListBySubject(subject.getSubjectId());
                List<HashMap<String, Object>> items = new ArrayList<>();
                for (Courses courses : coursesList) {
                    HashMap<String, Object> item = new HashMap<>();
                    item.put("value", courses);
                    item.put("label", courses.getCourseId() + " " + courses.getCourseName());
                    items.add(item);
                }
                course.put("children", items);
                result.add(course);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR + ": Cannot get electiveCourses";
        }
    }

    public Object getRequiredCourses(String userId) {
        try {
            List<Courses> requiredCoursesList = coursesMapper.getRequiredCourses(userId);
            List<HashMap<String, Object>> items = new ArrayList<>();
            for (Courses requiredCourse : requiredCoursesList) {
                HashMap<String, Object> item = new HashMap<>();
                item.put("value", requiredCourse.getCourseId());
                item.put("label", requiredCourse.getCourseId() + " " + requiredCourse.getCourseName());
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR + ": Cannot get requiredCourses";
        }

    }

    public Object getCourseList(String userId) {
        HashMap<String, Object> courses = new HashMap<>();
        try {
            courses.put("requiredCourses", getRequiredCourses(userId));
            courses.put("electiveCourses", getElectiveCourses());
            courses.put("message","OK");
            courses.put("status",operationStatus.SUCCESSFUL);
        } catch (Exception e) {
            e.printStackTrace();
            courses.put("courses","Cannot fetch course list");
            courses.put("status",operationStatus.SERVERERROR);
            if (DevMode.ON) {
                courses.put("message", e.toString());
            } else {
                courses.put("message", DevMode.unknownError);
            }
        }
        return courses;
    }
}

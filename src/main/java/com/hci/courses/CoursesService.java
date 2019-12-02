package com.hci.courses;

import com.hci.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    public Object getElectiveCourses(String userId) {
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<Subject> subjectList = coursesMapper.getSubjectList();
            for (Subject subject : subjectList) {
                HashMap<String, Object> course = new HashMap<>();
                course.put("label", subject.getSubjectName());
                course.put("value", subject.getSubjectId());
                List<Courses> coursesList = coursesMapper.getCourseListBySubject(subject.getSubjectId(),userId);
                List<HashMap<String, Object>> items = courseWrapper(coursesList);
                course.put("children", items);
                result.add(course);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR + ": Cannot get electiveCourses";
        }
    }

    public List<HashMap<String, Object>> courseWrapper(List<Courses> coursesList) {
        List<HashMap<String, Object>> items = new ArrayList<>();
        for (Courses courses : coursesList) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("value", courses.getCourseId());
            item.put("label", courses.getCourseId() + " " + courses.getCourseName());
            items.add(item);
        }
        return items;
    }

    public Object getRequiredCourses(String userId) {
        try {
            List<Courses> requiredCoursesList = coursesMapper.getRequiredCourses(userId);
            return courseWrapper(requiredCoursesList);
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR + ": Cannot get requiredCourses";
        }

    }

    public Object getCourseList(String userId) {
        HashMap<String, Object> courses = new HashMap<>();
        try {
            courses.put("requiredCourses", getRequiredCourses(userId));
            courses.put("electiveCourses", getElectiveCourses(userId));
            courses.put("message", "OK");
            courses.put("status", operationStatus.SUCCESSFUL);
        } catch (Exception e) {
            e.printStackTrace();
            courses.put("courses", "Cannot fetch course list");
            courses.put("status", operationStatus.SERVERERROR);
            if (DevMode.ON) {
                courses.put("message", e.toString());
            } else {
                courses.put("message", DevMode.unknownError);
            }
        }
        return courses;
    }

    public Object getDegreeMajor() {
        HashMap<String, Object> result = new HashMap<>();
        List<Object> degreeMajor = new ArrayList<>();
        try {
            List<Degree> degreeList = coursesMapper.getDegreeList();
            for (Degree degree : degreeList) {
                HashMap<String, Object> degreeItem = new HashMap<>();
                degreeItem.put("value ", degree.getDegreeId());
                degreeItem.put("label", degree.getDegreeName());
                List<Major> majorList = coursesMapper.getMajorByDegreeId(degree.getDegreeId());
                List<HashMap<String, Object>> majorDTO = new ArrayList<>();
                for (Major major: majorList){
                    HashMap<String, Object> majorItem = new HashMap<>();
                    majorItem.put("value",major.getMajorId());
                    majorItem.put("label",major.getMajorName());
                    majorDTO.add(majorItem);
                }
                degreeItem.put("children",majorDTO);
                degreeMajor.add(degreeItem);
            }
            result.put("degreeMajor", degreeMajor);
            result.put("message", "OK");
            result.put("status", operationStatus.SUCCESSFUL);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", operationStatus.SERVERERROR);
            result.put("degreeMajor", "Cannot get degree & major list");
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
        }
        return result;
    }
}

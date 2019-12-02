package com.hci.registration;

import com.hci.utils.DevMode;
import com.hci.utils.operationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationMapper registrationMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean addCourses(String userId, String course) {
        try {
            if (registrationMapper.addCourse(userId, course) != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean dropCourses(String userId, String course) {
        try {
            if (registrationMapper.dropCourse(userId, course) != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean completeCourses(String userId, String courseId, String letterGrade) {
        try {
            double gradePoint;
            String status = "Passed";
            if (letterGrade.equals("A+")) {
                gradePoint = 4.3;
            } else if (letterGrade.equals("A")) {
                gradePoint = 4.0;
            } else if (letterGrade.equals("A-")) {
                gradePoint = 3.7;
            } else if (letterGrade.equals("B+")) {
                gradePoint = 3.3;
            } else if (letterGrade.equals("B")) {
                gradePoint = 3.0;
            } else if (letterGrade.equals("B-")) {
                gradePoint = 2.7;
            } else if (letterGrade.equals("C+")) {
                gradePoint = 2.3;
            } else if (letterGrade.equals("C")) {
                gradePoint = 2.0;
            } else if (letterGrade.equals("C-")) {
                gradePoint = 1.7;
            } else if (letterGrade.equals("D")) {
                gradePoint = 1.0;
            } else if (letterGrade.equals("F")) {
                gradePoint = 0.0;
                status = "Failed";
            } else {
                return false;
            }

            if (registrationMapper.completeCourse(userId, courseId, status, letterGrade, gradePoint) != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            return false;
        }
    }


    public Object getCourseRegistrationList(String userId) {
        try {
            return registrationMapper.getCourseRegistrationList(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return operationStatus.SERVERERROR + " Cannot get course registration list";
        }
    }

    public Object getBasicInfo(String userId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            BasicInfo basicInfo = new BasicInfo();
            basicInfo.setGPA(registrationMapper.getGPA(userId));
            basicInfo.setInProgress(registrationMapper.getInProgress(userId));
            basicInfo.setTotalCreditHour(registrationMapper.getTotalCreditHour(userId));
            return basicInfo;
        } catch (Exception e) {
            e.printStackTrace();
            if (DevMode.ON) {
                result.put("message", e.toString());
            } else {
                result.put("message", DevMode.unknownError);
            }
            result.put("status", operationStatus.SERVERERROR);
        }
        return result;
    }

}

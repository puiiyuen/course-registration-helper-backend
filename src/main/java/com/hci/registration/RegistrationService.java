package com.hci.registration;

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
    public boolean addCourses(String userId, List<String> courses) {
        try {
            for (String course : courses) {
                if (registrationMapper.addCourse(userId, course) != 1) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//Manual transaction rollback
            return false;
        }
    }

    public Object getCourseRegistrationList(String userId){
        try {
            return registrationMapper.getCourseRegistrationList(userId);
        } catch (Exception e){
            e.printStackTrace();
            return operationStatus.SERVERERROR+" Cannot get course registration list";
        }
    }

}

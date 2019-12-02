package com.hci.registration;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegistrationMapper {

    @Insert("INSERT INTO registration (user_id,course_id) " +
            "SELECT #{userId},#{courseId} " +
            "FROM dual WHERE NOT EXISTS (SELECT user_id FROM registration " +
            "WHERE user_id=#{userId} AND course_id=#{courseId})")
    int addCourse(String userId, String courseId);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "courseStatus", column = "status"),
            @Result(property = "subject",column = "subject_name")
    })
    @Select("SELECT user_id,r.course_id,c.course_name,status,s.subject_name " +
            "FROM registration AS r,courses AS c,subject AS s " +
            "WHERE user_id=#{userId} AND r.course_id=c.course_id AND s.subject_id=c.subject_id")
    List<Registration> getCourseRegistrationList(String userId);

    @Select("SELECT SUM(c.credit_hour) AS 'total_credit_hour' FROM courses AS c,registration AS r " +
            "WHERE grade_point>0 AND c.course_id=r.course_id AND r.user_id=#{userId}")
    int getTotalCreditHour(String userId);

    @Select("SELECT COUNT(status) AS 'in_progress' FROM registration " +
            "WHERE status LIKE '%In-Progress%' AND user_id=#{userId}")
    int getInProgress(String userId);


    @Select("SELECT SUM(r.grade_point*c.credit_hour)/SUM(c.credit_hour) AS 'GPA' FROM registration AS r, courses AS c " +
            "WHERE r.grade_point>=0 AND c.course_id=r.course_id AND r.user_id=#{userId}")
    double getGPA(String userId);



}

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

    @Delete("DELETE FROM registration WHERE user_id=#{userId} AND course_id=#{courseId}")
    int dropCourse(String userId, String courseId);

    @Update("Update registration SET letter_grade=#{letterGrade},grade_point=#{gradePoint},status=#{status} " +
            "WHERE user_id=#{userId} AND course_id=#{courseId}")
    int completeCourse(String userId, String courseId, String status, String letterGrade, double gradePoint);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "creditHour", column = "credit_hour"),
            @Result(property = "courseStatus", column = "status"),
            @Result(property = "subject", column = "subject_name"),
            @Result(property = "letterGrade", column = "letter_grade"),
            @Result(property = "gradePoint", column = "grade_point")
    })
    @Select("SELECT user_id,r.course_id,c.course_name,c.credit_hour,status,s.subject_name,letter_grade,grade_point " +
            "FROM registration AS r,courses AS c,subject AS s " +
            "WHERE user_id=#{userId} AND r.course_id=c.course_id AND s.subject_id=c.subject_id")
    List<Registration> getCourseRegistrationList(String userId);

    @Select("SELECT IFNULL(SUM(c.credit_hour),0) AS 'total_credit_hour' FROM courses AS c,registration AS r " +
            "WHERE grade_point>0 AND c.course_id=r.course_id AND r.user_id=#{userId}")
    int getTotalCreditHour(String userId);

    @Select("SELECT IFNULL(COUNT(status),0) AS 'in_progress' FROM registration " +
            "WHERE status LIKE '%In-Progress%' AND user_id=#{userId}")
    int getInProgress(String userId);


    @Select("SELECT IFNULL(SUM(r.grade_point*c.credit_hour)/SUM(c.credit_hour),0.0) AS 'GPA' " +
            "FROM registration AS r, courses AS c " +
            "WHERE r.grade_point>=0 AND c.course_id=r.course_id AND r.user_id=#{userId}")
    double getGPA(String userId);


}

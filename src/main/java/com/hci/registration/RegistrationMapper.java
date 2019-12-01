package com.hci.registration;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegistrationMapper {

    @Insert("INSERT INTO registration (user_id,course_id) " +
            "SELECT #{userId},#{courseId} " +
            "FROM dual WHERE NOT EXISTS (SELECT user_id FROM registration " +
            "WHERE user_id=#{userId} AND course_id=#{courseId})")
    int addCourse(String userId,String courseId);

    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName",column = "course_name"),
            @Result(property = "courseStatus", column = "status")
    })
    @Select("SELECT user_id,r.course_id,c.course_name,status FROM registration AS r,courses AS c " +
            "WHERE user_id=#{userId} AND r.course_id=c.course_id")
    List<Registration> getCourseRegistrationList(String userId);

}

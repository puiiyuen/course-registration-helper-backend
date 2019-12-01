package com.hci.registration;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrationMapper {

    @Insert("INSERT INTO registration (user_id,course_id) " +
            "SELECT #{userId},#{courseId} " +
            "FROM dual WHERE NOT EXISTS (SELECT user_id FROM registration " +
            "WHERE user_id=#{userId} AND course_id=#{courseId})")
    int addCourse(String userId,String courseId);

}
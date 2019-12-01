package com.hci.courses;


import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoursesMapper {


    @Results({
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "creditHour", column = "credit_hour"),
            @Result(property = "subjectId", column = "subject_id")
    })
    @Select("SELECT * FROM courses WHERE subject_id=#{subjectJd}")
    List<Courses> getCourseListBySubject(String subjectId);

    @Results({
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "subjectName", column = "subject_name")
    })
    @Select("SELECT subject_id,subject_name from subject")
    List<Subject> getSubjectList();

    @Results({
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "creditHour", column = "credit_hour"),
            @Result(property = "subjectId", column = "subject_id")
    })
    @Select("SELECT DISTINCT c.course_id,course_name,credit_hour,subject_id " +
            "FROM courses AS c,required_courses AS r,psn_info AS p " +
            "WHERE c.course_id=r.course_id AND r.major_id =p.major AND p.user_id=#{userId} " +
            "ORDER BY subject_id DESC ")
    List<Courses> getRequiredCourses(String userId);

    @Results({
            @Result(property = "degreeId", column = "degree_id"),
            @Result(property = "degreeName", column = "degree_name")
    })
    @Select("SELECT * FROM degree")
    List<Degree> getDegreeList();

    @Results({
            @Result(property = "majorId", column = "major_id"),
            @Result(property = "majorName", column = "major_name")
    })
    @Select("SELECT * FROM major WHERE degree_id=#{degreeId}")
    List<Major> getMajorByDegreeId(String degreeId);


}

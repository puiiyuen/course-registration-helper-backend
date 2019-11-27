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
    public List<Courses> getCourseListBySubject(String subjectId);

    @Results({
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "subjectName", column = "subject_name")
    })
    @Select("SELECT subject_id,subject_name from subject")
    public List<Subject> getSubjectList();

}

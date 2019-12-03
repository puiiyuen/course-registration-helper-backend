/**
 * User Mapper
 *
 * @author Peiyuan
 * 2019-01-21
 */

package com.hci.user;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT user_id FROM user WHERE user_id=#{userId}")
    String isExist(String userId);


    @Results({
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT user_id FROM user " +
            "WHERE user_id=#{userId} AND password=#{password} ")
    String existUser(String userId, String password);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickname", column = "nickname"),
//            @Result(property = "email", column = "email"),
            @Result(property = "degree", column = "degree_name"),
            @Result(property = "major", column = "major_name"),
            @Result(property = "degreeId",column = "degree_id"),
            @Result(property = "majorId",column = "major_id")
    })
    @Select("SELECT user_id,nickname,d.degree_name,m.major_name,d.degree_id,m.major_id " +
            "FROM psn_info AS p, major AS m, degree AS d " +
            "WHERE user_id=#{userId} AND m.major_id=p.major AND d.degree_id=p.degree")
    User getUserById(String userId);


    @Insert("INSERT INTO user (user_id,password) VALUES (#{userId},#{password})")
    int createUser(String userId, String password);

    @Insert("INSERT INTO psn_info (user_id,nickname,major,degree) VALUES (#{userId},#{nickname},#{major},#{degree})")
    int createAccount(String userId, String nickname, String major, String degree);

    @Update("UPDATE psn_info SET nickname=#{nickname} WHERE user_id=#{userId}")
    int changeNickname(String nickname, String userId);

    @Update("UPDATE user SET password=#{password} WHERE user_id=#{userId}")
    int changePassword(String password, String userId);

    @Update("UPDATE psn_info SET major=#{major} WHERE user_id=#{userId}")
    int changeMajor(String major, String userId);

    @Update("UPDATE psn_info SET degree=#{degree} WHERE user_id=#{userId}")
    int changeDegree(String degree, String userId);

}

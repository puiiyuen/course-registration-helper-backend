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
            @Result(property = "degree_name", column = "degree"),
            @Result(property = "major_name", column = "major")
    })
    @Select("SELECT user_id,nickname,d.degree_name,m.major_name " +
            "FROM psn_info AS p, major AS m, degree AS d " +
            "WHERE user_id=#{userId} AND m.major_id=p.major AND d.degree_id=p.degree")
    User getUserById(String userId);


    @Insert("INSERT INTO user (user_id,password) VALUES (#{userId},#{password})")
    int createUser(String userId, String password);

    @Insert("INSERT INTO psn_info (user_id,nickname,major,degree) VALUES (#{userId},#{nickname},#{major},#{degree})")
    int createAccount(String userId,String nickname,String major,String degree);


//    @Update("UPDATE user_auth SET phone=#{phone},email=#{email},update_date=CURRENT_TIMESTAMP" +
//            " WHERE user_id=#{userId} AND account_status!='destroy'")
//    int modifyContact(String userId, String phone, String email);



}

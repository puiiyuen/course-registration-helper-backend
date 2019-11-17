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
    String isExsit(String userId);


    @Results({
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT user_id FROM user " +
            "WHERE user_id=#{userId} AND password=#{password} ")
    User existUser(String userId, String password);

//    @Results({
//            @Result(property = "userId", column = "user_id"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "phone", column = "phone"),
//            @Result(property = "email", column = "email"),
//            @Result(property = "userType", column = "user_type")
//    })
//    @Select("SELECT user_id,username,phone,email,user_type FROM user_auth WHERE user_id=#{userId} " +
//            "AND account_status='activated'")
//    User getUserById(String userId);


    @Insert("INSERT INTO user (user_id,password) VALUES (#{userId},#{password})")
    int createUser(String userId, String password);

    @Insert("INSERT INTO psn_info (user_id,nickname,major) VALUES (#{userId},#{nickname},#{major})")
    int createAccount(String userId,String nickname,String major);


//    @Update("UPDATE user_auth SET phone=#{phone},email=#{email},update_date=CURRENT_TIMESTAMP" +
//            " WHERE user_id=#{userId} AND account_status!='destroy'")
//    int modifyContact(String userId, String phone, String email);



}

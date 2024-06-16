package com.cfg.softcare.Repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<String,Object> login(String phone, String password){
        return jdbcTemplate.queryForMap("EXEC dbo.sp_login ?,?",phone,password);
    }

    public int insertUser(String fullname, String email, String password,  String phone , int role){
        return jdbcTemplate.update("EXEC  dbo.sp_insert_user ?,?,?,?,?,?",fullname,email,password,
                phone,role);
    }



    public int updateUser(String fullname, String email, String password,  String phone , int role,String active_yn){
        return jdbcTemplate.update("EXEC  dbo.sp_update_user ?,?,?,?,?,?",fullname,email,password,
                phone,role,active_yn);
    }









}

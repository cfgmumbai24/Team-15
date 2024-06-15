package com.cfg.softcare.Repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String , Object >> catalog(){
        return jdbcTemplate.queryForList("EXEC dbo.sp_ShowProducts");
    }

    public int insertCategory(String category_name){
        System.out.println(category_name);
        return jdbcTemplate.update("EXEC  dbo.sp_insert_category ?",category_name);
    }


    public List<Map<String, Object>> showBuyers() {
        return jdbcTemplate.queryForList("EXEC  dbo.BuyerDetails ");
    }

    public List<Map<String,Object>> fetchCategory(){
        return jdbcTemplate.queryForList("EXEC dbo.sp_fetch_category");
    }


    public List<Map<String, Object>> fetchVerifiedRequest() {
        return jdbcTemplate.queryForList("EXEC dbo.sp_fetch_verified_Requests");
    }

    public List<Map<String, Object>> fetchPendingRequest() {
        return jdbcTemplate.queryForList("EXEC dbo.sp_fetch_Pending_Requests");
    }

    public int updateQty(int Cid,int Qty,String operation){
        return jdbcTemplate.update("EXEC dbo.sp_updateQTY ?,?,?",Cid,Qty,operation);
    }


    public int sendQuery(int Cid, int Qty, int uid, String Desc) {
        return jdbcTemplate.update("EXEC dbo.sp_ReqOrder ?,?,?,?",Cid,Qty,Desc,uid);
    }

    public Map<String, Object> getSpecificUser(int uid) {
        return jdbcTemplate.queryForMap("EXEC dbo.sp_get_specific_user ?",uid);
    }

    public Map<String, Object> getSpecificProduct(int cid) {
        return jdbcTemplate.queryForMap("EXEC dbo.sp_SpecProd ?",cid);
    }

    public List<Map<String, Object>> fetchSubAdmin() {
        return jdbcTemplate.queryForList("EXEC dbo.showSubAdmin");
    }

    public List<Map<String, Object>> fetchAdmin() {
        return jdbcTemplate.queryForList("EXEC dbo.showAdmin");
    }
}

package com.cfg.softcare.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.cfg.softcare.Repository.ProductsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;
import java.util.Map;

@Service
public class ProductsService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ProductsRepository productRepository;

    public ResponseEntity<Map<String, Object>> insertCategory(Map<String,Object> body){
        String category_name=(String) body.get("category");
        int insertedRows = productRepository.insertCategory(category_name);

        if (insertedRows > 0) {
            return ResponseEntity.ok(Map.of("status", "Successful"));

        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status", "failed"));


    }

    public List<Map<String, Object>> showBuyers() {
        return productRepository.showBuyers();
    }

    public List<Map<String,Object>> fetchCategory(){
        return productRepository.fetchCategory();
    }

    public List<Map<String, Object>> fetchVerifiedRequest() {
        return productRepository.fetchVerifiedRequest();
    }

    public List<Map<String, Object>> fetchPendingRequest() {
        return productRepository.fetchPendingRequest();
    }

    public ResponseEntity<Map<String, Object>> updateQty(Map<String,Object> body){
        int cid=(int)body.get("cid");
        int qty=(int)body.get("qty");
        String operation=(String)body.get("operation");

        int insertedRows = productRepository.updateQty(cid,qty,operation);

        if (insertedRows > 0) {
            return ResponseEntity.ok(Map.of("status", "Successful"));

        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status", "failed"));

    }

    public ResponseEntity<Map<String, Object>> sendQuery(Map<String, Object> body) {
        int Cid=(int)body.get("cid");
        int Qty=(int)body.get("qty");
        int uid=(int)body.get("uid");
        String Desc=(String)body.get("desc");

        int insertedRows=productRepository.sendQuery(Cid,Qty,uid,Desc);
        if (insertedRows > 0) {
            Map<String,Object> userData=productRepository.getSpecificUser(uid);
            Map<String,Object> catalogData=productRepository.getSpecificProduct(Cid);

            sendMail((String)userData.get("name"),(String)userData.get("email"),(String)userData.get("phone"),Desc,Qty,
                    (String)catalogData.get("SKU"),
                    (String)catalogData.get("category_name"),(String)catalogData.get("Color"),(String)catalogData.get(
                            "name"),
                    (int)catalogData.get("Price"),(String)catalogData.get("Description"));
            return ResponseEntity.ok(Map.of("status", "Successful"));

        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status", "failed"));


    }

    public void sendMail(String username,String email,String phone,String query,int qty,String SKU,
                         String category_name,String Color,String product_name,int Price,String Description ){
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);

        try{
            mimeMessageHelper.setSubject("Query Raised");
            List<Map<String,Object>> subadmin=productRepository.fetchSubAdmin();
            List<Map<String,Object>> admin=productRepository.fetchAdmin();

            for(Map<String,Object> x : subadmin){
                String SubAdminemail=(String)x.get("email");
                mimeMessageHelper.setTo(SubAdminemail);
                mimeMessageHelper.setText(
                        "<h1>Buyer Details</h1>"+"<h3>"+username +"</h3>"+"<br><h3>"+email +"</h3><br><h3>"+phone+
                                "</h3><br><h3>Raised A Query: <br>"+query+"</h3>for the following product:<br>Product" +

                                " Name :"+product_name+"<br>Category Name :"+category_name+"<br>Color:"+Color+"<br" +
                                ">Quantity:"+qty+"<br>SKU:"+SKU+"<br>Price:"+Price+"<br>Description:"+Description,true);
            }

            for(Map<String,Object> x : admin){
                String Adminemail=(String)x.get("email");
                mimeMessageHelper.setTo(Adminemail);
                mimeMessageHelper.setText(
                        "<h1>Buyer Details</h1>"+"<h3>"+username +"</h3>"+"<br><h3>"+email +"</h3><br><h3>"+phone+
                                "</h3><br><h3>Raised A Query: <br>"+query+"</h3>for the following product:<br>Product" +

                                " Name :"+product_name+"<br>Category Name :"+category_name+"<br>Color:"+Color+"<br" +
                                ">Quantity:"+qty+"<br>SKU:"+SKU+"<br>Price:"+Price+"<br>Description:"+Description,true);
            }
        }
        catch (MessagingException e){
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMessage);
    }
}

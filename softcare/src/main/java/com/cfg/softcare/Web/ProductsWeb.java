package com.cfg.softcare.Web;


import com.cfg.softcare.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:4200","http://localhost:63145","http://localhost:3000"},allowCredentials =
        "true")
public class ProductsWeb {

    @Autowired
    ProductsService productsService;

    @PostMapping("/newCategory")
    public ResponseEntity<Map<String,Object>> insertCategory(@RequestBody  Map<String,Object> body){
        return productsService.insertCategory(body);
    }

    @GetMapping("/BuyerDetails")
    public List<Map<String,Object>> showBuyers(){
        return productsService.showBuyers();
    }

    @GetMapping("/getCategory")
    public List<Map<String,Object>> fetchCategory(){
        return productsService.fetchCategory();
    }

    @GetMapping("/getPendingRequests")
    public List<Map<String,Object>> fetchPendingRequest(){
        return productsService.fetchPendingRequest();
    }

    @GetMapping("/getVerifiedRequests")
    public List<Map<String,Object>> fetchVerifiedRequest(){
        return productsService.fetchVerifiedRequest();
    }

    @PostMapping("/updateQty")
    public ResponseEntity<Map<String,Object>> updateQty(@RequestBody Map<String,Object> body){
        return productsService.updateQty(body);
    }

    @PostMapping("/sendQuery")
    public ResponseEntity<Map<String,Object>> sendQuery(@RequestBody Map<String,Object> body){
        return productsService.sendQuery(body);
    }

//    @PostMapping("/sendRequest")
//    public ResponseEntity<Map<String,Object>> sendRequest(@RequestBody Map<String,Object> body){
//        return productsService.sendRequest(body);
//    }

    @GetMapping("/showProducts")
    public List<Map<String,Object>> showProducts(){
        return productsService.showProducts();
    }

    @GetMapping("/getRequest")
    public List<Map<String,Object>> getRequest(){


        return productsService.getRequest();
    }







}

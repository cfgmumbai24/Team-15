package com.cfg.softcare.Web;


import com.cfg.softcare.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:4200","http://localhost:63145"},allowCredentials = "true")
public class UserWeb {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,Object> body){
        return userService.login(body);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> insertUser(@RequestBody Map<String,Object> body){
        return userService.insertUser(body);

    }

    public void UploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                           @RequestParam("userid") int userid){
        userService.uploadFile(file,name,userid);
    }
}

package com.cfg.softcare.Web;


import com.cfg.softcare.Service.FetchJsonDataService;
import com.cfg.softcare.Service.UserService;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:4200","http://localhost:63145","http://localhost:3000"},allowCredentials =
        "true")
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

    @Autowired
    private FetchJsonDataService fetchJsonDataService;

    @GetMapping("/fetch-data")
    public List<Map<String,Object>> fetchData() throws IOException {
         return fetchJsonDataService.fetchData("https://firestore.googleapis.com/v1/projects/jpmc-userapp/databases/" +
                 "(default)/documents/Boards?key=");
    }

//    public static String stream(URL url) {
//        try (InputStream input = url.openStream()) {
//            InputStreamReader isr = new InputStreamReader(input);
//            BufferedReader reader = new BufferedReader(isr);
//            StringBuilder json = new StringBuilder();
//            int c;
//            while ((c = reader.read()) != -1) {
//                json.append((char) c);
//            }
//            return json.toString();
//        }
//        catch(Exception e){
//
//        }
//    }
}




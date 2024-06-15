package com.cfg.softcare.Service;


import com.cfg.softcare.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Map<String, Object> login(Map<String, Object> body) {

        String phone = (String) body.get("phone");
        String pwd = (String) body.get("pwd");

        return userRepository.login(phone, pwd);
    }

    public ResponseEntity<Map<String, Object>> insertUser(Map<String, Object> body) {
        String fullname = (String) body.get("full_name");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String phone = (String) body.get("phone");
        int role = (int) body.get("role");

        int insertedRows = userRepository.insertUser(fullname,  email, password, phone, role);
        if (insertedRows > 0) {
            return ResponseEntity.ok(Map.of("status", "Successful"));

        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status", "failed"));
    }

    public void uploadFile(MultipartFile data, String name, int id) {
        Path path = Paths.get("Photos");
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            DateFormat DFormat
                    = DateFormat.getDateTimeInstance();
            String fileName = StringUtils.cleanPath(data.getOriginalFilename());
            String[] extension=fileName.split(Pattern.quote("."));

            Path finalPath = path.resolve(name+ DFormat.hashCode() +"."+extension[1]);

            InputStream inputStream = data.getInputStream();

            Files.copy(inputStream, finalPath, StandardCopyOption.REPLACE_EXISTING);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

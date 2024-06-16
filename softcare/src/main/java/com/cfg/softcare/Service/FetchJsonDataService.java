package com.cfg.softcare.Service;

import com.cfg.softcare.Repository.ProductsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FetchJsonDataService {

    @Autowired
    ProductsRepository productsRepository;
    private final RestTemplate restTemplate;

    public FetchJsonDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String,Object>> fetchData(String url) throws IOException {
        String jsonString = restTemplate.getForObject(url, String.class);

        Gson gson = new GsonBuilder().create();
        DocumentList documentList = gson.fromJson(jsonString, DocumentList.class);
        List<Map<String, Object>> ans = new ArrayList<>();
        // Accessing documents
        for (Document document : documentList.getDocuments()) {
            System.out.println("Fields:");
//            String image;
//            String inferredClass;
//            int qty;

            Map<String, Object> details = new HashMap<>();
            for (Map.Entry<String, Object> entry : document.getFields().entrySet()) {

                if ("inferredClass".equals(entry.getKey()) || "image".equals(entry.getKey())) {
                    System.out.println(((Map) entry.getValue()).get("stringValue"));
                    details.put(entry.getKey(), ((Map) entry.getValue()).get("stringValue"));
                } else if ("qty".equals(entry.getKey())) {
                    System.out.println(((Map) entry.getValue()).get("integerValue"));
                    details.put(entry.getKey(), ((Map) entry.getValue()).get("integerValue"));

                }

                ans.add(details);

            }


        }
        return ans;

//        return objectMapper.readValue(jsonString, new TypeReference<List<Item>>() {});
    }
}
class DocumentList {
    private Document[] documents;

    // Getter and setter
    public Document[] getDocuments() {
        return documents;
    }

    public void setDocuments(Document[] documents) {
        this.documents = documents;
    }
}
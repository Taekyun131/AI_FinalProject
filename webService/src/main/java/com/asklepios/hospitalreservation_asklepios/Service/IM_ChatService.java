package com.asklepios.hospitalreservation_asklepios.Service;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class IM_ChatService implements IF_ChatService {
    private final RestTemplate restTemplate;

    public IM_ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String guideMessage(String userMessage) {
        System.out.println("Service");
        System.out.println(userMessage);

        String API_URL = "http://54.180.120.47:5100/chatbot";

        JSONObject requestJson = new JSONObject();
        requestJson.put("message", userMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());

        return responseJson.getString("response");
    }

    @Override
    public String recommendDepartment(String symptoms) {
        System.out.println("Service");
        System.out.println(symptoms);

        String url = "http://54.180.120.47:5000/predict"; // Python Flask 서버 주소

        // JSON 데이터 생성
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("symptoms", symptoms);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // Flask API 호출
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody().get("medical").toString();
    }
}
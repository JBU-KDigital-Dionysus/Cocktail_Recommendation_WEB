package com.cocktail.cocktail_recommendation.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ApiController {

    @Value("${uploadFilePath}")
    public String SAVE_PATH;

    @Value("${flavorBasedReco}")
    public String flavorBasedReco;



    @GetMapping(value = "/CONT/upload")
    public String imgUpload(HttpSession session, RedirectAttributes redirectAttributes) {
        JSONObject dbSrvJson = new JSONObject();
        Enumeration<String> enumStr = session.getAttributeNames();
        while (enumStr.hasMoreElements()) {
            String attrName = enumStr.nextElement();
            dbSrvJson.put(attrName, session.getAttribute(attrName));
        }

        try{
            dbSrvJson.remove("loginUser");
            dbSrvJson.remove("redirectPage");
            dbSrvJson.remove("recoCocktail");
        }catch (Exception e){
            System.out.println("지울 세션 없음");
        }

        JSONObject result_json = getRecommendResult(flavorBasedReco, dbSrvJson, "POST");
        ObjectMapper mapper = new ObjectMapper();
        String json_str = result_json.toJSONString();
        Integer recommendedCtId = 0;
        System.out.println("json_str: " + json_str);
        try{
            Map map = mapper.readValue(json_str, Map.class);
            map = (Map)map.get("data");
            recommendedCtId = (Integer)map.get("reco_0");
            session.setAttribute("reoCocktail", map);
            List<Integer> valueList = (List<Integer>) map.values().stream().collect(Collectors.toCollection(ArrayList::new));
            session.setAttribute("cocktailList", valueList);
            System.out.println(valueList);
            System.out.println(valueList.getClass());
        }catch (Exception e){
            e.printStackTrace();
        }
        redirectAttributes.addAttribute("ctNo", recommendedCtId);
        return "redirect:/cocktail/detail.do";
    }


    public JSONObject getRecommendResult(String pythonUrl, JSONObject pflavors, String requestMethod) {
        JSONObject responseJson = new JSONObject();
        System.out.println("pflavors: " + pflavors);
        try {
            URL url = new URL(pythonUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod(requestMethod);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Whale/3.18.154.7 Safari/537.36");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Accept", "application/json");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpConn.getOutputStream()));
            bw.write(pflavors.toString());
            bw.flush();
            bw.close();

            StringBuilder sb = new StringBuilder();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                responseJson.put("data", sb);
                responseJson.put("result", "SUCCESS");
                br.close();

                return responseJson;
            } else {
                responseJson.put("result", "FAIL");
                return responseJson;
            }
        } catch (IOException e) {
            responseJson.put("result", "EXCEPTION");
            return responseJson;
        }
    }
}

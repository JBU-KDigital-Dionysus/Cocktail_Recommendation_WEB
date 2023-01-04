package com.cocktail.cocktail_recommendation.controller;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.repository.CocktailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    CocktailRepository cocktailRepository;

    @Value("${likedBasedReco}")
    public String likedBasedReco;

    @GetMapping("/")
    public String home(@SessionAttribute(required = false) Customer loginUser, Model model) {
        String cstId = null;

        if (loginUser != null) {
            List<CocktailDto> likedBasedRecoResult = likeRecommend(loginUser);
            model.addAttribute("likedBaseCts", likedBasedRecoResult);
            System.out.println(likedBasedRecoResult);

        }

        return "index";
    }

    public List<CocktailDto> likeRecommend(Customer loginUser) {
        System.out.println("Request likeRecommend");
        List<Integer> valueList = null;
        List<CocktailDto> cosineList = new ArrayList<>();
        JSONObject dbSrvJson = new JSONObject();
        dbSrvJson.put("cstId", loginUser.getCstId());
        JSONObject result_json = getRecommendResult(likedBasedReco, dbSrvJson, "POST");
        ObjectMapper mapper = new ObjectMapper();
        String json_str = result_json.toJSONString();
        try {
            Map map = mapper.readValue(json_str, Map.class);
            map = (Map) map.get("data");
            valueList = (List<Integer>) map.values().stream().collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < valueList.size(); i++) {
                CocktailDto cocktail = cocktailRepository.getByCtNo(valueList.get(i));
                cosineList.add(cocktail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cosineList;
    }

    public JSONObject getRecommendResult(String pythonUrl, JSONObject pflavors, String requestMethod) {
        JSONObject responseJson = new JSONObject();
        try {
            URL url = new URL(pythonUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
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

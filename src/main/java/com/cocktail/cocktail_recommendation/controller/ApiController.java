package com.cocktail.cocktail_recommendation.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/CONT")
public class ApiController {

	@Value("${uploadFilePath}")
	public String SAVE_PATH;

	@Value("${pythonURL}")
	public String pythonURL;
	
	@Value("${debugMode}")
	public boolean debugMode;

	@PostMapping(value = "/upload",consumes="application/json;" )
	@ResponseBody
	public Map<String, String>  imgUpload(@RequestBody  Map<String, Object> map) {
		System.out.println(map);
		System.out.println(map.get("fileName"));
		//코드 실행 전에 시간 받아오기
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		JSONObject dbSrvJson = new JSONObject();
		dbSrvJson.put("fileName", map.get("fileName"));

		JSONObject result = byPass(pythonURL, dbSrvJson, "POST");
		resultMap.put("result", result.toString());
		return resultMap;
	}
	
	

	public JSONObject byPass(String url, JSONObject jsonData, String option) {
	    JSONObject responseJson = new JSONObject();
	    try {
	        // 연결할 url 생성
	        URL start_object = new URL(url);

	        // http 객체 생성
	        HttpURLConnection start_con = (HttpURLConnection) start_object.openConnection();
	        start_con.setDoOutput(true);
	        start_con.setDoInput(true);

	        // 설정 정보
	        start_con.setRequestProperty("Content-Type", "application/json");
	        start_con.setRequestProperty("Accept", "application/json");
	        start_con.setRequestMethod(option);

	        // data 전달

	        // 출력 부분
	        OutputStreamWriter wr = new OutputStreamWriter(start_con.getOutputStream());
	        wr.write(jsonData.toString());
	        wr.flush();

	        // 응답 받는 부분
	        StringBuilder start_sb = new StringBuilder();
	        int start_HttpResult = start_con.getResponseCode();

	        // 결과 성공일 경우 = HttpResult 200일 경우
	        if (start_HttpResult == HttpURLConnection.HTTP_OK) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(start_con.getInputStream(), "utf-8"));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                start_sb.append(line);
	            }
	            responseJson.put("data", start_sb);
	            responseJson.put("result", "SUCCESS");
	            br.close();
	            return responseJson;
	        } else {
	            // 그 외의 경우(실패)
	            responseJson.put("result", "FAIL");
	            return responseJson;
	        }
	    } catch (Exception e) {
	        responseJson.put("result", "EXCEPTION");
	        return responseJson;
	    }
	}
	
}

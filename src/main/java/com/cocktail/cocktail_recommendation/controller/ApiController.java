package com.cocktail.cocktail_recommendation.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/CONT")
public class ApiController {

	@Value("${uploadFilePath}")
	public String SAVE_PATH;

	@Value("${pythonURL}")
	public String pythonURL;
	
	@Value("${debugMode}")
	public boolean debugMode;

	@PostMapping(value = "/upload")
	@ResponseBody
	public Map<String, String> imgUpload(HttpSession session) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// 이미지 폴더 경로
		String path = SAVE_PATH;
		
		JSONObject dbSrvJson = new JSONObject();
		Enumeration<String> enumStr = session.getAttributeNames();
		while(enumStr.hasMoreElements()){
			String attrName = enumStr.nextElement();
			dbSrvJson.put(attrName, session.getAttribute(attrName));
		}
//		JSONObject result = getRecommendResult(pythonURL, "POST");
//		resultMap.put("result", result.toString());
		System.out.println(dbSrvJson.toJSONString());
		JSONObject result_json = getRecommendResult(pythonURL, dbSrvJson, "POST");

		resultMap.put("result", result_json.toJSONString());

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
			e.printStackTrace();
	        responseJson.put("result", "EXCEPTION");
	        return responseJson;
	    }
	}

	public JSONObject getRecommendResult(String pythonUrl, JSONObject pflavors, String requestMethod) {
		JSONObject responseJson = null;

		try {
			URL url = new URL("http://localhost:5000/apiTest");
//            URL url = new URL(pythonUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestMethod(requestMethod);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Whale/3.18.154.7 Safari/537.36");
			httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setRequestProperty("Accept", "application/json");

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2
					? httpConn.getInputStream()
					: httpConn.getErrorStream();
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			System.out.println("response: " + response);
			responseJson.put("content", response);
			responseJson.put("result", "SUCCESS");
		} catch (IOException e) {
			responseJson.put("result", "EXCEPTION");
			throw new RuntimeException(e);
		}

		return responseJson;
	}
	
}

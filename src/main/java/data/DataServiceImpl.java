package data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class DataServiceImpl implements DataService {
	//xml -> json 으로 변환하는 처리
	private ArrayList<HashMap<String, Object>> xml_to_json( String xml) {
		ArrayList<HashMap<String, Object>> list 
		= new ArrayList<HashMap<String, Object>>();
		
		try {
			JSONObject json = (JSONObject) new JSONParser().parse(xml);
			json = (JSONObject)json.get("response");
			json = (JSONObject) json.get("body");
			if(json == null) return list;
			
			if( json.get("totalCount") == null || (Long)json.get("totalCount") > 0) {
				if( json.get("items") instanceof JSONObject) {
					json = (JSONObject) json.get("items");
					if( json.get("item") instanceof JSONArray) {
						JSONArray item = (JSONArray)json.get("item");
						list = new ObjectMapper().readValue(item.toString(), 
								new TypeReference<ArrayList<HashMap<String, Object>>>(){} );					
					}else {
						json =(JSONObject)json.get("item");
						HashMap<String, Object> map=new ObjectMapper().readValue(json.toString(), 
								new TypeReference<HashMap<String, Object>>(){});
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public String xml_list(StringBuilder url) {

		String result = url.toString();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(result).openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<HashMap<String, Object>> json_list(StringBuilder url) {
		return xml_to_json(xml_list(url));
	}	
}
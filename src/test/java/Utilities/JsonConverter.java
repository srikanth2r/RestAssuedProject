package Utilities;

import io.restassured.path.json.JsonPath;

public class JsonConverter {
	
	public static JsonPath returnJson(String response) {
		
		JsonPath js=new JsonPath(response);
		
		return js;
		
	}
	

	

}

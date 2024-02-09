package Dailytesting;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class OauthAuthentication {
	
	public static void main(String[] args) {
		
	String rrp	=given().
		formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
		formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W" ).
		formParams("grant_type", "client_credentials").
		formParams("scope", "trust").
		when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(rrp);
		
		JsonPath js=new JsonPath(rrp);
		String token=js.getString("access_token");
		System.out.println(token);
		
		//Get the course details
		
		String courseresp=given().queryParam("access_token", token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		System.out.println(courseresp);
		
		
	}

}

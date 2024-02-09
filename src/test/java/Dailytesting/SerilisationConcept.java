package Dailytesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SerilisationConcept {
	
	public static void main(String[] args) {
		
		
		SerilsationMaps maps=new SerilsationMaps();
		
		maps.setAddress("machperson , singapore");
		maps.setPhone_number("53454353453");
		maps.setWebsite("http://google.com");
		maps.setAccuracy(30);
		
		ArrayList<String> abc=new ArrayList<String>();
		abc.add("new shoes");
		abc.add("nike");
		maps.setTypes(abc);
		SerLocation l=new SerLocation();
		l.setLat(123435.987);
		l.setLng(098876.345);
		maps.setLocation(l);
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response locationupdate = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(maps).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.extract().response();
		String respo=locationupdate.asString();
		System.out.println(respo);
		
	}

}

package Dailytesting;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class DeSerilisationConcept {
	
	public static void main(String[] args) {
		
		String[] expectedtitels= {"Selenium Webdriver Java","Cypress","Protractor"};
		String keygen	=given().
				formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
				formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W" ).
				formParams("grant_type", "client_credentials").
				formParams("scope", "trust").
				when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
				System.out.println(keygen);
				
				JsonPath js=new JsonPath(keygen);
				String token=js.getString("access_token");
				System.out.println(token);
				
				//Get the course details
				
				GetCourse	 courseresp=given().queryParam("access_token", token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
				
				System.out.println(courseresp.getInstructor());
				System.out.println(courseresp.getLinkedIn());
				String Title=courseresp.getCourses().getApi().get(1).getCourseTitle();
				System.out.println(Title);
				
				//code to find the title if in random place
				List<Api> apicourses=courseresp.getCourses().getApi();
				int courseslen=apicourses.size();
				System.out.println(courseslen);
				for(int i=0;i<courseslen;i++) {
					if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
						System.out.println(  apicourses.get(i).getPrice());
					}
				}
				
				//Get the course titles from the webautomation
				
				ArrayList<String> ar=new ArrayList<String>();
				List<webAutomation> webtitles=courseresp.getCourses().getWebAutomation();
				
				int coursenos=webtitles.size();
				System.out.println(coursenos);
				
				for(int j=0;j<coursenos;j++) {
					ar.add(webtitles.get(j).getCourseTitle());
					
				}
					
			List<String>	ttles=Arrays.asList(expectedtitels);
			System.out.println(ttles);
			Assert.assertTrue(ar.equals(ttles));
				
		
		
	}

}

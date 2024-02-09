package Dailytesting;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class JiraTesting {
	
	public static void main(String[] args) {
		//Login API
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session=new SessionFilter();
		String repo=given().header("Content-Type","application/json").body("{ \"username\": \"srikanthramagiri11\", \"password\": \"AnuSri@1212\" }").log().all().
		filter(session).when().post("/rest/auth/1/session").then().extract().response().asString();
		
		
		//Add the comment
		String expectedcomment="Hi how are you ?";
		String repp=given().pathParam("key", "10001").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedcomment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().extract().response().asString();
		JsonPath jj=new JsonPath(repp);
		int id=jj.getInt("id");
		System.out.println(id);
		
		
		// add an attachment to the issue
		
		given().header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").filter(session).pathParam("key", "10001").log().all()
		.multiPart("file",new File("demo.txt")).when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		
		//Get the response
		
		String commentcounters=given().filter(session).pathParam("key", "10001").log().all().queryParam("fields", "comment").when().get("/rest/api/2/issue/{key}").
		then().log().all().extract().response().asString();
		System.out.println(commentcounters);
		
		JsonPath js5=new JsonPath(commentcounters);
		int commentscount=js5.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<commentscount;i++) {
		String commentidissue=js5.get("fields.comment.comments["+i+"].id").toString();
		
		if(commentidissue.equals(id)) {
			String message=js5.get("fields.comment.comments["+i+"].body").toString();
			System.out.println(message);
		}
		}
		
		
	}

}

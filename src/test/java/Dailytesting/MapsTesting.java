package Dailytesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Utilities.JsonConverter;

public class MapsTesting {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Add the location - update the location - get the location- delete the
		// location

		String locationupdate = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(LocationBody.Location()).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.52 (Ubuntu)")).extract().response()
				.asString();

		System.out.println(locationupdate);

		JsonPath js = new JsonPath(locationupdate);
		String placceid = js.getString("place_id");
		System.out.println(placceid);

		// update the location
		String address = "hyderabd,india";
		given().queryParam("place_id ", placceid).header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placceid + "\",\r\n" + "\"address\":\"" + address + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get the location details

		String responseloc = given().log().all().queryParam("Key", "qaclick123").queryParam("place_id", placceid).when()
				.get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath js1 = JsonConverter.returnJson(responseloc);

		String add = js1.getString(address);
		System.out.println(add);
		Assert.assertEquals(address, add);

	}

}

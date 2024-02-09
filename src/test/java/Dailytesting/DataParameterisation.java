package Dailytesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utilities.JsonConverter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DataParameterisation {

	@Test(dataProvider = "BooksData")
 public void addBook(String isbn,String aisle) {
	 
	 RestAssured.baseURI="http://216.10.245.166";
	 String resp=given().header("Content-Type","application/json").
		body(LocationBody.addNewBook(isbn,aisle)).
		when().post("/Library/Addbook.php").
		then().assertThat().statusCode(200).
		extract().response().asString();
	 JsonPath jd=JsonConverter.returnJson(resp);
	 String id=jd.get("ID");
	 System.out.println(id);
	 
 }
	@DataProvider(name="BooksData")
	
	public Object[][] getData(){
		return new Object[][] {{"Sri","1234"},{"Anu","0404"},{"AnuSri","1204"}};
	}

}

package Dailytesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class LibraryValidations {

	@Test
	public void couresTitle() {
		JsonPath jss3 = new JsonPath(LocationBody.libraryResponse());
		int size = jss3.getInt("courses.size()");
		System.out.println(size);
//	2.Print Purchase Amount
		int puramount = jss3.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount is " + puramount);

		// 6. Verify if Sum of all Course prices mat ches with Purchase Amount
		int sum = 0;

		for (int i = 0; i < size; i++) {
			String titles = jss3.getString("courses[" + i + "].title");
			int price = jss3.getInt("courses[" + i + "].price");
			int copies = jss3.getInt("courses[" + i + "].copies");
			int totalamount = price * copies;
			System.out.println(totalamount);

			sum = sum + totalamount;

		}
		System.out.println(sum);
		Assert.assertEquals(puramount, sum);

		
//		5. Print no of copies sold by RPA Course
		for (int i = 0; i <size; i++) {
			String titles = jss3.getString("courses[" + i + "].title");
			if (titles.equals("RPA")) {
				int rpacopies = jss3.getInt("courses[" + i + "].copies");
				System.out.println("Total of RPA copies sold are" + rpacopies);
			}

			int cprice = jss3.getInt("courses[" + i + "].price");
			System.out.println("Course Title available are :" + titles + " " + cprice);
		}
	}
}

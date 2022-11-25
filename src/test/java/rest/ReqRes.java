package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReqRes {
	
	
	//All expected data taken as variable
	int expectedStatusCode = 200;
	int expectedPageCount = 2;
	String eMail = "byron.fields@reqres.in";
	String url = "https://reqres.in/api/users?page=2";

	// Validating Status Code and Page Count
	@Test
	public void verifyStatusCode() {
		given().when().get(url).then().statusCode(expectedStatusCode).body("page", equalTo(expectedPageCount)).log()
				.all();
	}

	// Validating given email is present or not in JSON response
	@Test
	public void verifyEmailAddress() {
		Response response = given().when().get(url);
		JsonPath js = new JsonPath(response.asString());
		int s = js.getInt("data.size()");

		List<String> eMailList = new ArrayList<String>();
		for (int i = 0; i < s; i++) {
			eMailList.add(js.getString("data[" + i + "].email"));
		}
		Assert.assertTrue(eMailList.contains(eMail), "Email not present");

	}

}

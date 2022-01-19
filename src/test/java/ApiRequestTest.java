import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ApiRequestTest {

    @Test
    public void getRequest(){
        RestAssured.baseURI="https://reqres.in";
         String response= given().queryParam("page","2").
                when().get("api/users")
                         .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }

    @Test
    public void postRequest(){
        RestAssured.baseURI="https://reqres.in";
        String response=given().header("Content-Type","application/json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}\n")
                .when().post("/api/users")
                .then().assertThat().statusCode(201).extract().response().asString();
        System.out.println(response);

        JsonPath js =new JsonPath(response);

        String ID=js.getString("id");
        System.out.println(ID);
        Assert.assertNotNull(ID);
    }

}
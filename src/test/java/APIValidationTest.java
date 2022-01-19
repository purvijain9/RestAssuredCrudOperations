import Users.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APIValidationTest {

    @Test
    public void addPlaceWithPostRequest(){
        //given-all the request
        //when-parameters and HTTP Method
        //then-Assert the response
        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json").body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Hanumaantall house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"chips park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"Hindi\"\n" +
                        "}")
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .header("Server","Apache/2.4.18 (Ubuntu)")
                .statusLine("HTTP/1.1 200 OK");
    }

    @Test
    public void updatePlaceWithPutRequest(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response =given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json").body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

        JsonPath js = new JsonPath(response);
        String placeID=js.getString("place_id");
        System.out.println(placeID);

        //update address
        String updateAddress ="Taj Hotel";
        String responseBody=given().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\": \""+placeID+"\",\n" +
                        "\"address\":\""+updateAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().body("msg",equalTo("Address successfully updated"))
                .extract().response().asString();
        System.out.println(responseBody);

    }
}



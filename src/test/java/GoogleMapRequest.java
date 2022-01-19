import Pojo.AddPlaceInGoogleMaps;
import Pojo.Location;
import Pojo.SingleUser;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleMapRequest {

    @Test
    public void addPlace(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        AddPlaceInGoogleMaps addPlaceViaPost = new AddPlaceInGoogleMaps();
        addPlaceViaPost.setAccuracy(60);
        addPlaceViaPost.setAddress("Purvi Jain House 0901");
        addPlaceViaPost.setLanguage("Jaini");
        addPlaceViaPost.setWebsite("www.google.com");
        addPlaceViaPost.setPhone_number("123456787");
        addPlaceViaPost.setName("puriiiii");

        Location loc =new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.42736);
        addPlaceViaPost.setLocation(loc);

        List<String> myList =new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        addPlaceViaPost.setTypes(myList);

        String response=given().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(addPlaceViaPost)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);

        System.out.println(addPlaceViaPost.getAddress());
        System.out.println(addPlaceViaPost.getLocation().getLat());
        System.out.println(addPlaceViaPost.getLocation().getLng());
        System.out.println(addPlaceViaPost.getName());
        System.out.println(addPlaceViaPost.getPhone_number());
    }

    @Test
    public void getSingleUser(){
        RestAssured.baseURI="https://reqres.in/";
        SingleUser response=given().
                expect().defaultParser(Parser.JSON)
                .when().get("api/users/2").as(SingleUser.class);

       System.out.println(response.getData().getAvatar());
        System.out.println(response.getData().getEmail());
        System.out.println(response.getData().getFirst_name());
        System.out.println(response.getData().getLast_name());
        System.out.println(response.getData().getId());
//       System.out.println(response.getData().getMiddle_name());

        System.out.println(response.getSupport().getText());
        System.out.println(response.getSupport().getUrl());


    }
}

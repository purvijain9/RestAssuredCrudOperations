import Users.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJsonBody {

    @Test
    public void addBookWithPostRequest(){
        RestAssured.baseURI ="http://216.10.245.166";
        String response=given().header("Content-Type","application/json")
                .body(Payload.addBooks())
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath js =new JsonPath(response);
        String id=js.getString("ID");
        System.out.println(id);
    }
    @Test
    public void addBookWithPostRequest1(){
        RestAssured.baseURI ="http://216.10.245.166";
        String response=given().header("Content-Type","application/json")
                .body(Payload.addBooksWithParams("POSM","222","redf"))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath js =new JsonPath(response);
        String id=js.getString("ID");
        System.out.println(id);
    }

    @Test(dataProvider = "BooksDetails")
    public void addBookWithPostRequest2(String bookname,String isbn,String aisle){
        RestAssured.baseURI ="http://216.10.245.166";
        String response=given().header("Content-Type","application/json")
                .body(Payload.addBooksWithParams(bookname,isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath js =new JsonPath(response);
        String id=js.getString("ID");
        System.out.println(id);
        System.out.println("Books Added Successfully");

        given().queryParam("ID",id)
                .when().delete("/Library/DeleteBook.php")
                .then().assertThat().statusCode(200);
        System.out.println("Books deleted Successfully");
    }

    @DataProvider(name="BooksDetails")
    public Object [][] setbooksDetails()
    {
        return new Object[][] {{"POSM","272","redf"},{"POSM1","2112","redf"},{"POSM2","2162","redf"}};
    }

}


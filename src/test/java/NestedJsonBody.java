import Users.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedJsonBody {

    JsonPath js = new JsonPath(Payload.coursesResponseBody());

    @Test
    public void getCoursesCount() {
        int count = js.getInt("courses.size()");
        System.out.println(count);
    }
    @Test
    public void getPurchaseAmount() {
        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println(amount);
    }
    @Test
    public void getTitleOfFirstCourse() {
        String title = js.getString("courses[0].title");
        System.out.println(title);
    }
    @Test
    public void getTitleAndPriceOfAllCourse() {
        for (int i = 0; i < 3; i++) {
            String title = js.getString("courses[" + i + "].title");
            String price = js.getString("courses[" + i + "].price");
            System.out.println(title);
            System.out.println(price);
        }
    }
    @Test
    public void getNoOfCopiesForAnyCourse() {
        String courseName= "RPA";
        for (int i = 0; i < 3; i++) {
            String title = js.getString("courses[" + i + "].title");
            System.out.println(title);
            if(title.equals(courseName)){
                String copies = js.getString("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }
    }
    @Test
    public void getSumOfAmountOfAllCopies() {
        int sum=0;
        int count = js.getInt("courses.size()");
        for (int i = 0; i < count; i++) {
            int copies = js.getInt("courses[" + i + "].copies");
            int price = js.getInt("courses[" + i + "].price");
            int amount=copies * price;
            sum = sum + amount;
        }
        System.out.println("Total Sum Calculated "+ sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Actual Total Sum "+ purchaseAmount);
        Assert.assertEquals(purchaseAmount,sum);
    }
}



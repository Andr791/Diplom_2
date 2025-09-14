package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest extends BaseClass {

  /*  @Test
    @DisplayName("Получить список заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void listOfOrders() {
        Response response = listOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }*/
}

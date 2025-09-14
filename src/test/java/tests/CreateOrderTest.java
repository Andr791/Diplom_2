package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.Steps.*;

public class CreateOrderTest extends BaseClass {

    @Test
    @DisplayName("Создать заказ")
    @Description("С авторизацией, с ингредиентами")
    public void createOrderPozivive() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        Response responseIngredients = ingredients();
        String ingredientsOne = responseIngredients.then().extract().body().path("data[0]._id");
        String ingredientsTwo = responseIngredients.then().extract().body().path("data[1]._id");
        String[] ingredients = new String[]{ingredientsOne, ingredientsTwo};
        Response newOrder = createOrder(ingredients, accessToken);
        newOrder.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        deleteUser(accessToken);
    }

  /*  @Test
    @DisplayName("Получить список заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void listOfOrders() {
        Response response = listOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получить список заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void listOfOrders() {
        Response response = listOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получить список заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void listOfOrders() {
        Response response = listOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }*/
}

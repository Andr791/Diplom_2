package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.Steps.*;
import static steps.Steps.deleteUser;

public class ListOfOrdersTest extends BaseClass {
    private String accessToken;

    @Test
    @DisplayName("Получить список заказов пользователя")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void listOfOrders() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        accessToken = response.then().extract().body().path("accessToken");

        Response responseIngredients = ingredients();
        String ingredientsOne = responseIngredients.then().extract().body().path("data[0]._id");
        String ingredientsTwo = responseIngredients.then().extract().body().path("data[1]._id");
        String[] ingredients = new String[]{ingredientsOne, ingredientsTwo};
        createOrder(ingredients, accessToken);

        Response responseOrders = orders(accessToken);
        responseOrders.then().assertThat().body("orders[0]._id", notNullValue())
                .and()
                .statusCode(200);

    }

    @Test
    @DisplayName("Получить список заказов пользователя без авторизации")
    @Description("Проверка ошибки")
    public void listOfOrdersNegative() {
        Response responseOrders = orders("");
        responseOrders.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
    @After
    public void cleanup() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }
}

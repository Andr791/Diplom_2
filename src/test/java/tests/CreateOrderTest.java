package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static steps.Steps.*;

public class CreateOrderTest extends BaseClass {

    @Test
    @DisplayName("Создать заказ - позитивный тест")
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

    @Test
    @DisplayName("Создать заказ - негативный тест")
    @Description("с авторизацией, без ингредиентов")
    public void createOrderNegativeNoIngredients() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        String[] ingredients = new String[]{};
        Response newOrder = createOrder(ingredients, accessToken);
        newOrder.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создать заказ - негативный тест")
    @Description("с авторизацией, с неверным хешем ингредиентов")
    public void createOrderNegativeInvalidId() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        String[] ingredients = new String[]{"60d3b"};
        Response newOrder = createOrder(ingredients, accessToken);
        newOrder.then()
                .statusCode(500);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создать заказ без авторизации")
    @Description("Токен не передаем в запросе")
    public void createOrderNoAuthorization() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        Response responseIngredients = ingredients();
        String ingredientsOne = responseIngredients.then().extract().body().path("data[0]._id");
        String ingredientsTwo = responseIngredients.then().extract().body().path("data[1]._id");
        String[] ingredients = new String[]{ingredientsOne, ingredientsTwo};
        Response newOrder = createOrder(ingredients, "");
        newOrder.then().assertThat().body("success", equalTo(true)) // в документации и по факту без авторизации заказ через API создается!
                .and()
                .statusCode(200);

        deleteUser(accessToken);
    }
}

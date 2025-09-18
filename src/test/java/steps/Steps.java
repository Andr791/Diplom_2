package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.TestDataLogin;
import pojo.TestDataOrder;
import pojo.TestDataRegister;
import pojo.TestDataUpdate;

import static constante.Const.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Steps {
    @Step("Создание пользователя")
    public static Response userCreate(String email, String password, String name) {
        TestDataRegister register = new TestDataRegister(email, password, name);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(register)
                .when()
                .post(RUKA_REGISTER);
    }

    @Step("Залогиниться пользователем")
    public static Response login(String email, String password) {
        TestDataLogin testDataLogin = new TestDataLogin(email, password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(testDataLogin)
                .when()
                .post(RUKA_LOGIN);
    }

    @Step("Изменить данные пользователя")
    public static Response updateLogin(String name, String email, String authorization) {
        TestDataUpdate testDataUpdate = new TestDataUpdate(name, email);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", authorization)
                .and()
                .body(testDataUpdate)
                .when()
                .patch(RUKA_USER);
    }

    @Step("Удаление пользователя")
    public static void deleteUser(String authorization) {
        Response responseDelete =
                given()
                        .header("Authorization", authorization)
                        .delete(RUKA_USER);
        responseDelete.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(202);
    }

    @Step("Создать заказ")
    public static Response createOrder(String[] ingredients, String authorization) {
        TestDataOrder testDataOrder = new TestDataOrder(ingredients);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", authorization)
                .and()
                .body(testDataOrder)
                .when()
                .post(RUKA_ORDER);
    }

    @Step("Получение заказов конкретного пользователя")
    public static Response orders(String authorization) {
        return  given()
                .header("Authorization", authorization)
                .get(RUKA_ORDER);
    }

    @Step("Получить ингредиенты")
    public static Response ingredients() {
        return  given()
                .get(RUKA_INGREDIENTS);
    }


}

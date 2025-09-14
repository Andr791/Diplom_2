package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static steps.Steps.*;

public class LoginTest extends BaseClass {

    @Test
    @DisplayName("Позитивный тест авторизации") // имя теста
    @Description("Это проверка успешной авторизации в системе") // описание теста
    public void loginPozitive() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        userCreate(email, password, name);

        Response responseLogin = login(email, password);
        responseLogin.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        String accessToken = responseLogin.then().extract().body().path("accessToken");
        deleteUser(accessToken);
    }


    @Test
    @DisplayName("Авторизоваться с неправильным емейлом") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void errorLogin() {
        String email = "qwerty88nn99@mail.ru";
        String password = "10458617";

        Response responseLogin = login(email, password);
        responseLogin.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Неправильно указать пароль") // имя теста
    @Description("Это негативный тест на проверку нужной ошибки") // описание теста
    public void errorPassword() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String passwordWrong = "10999999";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");
        Response responseLogin = login(email, passwordWrong);
        responseLogin.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);

        deleteUser(accessToken);
    }
}

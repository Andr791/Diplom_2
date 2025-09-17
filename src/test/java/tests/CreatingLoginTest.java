package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static steps.Steps.*;

public class CreatingLoginTest extends BaseClass {
    private String accessToken;

    @Test
    @DisplayName("Позитивный тест создания логина")
    @Description("Это проверка успешного создания логина")
    public void loginCreatingPozitive() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        accessToken = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Тест создания двух одинаковых логинов")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingLoginPovtor() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        accessToken = response.then().extract().body().path("accessToken");
        Response responsePovtor = userCreate(email, password, name);
        responsePovtor.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);

    }

    @Test
    @DisplayName("Создание логина без емейл")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingLoginBezEmaila() {
        String email = "";
        String password = "10458617";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание логина без пароля")
    @Description("Это негативный тест на проверку нужной ошибки")
    public void creatingLoginBezParolya() {
        String email = "azxqwe1@mail.ru";
        String password = "";
        String name = "Andrey";
        Response response = userCreate(email, password, name);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
    @After
    public void cleanup() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }
}

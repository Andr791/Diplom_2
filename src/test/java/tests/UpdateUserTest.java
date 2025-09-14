package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static steps.Steps.*;

public class UpdateUserTest extends BaseClass {

    @Test
    @DisplayName("Позитивный тест изменения юзера") // имя теста
    @Description("Меняем имя") // описание теста
    public void updateUserNamePozitive() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        String nameNew = "Dima";

        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        Response responseUpdate = updateLogin(nameNew, email, accessToken);
        responseUpdate.then().assertThat().body("user.name", equalTo(nameNew))
                .and()
                .statusCode(200);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Позитивный тест изменения юзера") // имя теста
    @Description("Меняем емейл") // описание теста
    public void updateUserEmailPozitive() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        String emailNew = "azxqwe777@mail.ru";

        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");

        Response responseUpdate = updateLogin(name, emailNew, accessToken);
        responseUpdate.then().assertThat().body("user.email", equalTo(emailNew))
                .and()
                .statusCode(200);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Негативный тест изменения юзера") // имя теста
    @Description("Меняем имя") // описание теста
    public void updateUserNameNegative() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        String nameNew = "Dima";

        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");
        String accessTokenWrong = "";

        Response responseUpdate = updateLogin(nameNew, email, accessTokenWrong);
        responseUpdate.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Негативный тест изменения юзера") // имя теста
    @Description("Меняем емейл") // описание теста
    public void updateUserEmailNegative() {
        String email = "azxqwe1@mail.ru";
        String password = "10458617";
        String name = "Andrey";
        String emailNew = "azxqwe777@mail.ru";

        Response response = userCreate(email, password, name);
        String accessToken = response.then().extract().body().path("accessToken");
        String accessTokenWrong = "";

        Response responseUpdate = updateLogin(name, emailNew, accessTokenWrong);
        responseUpdate.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);

        deleteUser(accessToken);
    }
}

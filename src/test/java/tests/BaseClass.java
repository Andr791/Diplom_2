package tests;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseClass {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}

package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void sendRequest(RegistrationDto user) {

        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String randomLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        return login;
    }

    public static String randomPass() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        return password;
    }

    public static RegistrationDto getNewUser(String status){
        RegistrationDto user = new RegistrationDto(randomLogin(),randomPass(),status);
        return user;
    }

    public static RegistrationDto getRegisteredUser (String status){
        RegistrationDto registeredUser = getNewUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }


    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }

}


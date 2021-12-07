package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");

    }

    @Test
    void shouldLoginWithActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login']  input").setValue(registeredUser.getLogin());
        $("[data-test-id='password']  input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }
    @Test
    void shouldLoginWithBlockedUser(){
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Пользователь заблокирован"));

    }
    @Test
    void shouldLoginIfUserNotRegistered(){
        var newUser = getNewUser("active");
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));
    }
    @Test
    void shouldLoginWithWrongPassword(){
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = randomPass();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));

    }
    @Test
    void shouldLoginWithWrongLogin(){
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = randomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));

    }
    @Test
    void shouldLoginWithoutLogin(){
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub").shouldBe(visible).shouldHave(text(
                "Поле обязательно для заполнения"));

    }
    @Test
    void shouldLoginWithoutPassword(){
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'] .input__sub").shouldBe(visible).shouldHave(text(
                "Поле обязательно для заполнения"));

    }

    }



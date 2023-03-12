package ru.netology.web.steps;


import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class TemplateSteps {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VerificationPage verificationPage;
    private TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        loginPage = open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("с проверочным кодом {string}")
    public void setValidCode(String verificationCode) {
        verificationPage = new VerificationPage();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void successfulTransfer(String amount, String cardFrom, int cardOn) {
        transferPage = dashboardPage.transferPage(cardOn);
        transferPage.validTransfer(cardFrom, amount);
    }

    @Тогда("баланс его {} карты из списка на главной странице должен стать {} рублей")
    public void matchBalance(int cardOn, int expectedBalance) {
        dashboardPage.reloadBalance();
        int actualBalance = dashboardPage.getCardBalance(cardOn);
        Assertions.assertEquals(actualBalance, expectedBalance);
    }
}

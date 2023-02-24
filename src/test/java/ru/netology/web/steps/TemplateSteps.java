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
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        open("http://localhost:9999");
        loginPage = new LoginPage();
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("с проверочным кодом {string}")
    public void setValidCode(String verificationCode) {
        verificationPage = new VerificationPage();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void successfulTransfer(String amount, String cardFrom, String indexCardTo) {
        transferPage = dashboardPage.transferPage(0);
        transferPage.validTransfer(cardFrom, amount);
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void matchBalance(String indexCard, String expectedBalance) {
        dashboardPage.reloadBalance();
        int actualBalance = dashboardPage.getCardBalance(0);
        Assertions.assertEquals(actualBalance, Integer.parseInt(expectedBalance));
    }
}

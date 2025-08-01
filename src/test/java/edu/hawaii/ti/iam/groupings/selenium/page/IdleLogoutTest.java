package edu.hawaii.ti.iam.groupings.selenium.page;

import com.codeborne.selenide.WebDriverRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

import edu.hawaii.ti.iam.groupings.selenium.core.User;

@SpringBootTest
public class IdleLogoutTest extends AbstractTestBase {

    private WebDriver driver;
    private User user;

    public IdleLogoutTest() {
        super();
    }

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        WebDriverRunner.setWebDriver(new ChromeDriver());
    }

    @AfterAll
    public static void close() {
        closeWebDriver();
    }

    @BeforeEach
    public void setUp() {
        open(property.value("app.url.login"));
        driver = WebDriverRunner.getWebDriver();

        user = createUser("student");
        loginWith(driver, user);

        open(property.value("app.url.home") + "/groupings");
        $(by("id", "overlay")).should(disappear, Duration.ofSeconds(80));
        $(byText("testiwtb-many")).click();
        $(by("id", "sel")).shouldBe(visible);
    }

//    @Disabled
    // When running this test make testiwtb owner of testiwtb-many and then run and after running remove them as owner
    @Test
    public void idleLogout() {
        Duration idleModal = Duration.ofSeconds(25 * 60 + 30);
        Duration logoutTimer = Duration.ofSeconds(5 * 60 + 30);
        $("body > div.modal.fade.ng-scope.ng-isolate-scope.in > div > div").should(appear, idleModal);
        $(byText("Login Here")).should(exist, logoutTimer);
    }
}

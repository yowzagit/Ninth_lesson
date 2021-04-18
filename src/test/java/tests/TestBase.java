package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.*;

public class TestBase {
    @BeforeAll
    static void setup() {
        //System.out.println("a");
        //System.out.println(System.getProperties());
        //System.out.println(System.getProperty("a"));

        addListener("AllureSelenide", new AllureSelenide());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud:4444/wd/hub/";
        Configuration.startMaximized = true;

        String remoteWebDriver = System.getProperty("remote.web.driver");
        if (remoteWebDriver != null) Configuration.remote = remoteWebDriver;
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        
        String videoStorage = System.getProperty("video.storage");
        if (videoStorage != null) attachVideo();
        closeWebDriver();
    }
}
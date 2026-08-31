package stepdefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {

        driver = new FirefoxDriver();

        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        System.out.println("========== TEST STARTED ==========");
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        System.out.println("========== TEST FINISHED ==========");
    }
}
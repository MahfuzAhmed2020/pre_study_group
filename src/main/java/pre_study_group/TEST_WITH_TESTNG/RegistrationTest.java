package pre_study_group.TEST_WITH_TESTNG;


import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest {

    WebDriver driver;

    String email;
    String password = "Test@123";

    String trackingFromCheckout;


    // =========================================================
    // BEFORE TEST
    // =========================================================

    @BeforeMethod
    public void setUp() {

        driver = new FirefoxDriver();

        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        System.out.println("=========================================");
        System.out.println("TEST STARTED");
        System.out.println("=========================================");
    }


    // =========================================================
    // TEST
    // =========================================================

    @Test
    public void registrationEndToEndTest() {

        // =====================================================
        // OPEN REGISTRATION PAGE
        // =====================================================

        driver.get(
            "http://localhost:8081/public/index.php"
        );

        System.out.println(
            "Page Title: " + driver.getTitle()
        );


        // =====================================================
        // GENERATE RANDOM USER
        // =====================================================

        Random random = new Random();

        String characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder name = new StringBuilder();

        for (int i = 0; i < 5; i++) {

            name.append(
                characters.charAt(
                    random.nextInt(characters.length())
                )
            );
        }

        email = name.toString() + "@gmail.com";


        // =====================================================
        // REGISTRATION
        // =====================================================

        driver.findElement(
            By.id("first_name")
        ).sendKeys(name.toString());

        driver.findElement(
            By.id("last_name")
        ).sendKeys(name.toString());

        driver.findElement(
            By.id("email")
        ).sendKeys(email);

        driver.findElement(
            By.id("password")
        ).sendKeys(password);

        driver.findElement(
            By.id("confirm_password")
        ).sendKeys(password);

        driver.findElement(
            By.id("agree_terms")
        ).click();

        driver.findElement(
            By.xpath("/html/body/div/div[2]/form/button")
        ).click();

        System.out.println(
            "Registration completed"
        );


        // =====================================================
        // GO TO LOGIN
        // =====================================================

        driver.findElement(
            By.xpath("/html/body/div/div[2]/div[1]/a")
        ).click();

        System.out.println(
            "Login page opened"
        );


        // =====================================================
        // LOGIN
        // =====================================================

        driver.findElement(
            By.id("email")
        ).sendKeys(email);

        driver.findElement(
            By.id("password")
        ).sendKeys(password);

        driver.findElement(
            By.id("loginBtn")
        ).click();

        System.out.println(
            "Login completed"
        );


        // =====================================================
        // VERIFY LOGIN
        // =====================================================

        String pageText =
            driver.findElement(
                By.xpath("/html/body/div/h1")
            ).getText();

        System.out.println(
            "Page heading: " + pageText
        );

        Assert.assertFalse(
            pageText.isEmpty(),
            "Login page heading should not be empty"
        );


        // =====================================================
        // PRODUCTS
        // =====================================================

        driver.findElement(
            By.xpath("//*[@id=\"products\"]/div[1]/button")
        ).click();

        System.out.println(
            "Added: Wireless Earbuds"
        );


        driver.findElement(
            By.xpath("//*[@id=\"products\"]/div[2]/button")
        ).click();

        System.out.println(
            "Added: Bluetooth Speaker"
        );


        driver.findElement(
            By.xpath("//*[@id=\"products\"]/div[3]/button")
        ).click();

        System.out.println(
            "Added: Smart Phone Case"
        );


        driver.findElement(
            By.xpath("//*[@id=\"products\"]/div[4]/button")
        ).click();

        System.out.println(
            "Added: Charging Cable Set"
        );


        driver.findElement(
            By.xpath("//*[@id=\"products\"]/div[5]/button")
        ).click();

        System.out.println(
            "Added: Fitness Tracker Band"
        );


        // =====================================================
        // SELECT CARD
        // =====================================================

        Select card = new Select(
            driver.findElement(
                By.id("card")
            )
        );

        card.selectByValue(
            "4111111111111111"
        );

        System.out.println(
            "Card selected"
        );


        // =====================================================
        // SELECT ADDRESS
        // =====================================================

        Select address = new Select(
            driver.findElement(
                By.id("address")
            )
        );

        address.selectByVisibleText(
            "456 Oak Avenue, Los Angeles, CA 90001, USA"
        );

        System.out.println(
            "Delivery address selected"
        );


        // =====================================================
        // CHECKOUT
        // =====================================================

        driver.findElement(
            By.id("Checkout")
        ).click();

        System.out.println(
            "Checkout button clicked"
        );


        // =====================================================
        // ALERT
        // =====================================================

        Alert alert =
            driver.switchTo().alert();

        String alertText =
            alert.getText();

        System.out.println(
            "Alert Message: " + alertText
        );


        // =====================================================
        // GET TRACKING NUMBER
        // =====================================================

        trackingFromCheckout =
            alertText
                .split("Tracking Number:")[1]
                .trim();

        System.out.println(
            "Tracking From Checkout: "
            + trackingFromCheckout
        );


        Assert.assertFalse(
            trackingFromCheckout.isEmpty(),
            "Tracking number should not be empty"
        );


        alert.accept();

        System.out.println(
            "Alert accepted"
        );


        // =====================================================
        // MY ORDERS
        // =====================================================

        System.out.println(
            "========================================="
        );

        System.out.println(
            "MY ORDERS"
        );


        List<WebElement> orders =
            driver.findElements(
                By.cssSelector("#orders .order")
            );


        System.out.println(
            "Number of orders: "
            + orders.size()
        );


        // =====================================================
        // VERIFY ORDER EXISTS
        // =====================================================

        Assert.assertTrue(
            orders.size() > 0,
            "At least one order should exist"
        );


        // =====================================================
        // GET LATEST ORDER
        // =====================================================

        WebElement latestOrder =
            orders.get(0);


        // =====================================================
        // ORDER NUMBER
        // =====================================================

        String orderNumber =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Order')]]"
                )
            )
            .getText()
            .replace("Order #", "")
            .trim();


        // =====================================================
        // TRACKING NUMBER
        // =====================================================

        String trackingNumber =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Tracking')]]"
                )
            )
            .getText()
            .replace("Tracking:", "")
            .trim();


        // =====================================================
        // STATUS
        // =====================================================

        String status =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Status')]]"
                )
            )
            .getText()
            .replace("Status:", "")
            .trim();


        // =====================================================
        // TOTAL
        // =====================================================

        String total =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Total')]]"
                )
            )
            .getText()
            .replace("Total:", "")
            .trim();


        // =====================================================
        // PRINT RESULTS
        // =====================================================

        System.out.println(
            "Latest Order #: "
            + orderNumber
        );

        System.out.println(
            "Latest Tracking: "
            + trackingNumber
        );

        System.out.println(
            "Latest Status: "
            + status
        );

        System.out.println(
            "Latest Total: "
            + total
        );


        // =====================================================
        // TESTNG ASSERTIONS
        // =====================================================

        Assert.assertFalse(
            orderNumber.isEmpty(),
            "Order number should not be empty"
        );


        Assert.assertEquals(
            trackingNumber,
            trackingFromCheckout,
            "Tracking number should match checkout tracking number"
        );


        Assert.assertEquals(
            status,
            "Processing",
            "Order status should be Processing"
        );


        Assert.assertFalse(
            total.isEmpty(),
            "Order total should not be empty"
        );


        System.out.println(
            "========================================="
        );

        System.out.println(
            "TEST PASSED"
        );

        System.out.println(
            "========================================="
        );
    }


    // =========================================================
    // AFTER TEST
    // =========================================================

    @AfterMethod
    public void tearDown() {

        if (driver != null) {

            driver.quit();
        }

        System.out.println(
            "Browser closed"
        );
    }
}
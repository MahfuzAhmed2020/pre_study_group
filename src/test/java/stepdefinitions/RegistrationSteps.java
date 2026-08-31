package stepdefinitions;

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

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationSteps {

    WebDriver driver;

    String email;

    String password = "Test@123";

    String trackingFromCheckout;


    @Given("I open the registration page")
    public void i_open_the_registration_page() {

        driver = new FirefoxDriver();

        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get(
            "http://localhost:8081/public/index.php"
        );

        System.out.println(
            "Registration page opened"
        );
    }


    @When("I register a new user")
    public void i_register_a_new_user() {

        Random random = new Random();

        String characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder name =
            new StringBuilder();

        for (int i = 0; i < 5; i++) {

            name.append(
                characters.charAt(
                    random.nextInt(
                        characters.length()
                    )
                )
            );
        }

        email =
            name.toString() + "@gmail.com";


        driver.findElement(
            By.id("first_name")
        ).sendKeys(
            name.toString()
        );


        driver.findElement(
            By.id("last_name")
        ).sendKeys(
            name.toString()
        );


        driver.findElement(
            By.id("email")
        ).sendKeys(
            email
        );


        driver.findElement(
            By.id("password")
        ).sendKeys(
            password
        );


        driver.findElement(
            By.id("confirm_password")
        ).sendKeys(
            password
        );


        driver.findElement(
            By.id("agree_terms")
        ).click();


        driver.findElement(
            By.xpath(
                "/html/body/div/div[2]/form/button"
            )
        ).click();


        System.out.println(
            "Registration completed"
        );
    }


    @When("I login with the registered user")
    public void i_login_with_the_registered_user() {

        driver.findElement(
            By.xpath(
                "/html/body/div/div[2]/div[1]/a"
            )
        ).click();


        driver.findElement(
            By.id("email")
        ).sendKeys(
            email
        );


        driver.findElement(
            By.id("password")
        ).sendKeys(
            password
        );


        driver.findElement(
            By.id("loginBtn")
        ).click();


        System.out.println(
            "Login completed"
        );
    }


    @When("I add five products to the cart")
    public void i_add_five_products_to_the_cart() {

        driver.findElement(
            By.xpath(
                "//*[@id=\"products\"]/div[1]/button"
            )
        ).click();


        driver.findElement(
            By.xpath(
                "//*[@id=\"products\"]/div[2]/button"
            )
        ).click();


        driver.findElement(
            By.xpath(
                "//*[@id=\"products\"]/div[3]/button"
            )
        ).click();


        driver.findElement(
            By.xpath(
                "//*[@id=\"products\"]/div[4]/button"
            )
        ).click();


        driver.findElement(
            By.xpath(
                "//*[@id=\"products\"]/div[5]/button"
            )
        ).click();


        System.out.println(
            "Five products added"
        );
    }


    @When("I select the payment card")
    public void i_select_the_payment_card() {

        Select card =
            new Select(
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
    }


    @When("I select the delivery address")
    public void i_select_the_delivery_address() {

        Select address =
            new Select(
                driver.findElement(
                    By.id("address")
                )
            );

        address.selectByVisibleText(
            "456 Oak Avenue, Los Angeles, CA 90001, USA"
        );

        System.out.println(
            "Address selected"
        );
    }


    @When("I checkout")
    public void i_checkout() {

        driver.findElement(
            By.id("Checkout")
        ).click();


        Alert alert =
            driver.switchTo().alert();


        String alertText =
            alert.getText();


        System.out.println(
            "Alert: " + alertText
        );


        trackingFromCheckout =
            alertText
                .split("Tracking Number:")[1]
                .trim();


        Assert.assertFalse(
            trackingFromCheckout.isEmpty(),
            "Tracking number should not be empty"
        );


        alert.accept();


        System.out.println(
            "Checkout completed"
        );
    }


    @Then("a tracking number should be generated")
    public void a_tracking_number_should_be_generated() {

        Assert.assertNotNull(
            trackingFromCheckout,
            "Tracking number should be generated"
        );


        Assert.assertFalse(
            trackingFromCheckout.isEmpty(),
            "Tracking number should not be empty"
        );
    }


    @Then("the order should have status {string}")
    public void the_order_should_have_status(
            String expectedStatus) {

        List<WebElement> orders =
            driver.findElements(
                By.cssSelector(
                    "#orders .order"
                )
            );


        Assert.assertTrue(
            orders.size() > 0,
            "At least one order should exist"
        );


        WebElement latestOrder =
            orders.get(0);


        String status =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Status')]]"
                )
            )
            .getText()
            .replace(
                "Status:",
                ""
            )
            .trim();


        Assert.assertEquals(
            status,
            expectedStatus
        );


        String trackingNumber =
            latestOrder.findElement(
                By.xpath(
                    ".//p[b[contains(text(),'Tracking')]]"
                )
            )
            .getText()
            .replace(
                "Tracking:",
                ""
            )
            .trim();


        Assert.assertEquals(
            trackingNumber,
            trackingFromCheckout,
            "Tracking numbers should match"
        );


        System.out.println(
            "Order status: " + status
        );

        System.out.println(
            "Tracking number: " + trackingNumber
        );
        
    }


}
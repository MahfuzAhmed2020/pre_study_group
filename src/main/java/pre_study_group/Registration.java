package pre_study_group;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration; 
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.openqa.selenium.support.ui.Select;



public class Registration {

    

    static WebDriver dr = null;

    // =========================================================
    // OPEN BROWSER
    // =========================================================

    public static WebDriver openBrowser(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {

            dr = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {

            dr = new FirefoxDriver();

        } else {

            throw new IllegalArgumentException(
                    "Browser must be chrome or firefox"
            );
        }

        return dr;
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args)
            throws InterruptedException, IOException {

        // =====================================================
        // TEST RESULT registration
        // =====================================================

        String resultsFolder =
                System.getProperty("user.dir") + "\\test-results";

        File folder = new File(resultsFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String resultsFile =
                resultsFolder + "\\registration-test-result.txt";

        String tempFile =
                resultsFolder + "\\registration-current-result.txt";

        // Save original Eclipse console
        PrintStream console = System.out;

        // Create temporary file for this execution
        PrintStream file = new PrintStream(
                new FileOutputStream(tempFile)
        );

        // Send output to BOTH Eclipse console and file
        System.setOut(new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {

                console.write(b);
                file.write(b);
            }

        }));

        // =====================================================
        // registration TEST START
        // =====================================================

        System.out.println("=========================================");
        System.out.println("TEST EXECUTION STARTED");
        System.out.println("=========================================");

        WebDriver driver = null;

        try {

            // =================================================
            // OPEN firefox
            // =================================================

            driver = openBrowser("firefox");

            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));

            driver.manage().window().maximize();

            // =================================================
            // registration PAGE
            // =================================================

            driver.get(
                    "http://localhost:8081/public/index.php"
            );

            System.out.println(
                    "Page Title: " + driver.getTitle()
            );

            // =================================================
            // registration
            // =================================================
            System.out.println(
                    "Page Title: " + driver.getTitle()
            );
            
            // =================================================
            // add random name
            // =================================================        

            Random random = new Random();

            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder name = new StringBuilder();

            for (int i = 0; i < 5; i++) {
               name.append(characters.charAt(random.nextInt(characters.length())));
                }

            driver.findElement(
                    By.xpath("//*[@id=\"first_name\"]")
                            ).sendKeys(name.toString());

            driver.findElement(
                By.xpath("//*[@id=\"last_name\"]")
                        ).sendKeys(name.toString());

            driver.findElement(
                    By.xpath("//*[@id=\"email\"]")
            ).sendKeys(name.toString() + "@gmail.com"); //d@gmail.com, x, r, ok, k, u, pre, n, 

            driver.findElement(
                    By.xpath("//*[@id=\"password\"]")
            ).sendKeys("Test@123");

  
             driver.findElement(
                    By.xpath("//*[@id=\"confirm_password\"]")
                            ).sendKeys("Test@123");
            
            

            driver.findElement(By.xpath("//*[@id=\"agree_terms\"]")).click();
            driver.findElement(By.xpath("/html/body/div/div[2]/form/button")).click();
        
            System.out.println("registration completed");
            System.out.println("Clicking on login link to go to login page");

            driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/a")).click();

     

            // =================================================
            // LOGIN
            // =================================================

            driver.findElement(
                    By.xpath("//*[@id=\"email\"]")
            ).sendKeys(name.toString() + "@gmail.com"); //d@gmail.com, x, r, ok, k, u, pre, n, 

            driver.findElement(
                    By.xpath("//*[@id=\"password\"]")
            ).sendKeys("Test@123");

            driver.findElement(
                    By.xpath("//*[@id=\"loginBtn\"]")
            ).click();

            System.out.println("Login completed");

            String text = driver.findElement(By.xpath("/html/body/div/h1")).getText();
            System.out.println(text);

            // =================================================
            // PRODUCTS
            // =================================================

            Thread.sleep(4000);

            driver.findElement(
                    By.xpath("//*[@id=\"products\"]/div[1]/button")
            ).click();

            System.out.println(
                    "Added: Wireless Earbuds"
            );

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//*[@id=\"products\"]/div[2]/button")
            ).click();

            System.out.println(
                    "Added: Bluetooth Speaker"
            );

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//*[@id=\"products\"]/div[3]/button")
            ).click();

            System.out.println(
                    "Added: Smart Phone Case"
            );

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//*[@id=\"products\"]/div[4]/button")
            ).click();

            System.out.println(
                    "Added: Charging Cable Set"
            );

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//*[@id=\"products\"]/div[5]/button")
            ).click();

            System.out.println(
                    "Added: Fitness Tracker Band"
            );

            // =================================================
            // SELECT CARD
            // =================================================

            Thread.sleep(2000);

            Select card = new Select(
                    driver.findElement(
                            By.xpath("//*[@id=\"card\"]")
                    )
            );

            card.selectByValue("4111111111111111");

            System.out.println(
                    "Card selected"
            );

            // =================================================
            // SELECT ADDRESS
            // =================================================

            Thread.sleep(4000);

            Select address = new Select(
                    driver.findElement(
                            By.xpath("//*[@id=\"address\"]")
                    )
            );

            address.selectByVisibleText(
                    "456 Oak Avenue, Los Angeles, CA 90001, USA"
            );

            System.out.println(
                    "Delivery address selected"
            );

            // =================================================
            // CHECKOUT
            // =================================================

            Thread.sleep(4000);

            driver.findElement(
                    By.xpath("//*[@id=\"Checkout\"]")
            ).click();

            System.out.println(
                    "Checkout button clicked"
            );

            // =================================================
            // HANDLE ALERT
            // =================================================

            Thread.sleep(4000);

            Alert alert = driver.switchTo().alert();

            String alertText = alert.getText();

            System.out.println(
                    "Alert Message:"
            );

            System.out.println(
                    alertText
            );

            // =================================================
            // GET TRACKING NUMBER FROM CHECKOUT
            // =================================================

            String trackingFromCheckout =
                    alertText
                            .split("Tracking Number:")[1]
                            .trim();

            System.out.println(
                    "Tracking From Checkout: "
                            + trackingFromCheckout
            );

            // Click OK
            alert.accept();

            System.out.println(
                    "Alert accepted"
            );

            // =================================================
            // MY ORDERS
            // =================================================

            Thread.sleep(2000);

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "MY ORDERS"
            );

            System.out.println(
                    "Page Title: " + driver.getTitle()
            );

            // =================================================
            // GET ALL INDIVIDUAL ORDERS
            // =================================================

            List<WebElement> orders =
                    driver.findElements(
                            By.cssSelector("#orders .order")
                    );

            System.out.println(
                    "Number of orders: "
                            + orders.size()
            );

            // =================================================
            // DISPLAY ALL ORDERS
            // =================================================

            for (WebElement order : orders) {

                System.out.println(
                        "===================="
                );

                System.out.println(
                        order.getText()
                );
            }

            System.out.println(
                    "========================================="
            );

            // =================================================
            // GET LATEST ORDER
            // =================================================

            if (orders.size() == 0) {

                System.out.println(
                        "FAIL: No orders found"
                );

            } else {

                // Assuming newest order is displayed first
                WebElement latestOrder =
                        orders.get(0);

                // =============================================
                // GET ORDER NUMBER
                // =============================================

                String orderNumber =
                        latestOrder.findElement(
                                By.xpath(
                                        ".//p[b[contains(text(),'Order')]]"
                                )
                        )
                        .getText()
                        .replace("Order #", "")
                        .trim();

                // =============================================
                // GET TRACKING NUMBER
                // =============================================

                String trackingNumber =
                        latestOrder.findElement(
                                By.xpath(
                                        ".//p[b[contains(text(),'Tracking')]]"
                                )
                        )
                        .getText()
                        .replace("Tracking:", "")
                        .trim();

                // =============================================
                // GET STATUS
                // =============================================

                String status =
                        latestOrder.findElement(
                                By.xpath(
                                        ".//p[b[contains(text(),'Status')]]"
                                )
                        )
                        .getText()
                        .replace("Status:", "")
                        .trim();

                // =============================================
                // GET TOTAL
                // =============================================

                String total =
                        latestOrder.findElement(
                                By.xpath(
                                        ".//p[b[contains(text(),'Total')]]"
                                )
                        )
                        .getText()
                        .replace("Total:", "")
                        .trim();

                // =============================================
                // PRINT LATEST ORDER
                // =============================================

                System.out.println(
                        "LATEST ORDER"
                );

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

                // =============================================
                // VERIFY ORDER NUMBER
                // =============================================

                System.out.println(
                        "========================================="
                );

                if (!orderNumber.isEmpty()) {

                    System.out.println(
                            "PASS: Latest Order # is "
                                    + orderNumber
                    );

                } else {

                    System.out.println(
                            "FAIL: Order number is empty"
                    );
                }

                // =============================================
                // VERIFY TRACKING NUMBER
                // =============================================

                if (trackingFromCheckout.equals(
                        trackingNumber)) {

                    System.out.println(
                            "PASS: Tracking number matches"
                    );

                    System.out.println(
                            "Checkout Tracking: "
                                    + trackingFromCheckout
                    );

                    System.out.println(
                            "Orders Tracking:   "
                                    + trackingNumber
                    );

                } else {

                    System.out.println(
                            "FAIL: Tracking number does NOT match"
                    );

                    System.out.println(
                            "Expected: "
                                    + trackingFromCheckout
                    );

                    System.out.println(
                            "Actual:   "
                                    + trackingNumber
                    );
                }

                // =============================================
                // VERIFY STATUS
                // =============================================

                if (status.equalsIgnoreCase(
                        "Processing")) {

                    System.out.println(
                            "PASS: Order status is Processing"
                    );

                } else {

                    System.out.println(
                            "FAIL: Unexpected order status: "
                                    + status
                    );
                }

                // =============================================
                // VERIFY TOTAL EXISTS
                // =============================================

                if (!total.isEmpty()) {

                    System.out.println(
                            "PASS: Order total is "
                                    + total
                    );

                } else {

                    System.out.println(
                            "FAIL: Order total is empty"
                    );
                }
            }

            // =================================================
            // TEST COMPLETED
            // =================================================

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "TEST EXECUTION COMPLETED"
            );

            System.out.println(
                    "========================================="
            );

        } catch (Exception e) {
            System.out.println(
                    "Exception occurred: " + e.getMessage()
            );
        }  // =================================================
            // NEW RESULT ON TOP
            // =================================================
    }}
            








    

    


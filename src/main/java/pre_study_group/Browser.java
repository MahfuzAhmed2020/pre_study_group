package pre_study_group;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Browser {

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
        // TEST RESULT LOGGING
        // =====================================================

        String resultsFolder =
                System.getProperty("user.dir") + "\\test-results";

        File folder = new File(resultsFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String resultsFile =
                resultsFolder + "\\test-result.txt";

        String tempFile =
                resultsFolder + "\\current-result.txt";

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
        // TEST START
        // =====================================================

        System.out.println("=========================================");
        System.out.println("TEST EXECUTION STARTED");
        System.out.println("=========================================");

        WebDriver driver = null;

        try {

            // =================================================
            // OPEN CHROME
            // =================================================

            driver = openBrowser("chrome");

            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));

            driver.manage().window().maximize();

            // =================================================
            // LOGIN PAGE
            // =================================================

            driver.get(
                    "http://localhost:8081/public/login.php"
            );

            System.out.println(
                    "Page Title: " + driver.getTitle()
            );

            // =================================================
            // LOGIN
            // =================================================

            driver.findElement(
                    By.xpath("//*[@id=\"email\"]")
            ).sendKeys("d@gmail.com");

            driver.findElement(
                    By.xpath("//*[@id=\"password\"]")
            ).sendKeys("Test@123");

            driver.findElement(
                    By.xpath("//*[@id=\"loginBtn\"]")
            ).click();

            System.out.println("Login completed");

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

            Thread.sleep(2000);

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

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//*[@id=\"Checkout\"]")
            ).click();

            System.out.println(
                    "Checkout button clicked"
            );

            // =================================================
            // HANDLE ALERT
            // =================================================

            Thread.sleep(2000);

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

        } finally {

            // =================================================
            // CLOSE BROWSER
            // =================================================

            if (driver != null) {

                // Uncomment when you don't want browser to stay open
                // driver.quit();
            }

            // =================================================
            // CLOSE CURRENT RESULT FILE
            // =================================================

            file.close();

            // =================================================
            // READ NEW RESULT
            // =================================================

            String newResult =
                    Files.readString(
                            new File(tempFile).toPath()
                    );

            // =================================================
            // READ OLD RESULTS
            // =================================================

            String oldResult = "";

            File oldFile =
                    new File(resultsFile);

            if (oldFile.exists()) {

                oldResult =
                        Files.readString(
                                oldFile.toPath()
                        );
            }

            // =================================================
            // NEW RESULT ON TOP
            // =================================================

            String combinedResult =
                    "=========================================\n"
                    + "NEW TEST EXECUTION\n"
                    + "=========================================\n"
                    + newResult
                    + "\n\n"
                    + oldResult;

            // =================================================
            // WRITE RESULT FILE
            // =================================================

            Files.writeString(
                    oldFile.toPath(),
                    combinedResult
            );

            // =================================================
            // DELETE TEMP FILE
            // =================================================

            new File(tempFile).delete();

            // =================================================
            // RESTORE ECLIPSE CONSOLE
            // =================================================

            System.setOut(console);

            System.out.println(
                    "Test result saved to: "
                            + resultsFile
            );
        }
    }
}
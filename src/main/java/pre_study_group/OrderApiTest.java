package pre_study_group;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;

public class OrderApiTest {

    public static void main(String[] args) throws IOException {

        // =====================================================
        // API TEST RESULT LOGGING
        // =====================================================

        String resultsFolder =
                System.getProperty("user.dir") + "\\test-results";

        File folder = new File(resultsFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // FINAL RESULT FILE
        String resultsFile =
                resultsFolder + "\\API-ORDER-RESULT.TXT";

        // TEMPORARY RESULT FILE
        String tempFile =
                resultsFolder + "\\ORDER-CURRENT-RESULT.TXT";

        // Save original console
        PrintStream console = System.out;

        // Create temporary file
        PrintStream file =
                new PrintStream(
                        new FileOutputStream(tempFile)
                );

        // Send output to BOTH console and file
        System.setOut(new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {

                console.write(b);
                file.write(b);
            }

        }));

        try {

            // =====================================================
            // CONFIGURATION
            // =====================================================

            String sessionId =
                    "23788d90ae2e5d1178b67355adc0a3f6";

            String email = "mahfuz@gmail.com";
            String password = "Test@123";

            // =====================================================
            // TEST START
            // =====================================================

            System.out.println("=========================================");
            System.out.println("ORDER API TEST STARTED");
            System.out.println("=========================================");

            // =====================================================
            // 1. LOGIN
            // =====================================================

            System.out.println("\n========== LOGIN ==========");

            Response loginResponse =
                    given()
                        .baseUri("http://localhost:8081")
                        .contentType("application/json")
                        .cookie("PHPSESSID", sessionId)

                        .body("""
                            {
                                "email": "%s",
                                "password": "%s"
                            }
                            """.formatted(
                                email,
                                password
                            ))

                    .when()
                        .post("/api/login_api.php");

            System.out.println(
                    "Login Status: "
                            + loginResponse.getStatusCode()
            );

            System.out.println(
                    loginResponse.getBody().asPrettyString()
            );

            if (loginResponse.getStatusCode() == 200) {

                System.out.println(
                        "PASS: Login successful"
                );

            } else {

                System.out.println(
                        "FAIL: Login failed"
                );

                return;
            }

            // =====================================================
            // 2. ADD PRODUCTS TO CART
            // =====================================================

            addProduct(sessionId, 1);
            addProduct(sessionId, 2);
            addProduct(sessionId, 3);
            addProduct(sessionId, 4);
            addProduct(sessionId, 5);

            // =====================================================
            // 3. GET CART
            // =====================================================

            System.out.println("\n========== GET CART ==========");

            Response cartResponse =
                    given()
                        .baseUri("http://localhost:8081")
                        .cookie("PHPSESSID", sessionId)

                    .when()
                        .get("/api/cart_api.php");

            System.out.println(
                    "Cart Status: "
                            + cartResponse.getStatusCode()
            );

            System.out.println(
                    cartResponse.asPrettyString()
            );

            // =====================================================
            // 4. CHECKOUT
            // =====================================================

            System.out.println("\n========== CHECKOUT ==========");

            Response checkoutResponse =
                    given()
                        .baseUri("http://localhost:8081")
                        .contentType("application/json")
                        .cookie("PHPSESSID", sessionId)

                        .body("""
                            {
                                "card_number": "4222222222222222",
                                "address_id": 5
                            }
                            """)

                    .when()
                        .post("/api/checkout_api.php");

            System.out.println(
                    "Checkout Status: "
                            + checkoutResponse.getStatusCode()
            );

            System.out.println(
                    checkoutResponse.asPrettyString()
            );

            // =====================================================
            // 5. EXTRACT TRACKING NUMBER
            // =====================================================

            String trackingNumber = null;

            try {

                trackingNumber =
                        checkoutResponse
                                .jsonPath()
                                .getString("tracking_number");

            } catch (Exception e) {

                System.out.println(
                        "Could not extract tracking_number from JSON."
                );
            }

            System.out.println(
                    "Tracking Number: "
                            + trackingNumber
            );

            // =====================================================
            // 6. TRACK ORDER
            // =====================================================

            if (trackingNumber != null &&
                    !trackingNumber.isEmpty()) {

                System.out.println(
                        "\n========== TRACK ORDER =========="
                );

                Response trackingResponse =
                        given()
                            .baseUri("http://localhost:8081")
                            .contentType("application/json")
                            .cookie("PHPSESSID", sessionId)

                            .body("""
                                {
                                    "tracking_number": "%s"
                                }
                                """.formatted(
                                    trackingNumber
                                ))

                        .when()
                            .post("/api/track_order_api.php");

                System.out.println(
                        "Tracking Status: "
                                + trackingResponse.getStatusCode()
                );

                System.out.println(
                        trackingResponse.asPrettyString()
                );

            } else {

                System.out.println(
                        "FAIL: Tracking number was not returned."
                );
            }

            // =====================================================
            // TEST COMPLETED
            // =====================================================

            System.out.println(
                    "\n========================================="
            );

            System.out.println(
                    "ORDER API TEST COMPLETED"
            );

            System.out.println(
                    "========================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "FAIL: Exception occurred"
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );

        } finally {

            // =====================================================
            // CLOSE CURRENT RESULT FILE
            // =====================================================

            file.close();

            // =====================================================
            // READ NEW RESULT
            // =====================================================

            String newResult =
                    Files.readString(
                            new File(tempFile).toPath()
                    );

            // =====================================================
            // READ OLD RESULTS
            // =====================================================

            String oldResult = "";

            File oldFile =
                    new File(resultsFile);

            if (oldFile.exists()) {

                oldResult =
                        Files.readString(
                                oldFile.toPath()
                        );
            }

            // =====================================================
            // PUT NEW RESULT ON TOP
            // =====================================================

            String combinedResult =
                    "=========================================\n"
                    + "NEW ORDER API TEST EXECUTION\n"
                    + "=========================================\n"
                    + newResult
                    + "\n\n"
                    + oldResult;

            // =====================================================
            // WRITE ORDER-RESULT.TXT
            // =====================================================

            Files.writeString(
                    oldFile.toPath(),
                    combinedResult
            );

            // =====================================================
            // DELETE TEMP FILE
            // =====================================================

            new File(tempFile).delete();

            // =====================================================
            // RESTORE CONSOLE
            // =====================================================

            System.setOut(console);

            System.out.println(
                    "API test result saved to: "
                            + resultsFile
            );
        }
    }


    // =========================================================
    // ADD PRODUCT METHOD
    // =========================================================

    public static void addProduct(
            String sessionId,
            int productId) {

        System.out.println(
                "\nAdding product ID: "
                        + productId
        );

        Response response =
                given()
                    .baseUri("http://localhost:8081")
                    .contentType("application/json")
                    .cookie("PHPSESSID", sessionId)

                    .body("""
                        {
                            "product_id": %d,
                            "quantity": 1
                        }
                        """.formatted(
                            productId
                        ))

                .when()
                    .post("/api/cart_api.php");

        System.out.println(
                "Status: "
                        + response.getStatusCode()
        );

        System.out.println(
                response.asPrettyString()
        );

        if (response.getStatusCode() == 200 ||
            response.getStatusCode() == 201) {

            System.out.println(
                    "PASS: Product "
                            + productId
                            + " added to cart"
            );

        } else {

            System.out.println(
                    "FAIL: Product "
                            + productId
                            + " could not be added"
            );
        }
    }
}
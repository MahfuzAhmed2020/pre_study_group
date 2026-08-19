package pre_study_group;

import io.restassured.response.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class RegistrationApiTest {

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

        String resultsFile =
                resultsFolder + "\\api-test-result.txt";

        String tempFile =
                resultsFolder + "\\api-current-result.txt";

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

        // =====================================================
        // GENERATE RANDOM 5 CHARACTER NAME
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

        String firstName = name.toString();
        String lastName = name.toString();

        // Make email unique for every test run
        String email =
                name.toString().toLowerCase()             
                + "@gmail.com";

        String password = "Test@123";

        // =====================================================
        // DISPLAY TEST DATA
        // =====================================================

        System.out.println("=========================================");
        System.out.println("REGISTRATION API TEST");
        System.out.println("=========================================");

        System.out.println("First Name : " + firstName);
        System.out.println("Last Name  : " + lastName);
        System.out.println("Email      : " + email);
        System.out.println("Password   : " + password);

        // =====================================================
        // POST REGISTRATION REQUEST
        // =====================================================

        Response response =
                given()
                    .baseUri("http://localhost:8081")

                    // Header
                    .header("Content-Type", "application/json")

                    // Cookie from Postman
                    .cookie("PHPSESSID",
                            "23788d90ae2e5d1178b67355adc0a3f6")

                    // Request body
                    .body("""
                        {
                            "first_name": "%s",
                            "last_name": "%s",
                            "email": "%s",
                            "password": "%s",
                            "confirm_password": "%s",
                            "agree_terms": "on"
                        }
                        """.formatted(
                            firstName,
                            lastName,
                            email,
                            password,
                            password
                        ))

                .when()

                    .post("/api/register_api.php");

        // =====================================================
        // RESPONSE
        // =====================================================

        System.out.println("=========================================");
        System.out.println("RESPONSE");
        System.out.println("=========================================");

        System.out.println(
                "Status Code: " + response.getStatusCode()
        );

        System.out.println(
                "Response Body:"
        );

        System.out.println(
                response.asPrettyString()
        );

        // =====================================================
        // VALIDATE STATUS
        // =====================================================
        System.out.println("=========================================");
        System.out.println("VALIDATE STATUS");
        System.out.println("=========================================");        

        if (response.getStatusCode() == 200 ||
            response.getStatusCode() == 201) {

            System.out.println(
                    "PASS: Registration API successful"
            );

        } else {

            System.out.println(
                    "FAIL: Registration API failed"
            );
        }

        System.out.println("=========================================");
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
            // PUT NEW RESULT ON TOP
            // =================================================

            String combinedResult =
                    "=========================================\n"
                    + "NEW API TEST EXECUTION\n"
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
            // RESTORE CONSOLE
            // =================================================

            System.setOut(console);

            System.out.println(
                    "API test result saved to: "
                            + resultsFile
            );       
    }
}
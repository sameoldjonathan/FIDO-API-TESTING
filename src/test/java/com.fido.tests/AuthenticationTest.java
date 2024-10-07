import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AuthenticationTest {

    public static void main(String[] args) {
        // Load base URL from config.properties
        RestAssured.baseURI = "https://www.videogamedb.uk/api";

        // Create the JSON body with username and password
        String requestBody = "{ \"username\": \"admin\", \"password\": \"admin\" }";

        // Make the POST request to authenticate and receive the token
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("/authenticate")
                        .then()
                        .extract()
                        .response();

        // Check the response status code
        if (response.getStatusCode() == 200) {
            // Extract the token from the JSON response
            String token = response.jsonPath().getString("token");

            // Print the token to use in subsequent requests
            System.out.println("Authentication Token: " + token);

            // Update the token in config.properties
            updateAuthTokenInConfig(token);
        } else {
            System.out.println("Failed to authenticate. Status Code: " + response.getStatusCode());
        }
    }

    // Method to update the token in config.properties
    public static void updateAuthTokenInConfig(String token) {
        try (OutputStream output = new FileOutputStream("src/test/resources/config.properties")) {
            Properties prop = new Properties();

            // Set the new token
            prop.setProperty("auth.token", "Bearer " + token);

            // Save the properties file
            prop.store(output, null);
            System.out.println("Auth token saved to config.properties file.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
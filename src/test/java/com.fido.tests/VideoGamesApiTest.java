package com.fido.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class VideoGamesApiTest {

    private String baseUrl;
    private String videoGamesEndpoint;
    private String authToken;

    @BeforeClass
    public void setUp() {
        // Set the base URL for the API
        baseUrl = "https://www.videogamedb.uk";
        videoGamesEndpoint = baseUrl + "/api/videogame";
        authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyODI5OTk2NSwiZXhwIjoxNzI4MzAzNTY1fQ.H5794gb1SdY7Gv8JvUEDNTRxVS6PKxW3dAX4hNOfRyY";
    }

    @Test
    public void testListVideoGames() {
        // Send a GET request to list all video games
        Response response = RestAssured
                .given()
                .header("Authorization", authToken) // Add the Authorization header
                .when()
                .get(videoGamesEndpoint)
                .then()
                .statusCode(200) // Verify status code is 200
                .extract()
                .response();

        // Validate that the response body contains a list
        assertNotNull(response.jsonPath().getList(""), "The video games list should not be null");
        System.out.println("List of video games: " + response.asString());
    }

    @Test
    public void testGetVideoGame() {
        // Define a specific video game ID to retrieve
        int videoGameId = 1; // Replace with a valid ID

        // Send a GET request to retrieve a specific video game
        Response response = RestAssured
                .given()
                .header("Authorization", authToken)
                .when()
                .get(videoGamesEndpoint + "/" + videoGameId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Validate that the retrieved video game is not null
        assertNotNull(response.jsonPath().get(""), "The retrieved video game should not be null");
        System.out.println("Retrieved video game: " + response.asString());
    }

    @Test
    public void testCreateVideoGame() {
        // Create a new video game JSON object
        String newGame = "{ \"name\": \"New Game\", \"releaseDate\": \"2023-01-01\", \"reviewScore\": 90, \"category\": \"Action\", \"rating\": \"Mature\" }";

        // Send a POST request to create a new video game
        Response response = RestAssured
                .given()
                .header("Authorization", authToken)
                .contentType("application/json")
                .body(newGame)
                .when()
                .post(videoGamesEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Validate that the response contains the created video game
        assertNotNull(response.jsonPath().get(""), "The created video game should not be null");
        System.out.println("Created video game: " + response.asString());
    }

    @Test
    public void testUpdateVideoGame() {
        // Define a specific video game ID to update
        int videoGameId = 1;
        String updatedGame = "{ \"name\": \"Updated Game\", \"releaseDate\": \"2023-02-01\", \"reviewScore\": 95, \"category\": \"Action\", \"rating\": \"Teen\" }";

        // Send a PUT request to update the video game
        Response response = RestAssured
                .given()
                .header("Authorization", authToken)
                .contentType("application/json")
                .body(updatedGame)
                .when()
                .put(videoGamesEndpoint + "/" + videoGameId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Validate that the response contains the updated video game
        assertNotNull(response.jsonPath().get(""), "The updated video game should not be null");
        System.out.println("Updated video game: " + response.asString());
    }

    @Test
    public void testDeleteVideoGame() {
        // Define a specific video game ID to delete
        int videoGameId = 1; // Replace with a valid ID

        // Send a DELETE request to delete the video game
        Response response = RestAssured
                .given()
                .header("Authorization", authToken)
                .when()
                .delete(videoGamesEndpoint + "/" + videoGameId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Validate the response
        assertEquals(response.getBody().asString(), "Video game deleted", "Response should indicate success");
        System.out.println("Deleted video game with ID: " + videoGameId);
    }
}

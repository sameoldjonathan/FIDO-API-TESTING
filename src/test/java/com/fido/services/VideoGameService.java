package com.fido.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class VideoGameService {

    private Properties properties;

    // Constructor to load properties file
    public VideoGameService() throws IOException {
        properties = new Properties();
        try (var input = Files.newInputStream(Paths.get("src/test/resources/api_endpoints.properties"))) {
            properties.load(input);
        }
    }

    // Method to authenticate and get the auth token
    public Response authenticate(String username, String password) {
        String authEndpoint = properties.getProperty("auth.endpoint");
        return RestAssured
                .given()
                .contentType("application/json")
                .body("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                .post(properties.getProperty("base.url") + authEndpoint);
    }

    // Method to get all video games
    public Response getAllVideoGames(String authToken) {
        String videoGamesEndpoint = properties.getProperty("videogames.endpoint");
        return RestAssured
                .given()
                .header("Authorization", authToken)
                .get(properties.getProperty("base.url") + videoGamesEndpoint);
    }

    // Method to get a video game by ID
    public Response getVideoGameById(String authToken, int id) {
        String endpoint = properties.getProperty("videogame.by.id.endpoint").replace("{id}", String.valueOf(id));
        return RestAssured
                .given()
                .header("Authorization", authToken)
                .get(properties.getProperty("base.url") + endpoint);
    }
}

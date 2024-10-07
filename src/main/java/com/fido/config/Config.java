package com.fido.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new IOException("Unable to find config.properties");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("api.base.url");
    }

    public static String getAuthEndpoint() {
        return properties.getProperty("api.auth.endpoint");
    }

    public static String getListVideoGamesEndpoint() {
        return properties.getProperty("api.videogames.list.endpoint");
    }

    public static String getGetVideoGameEndpoint() {
        return properties.getProperty("api.videogame.get.endpoint");
    }

    public static String getCreateVideoGameEndpoint() {
        return properties.getProperty("api.videogame.create.endpoint");
    }

    public static String getUpdateVideoGameEndpoint() {
        return properties.getProperty("api.videogame.update.endpoint");
    }

    public static String getDeleteVideoGameEndpoint() {
        return properties.getProperty("api.videogame.delete.endpoint");
    }
}

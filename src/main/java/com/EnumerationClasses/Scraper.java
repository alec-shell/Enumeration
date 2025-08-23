/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.EnumerationClasses;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 * Scraper.java: initializes a reusable HttpClient for HTML retrieval
 * 
 */
public class Scraper {
    private final HttpClient client;
    
    public Scraper() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    } // constructor()

    public final String getBody(String URL) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/115.0.0.0 Safari/537.36";
        String body = null;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .setHeader("User-Agent", userAgent)
                .uri(URI.create(URL))
                .timeout(Duration.ofMinutes(2))
                .GET()
                .build();
            response = client.send(request, BodyHandlers.ofString());
            body = response.body();
        }
        catch (IllegalArgumentException | IOException | InterruptedException e) {
            System.out.println("Error reaching host: " + e.getMessage());
        }
        return body;
    } // getResponse()

} // Scraper class

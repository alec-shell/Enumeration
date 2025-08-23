/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.EnumerationClasses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Crawler.java: abstract class for domain crawling
 * @author ALEC
 */
public abstract class Crawler {
    final Queue<String> URLQueue = new LinkedList<>();
    final Set<String> visited = new HashSet<>();
    final Scraper scraper = new Scraper();
    
    public Crawler(){}
    
    /**
     * - BFS on subdomains
     * 
     * @param seed - root URL
     */
    public final void crawl(String seed){
        URLQueue.add(seed);
        while (!URLQueue.isEmpty()) {
            String url = URLQueue.remove();
            if (!visited.contains(url)) {
                visited.add(url);
                scrape(url, seed);
                try {
                    Thread.sleep(800);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    } // crawl()
           
    /**
     * - overloaded crawl method
     *
     * @param seed
     * @param sizeLimit - adds count limit to number of enumerated subdomains
     */
    public final void crawl(String seed, int sizeLimit){
        URLQueue.add(seed);
        while (!URLQueue.isEmpty() && visited.size() < sizeLimit) {
            String url = URLQueue.remove();
            if (!visited.contains(url)) {
                visited.add(url);
                scrape(url, seed);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    } // overloaded crawl()

    /**
     * get methods
     */
    
    public final Queue<String> getQueue() {
        return URLQueue;
    } // getQueue()
    
    public final Set<String> getVisited() {
        return visited;
    } // getVisited()
    
    public final Scraper getScraper() {
        return scraper;
    } // getScraper()
    
    /**
     * - define scraping functionality for each new dequeued URL
     * - must use getter methods to access inherited 'scraper' object, update inherited 'URLQueue', as well as update inherited set 'visited'
     * 
     * @param currentURL - current subdomain to be scraped
     * @param baseURL  - root URL for matching
     */
    public abstract void scrape(String currentURL, String baseURL);
} // Crawler class

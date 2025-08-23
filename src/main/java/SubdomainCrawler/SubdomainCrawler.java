/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubdomainCrawler;

import com.EnumerationClasses.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawler.java: Generic subdomain crawler for enumeration
 * 
 */

public class SubdomainCrawler extends Crawler {
    
    public SubdomainCrawler() {}
    
    public final void start(String seed) {
        crawl(seed);
    } // start()
    
    public final void start(String seed, int limit) {
        crawl(seed, limit);
    } // overloaded start()
    
    @Override
    public void scrape(String url, String baseURL) {
        String body = getScraper().getBody(url);
        if (body != null) {
            Document doc = Jsoup.parse(body, baseURL);
            Elements links = doc.select("a[href]");

            for (Element e : links) {
                String link = e.absUrl("href");
                if (!getVisited().contains(link)) getQueue().add(link);
                System.out.println(link);
            }
        }
    } // scrape()
    
    public static void main(String[] args) {
        SubdomainCrawler crawler = new SubdomainCrawler();
        
        crawler.crawl("https://www.google.com/", 10);
    } // main()
} // Crawler class

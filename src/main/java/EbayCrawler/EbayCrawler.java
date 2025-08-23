/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EbayCrawler;

import com.EnumerationClasses.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * EbayCrawler.java: Extract eBay pricing information on user-defined item
 * @author ALEC
 */
public class EbayCrawler extends Crawler {
    private final String baseURL = "https://www.ebay.com/sch/i.html?_nkw=";
    private final String LINKELEMENT = "a.pagination__item";
    private final String ITEMDIVS = "div.su-card-container__content";
    private String[] searchTerms;
    
    public final void start(String... args) {
        if (args.length == 0) throw new IllegalArgumentException("Missing search query");
        
        searchTerms = new String[args.length];
        String query = baseURL;
        
        for (int i = 0; i < args.length; i++) {
            query += "+" + args[i];
            searchTerms[i] = args[i];
        }
        crawl(query);
    } // start()
    
    @Override
    public void scrape(String currentURL, String x) {
        String body = getScraper().getBody(currentURL);
        if (body != null) {
            Document doc = Jsoup.parse(body);
            
            Elements links = doc.select(LINKELEMENT);
            getLinks(links);
            
            Elements itemDivs = doc.select(ITEMDIVS);
            processItems(itemDivs);
        }
    } // scrape()
    
    private void getLinks(Elements links) {
        for (Element e : links) {
            String link = e.absUrl("href");
            if (!getVisited().contains(link) && !getQueue().contains(link)) {
                getQueue().add(link);
            }
        }
    } // getLinks()
    
    private void processItems(Elements itemDivs) {
        for (Element item : itemDivs) {
            String desc = item.child(0).text().toLowerCase();
            String price = item.child(1).text().toLowerCase();
            for (String term : searchTerms) {
                if (!desc.contains(term.toLowerCase())) return;
            }
            System.out.println(desc + "\n" + price + "\n");
        }
    } // processChildren()
    
    public static void main(String[] args) {
        String[] query = new String[]{"macbook", "air", "2020", "m1"};
        if (args.length == 0) {
        EbayCrawler crawler = new EbayCrawler();
        crawler.start(query);
        }
        else {
            System.out.println("FORMAT: EbayCrawler.java [search terms...]");
        }
    } // main()
    
} // EbayCrawler class

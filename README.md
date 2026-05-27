# Domain Crawler Framework (Java)

A lightweight, extensible domain crawling framework built in Java using a BFS-style crawler architecture. It supports pluggable scraping logic and can be adapted to different domains (e-commerce, search engines, etc.). An example implementation is included for scraping eBay search results.

---

## Features

- BFS-based crawling using a queue system
- Deduplication via visited URL tracking
- Pluggable scraping logic via abstract `Crawler.scrape()` method
- Reusable HTTP scraping layer using Java 11 `HttpClient`
- Built-in rate limiting (thread sleep delay)
- Example implementation for eBay product scraping
- HTML parsing powered by Jsoup

---

## Architecture Overview

The framework is split into two core components:

### 1. `Crawler` (Abstract Core)

Responsible for:
- URL queue management (BFS traversal)
- Tracking visited URLs
- Crawl orchestration
- Delegating page-specific scraping logic to subclasses

---

### 2. `Scraper` (HTTP Layer)

Responsible for:
- Making HTTP requests
- Returning raw HTML responses
- Handling timeouts and connection configuration

Uses:
- Java `HttpClient`
- Browser-like User-Agent header for compatibility

---

## How It Works

1. Start with a seed URL
2. Add it to the queue
3. Loop until queue is empty (or size limit is reached):
   - Dequeue a URL
   - Skip if already visited
   - Fetch HTML via `Scraper`
   - Call `scrape()`
   - Extract new URLs (if applicable)
   - Add new URLs to queue

---

## Project Structure
com.EnumerationClasses
├── Crawler.java (abstract BFS crawler)
├── Scraper.java (HTTP client wrapper)
EbayCrawler
└── EbayCrawler.java (example implementation)

---

## Crawler API

### `crawl(String seed)`
Starts an unbounded BFS crawl.

### `crawl(String seed, int sizeLimit)`
Starts a BFS crawl with a maximum visited limit.

### `scrape(String currentURL, String baseURL)`
Abstract method implemented by subclasses to define page processing logic.

### Shared State

- `Queue<String> URLQueue`
- `Set<String> visited`
- `Scraper scraper`

---

## Scraper API

### `getBody(String url)`

Fetches raw HTML from a URL.

Features:
- 20-second connection timeout
- 2-minute request timeout
- Browser-like User-Agent header
- Safe exception handling (returns `null` on failure)

---

## Example: eBay Crawler

### Usage

```java
EbayCrawler crawler = new EbayCrawler();
crawler.start("macbook", "air", "2020", "m1");
```
Behavior
Builds eBay search URL:
https://www.ebay.com/sch/i.html?_nkw=macbook+air+2020+m1
Crawls pagination links:
a.pagination__item
Extracts product listings:
div.su-card-container__content
Filters results using search terms
Prints matching items (description + price)
Scraping Logic Notes
Link Extraction
New URLs are added to the queue if not visited:
if (!getVisited().contains(link) && !getQueue().contains(link)) {
    getQueue().add(link);
}
Item Filtering
Each product is filtered by search terms:
if (!desc.contains(term.toLowerCase())) continue;

Dependencies
Java Version
Java 11+
Jsoup
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.17.2</version>
</dependency>

Design Highlights
BFS traversal ensures broad page coverage before depth expansion
Abstract scrape() enables reusable crawling across domains
Clear separation of concerns:
Crawler → traversal logic
Scraper → networking layer
Subclasses → domain-specific parsing

Limitations
No robots.txt handling
No retry/backoff mechanism
Single-threaded execution
Fixed sleep-based rate limiting
eBay DOM selectors may break if site changes
No persistence layer (all data is printed only)

Extending the Framework

To create a new crawler:
Extend Crawler
Implement scrape()
Use getScraper().getBody(url)
Parse and extract data using Jsoup
Add new URLs to the queue as needed
Example
```java
public class MyCrawler extends Crawler {

    @Override
    public void scrape(String currentURL, String baseURL) {
        String html = getScraper().getBody(currentURL);

        if (html == null) return;

        Document doc = Jsoup.parse(html);

        // custom parsing logic here
    }
}
```
License
This project is intended for educational and personal use.

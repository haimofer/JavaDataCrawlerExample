# Java Data Crawler
Java Data Crawler is a Java-based web crawler that extracts posts from a website and saves them as JSON files. 
The crawler uses Jsoup for HTML parsing, Gson for JSON serialization, and Typer for CLI creation.

### Usage
The crawler can be run from the command line by providing a URL as a command line argument:

```bash
java -jar JavaDataCrawler.jar https://example.com
```
The posts will be saved in the ./data/ directory with a unique thread filename prefix.

### Configuration
The crawler can be configured by changing the following constants in the PostCrawler class:

1. **MAX_CONNECTIONS**: the maximum number of connections to use for crawling (default: 2)
2. **USER_AGENT**: the user agent to use for HTTP requests (default: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36)
3. **TIMEOUT**: the maximum time to wait for a connection or response (default: 5 seconds)
4. **PAUSE_TIME**: the time to wait between each document retrieval (default: 3 seconds)
5. **SAVE_LOCATION**: the directory to save the posts in (default: ./data/)

### License
Java Data Crawler is licensed under the MIT License.

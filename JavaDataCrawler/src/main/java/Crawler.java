import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * The `PostCrawler` class represents a web crawler that is used to extract and save
 * posts from a given URL. It uses concurrency to improve the speed of the
 * extraction process by downloading multiple posts simultaneously. Each post is
 * represented as a `Post` object and is saved as a JSON file in the `./data`
 * directory. The filename of each file is generated from a unique thread prefix
 * followed by the post title.
 */
public class Crawler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
    private static final int MAX_CONNECTIONS = 2;
    private static final int TIMEOUT = 5000;
    private static final int PAUSE_TIME = 3000;
    private static final String POST_URL = "https://www.turkhacks.com/forum/";
    private static final String SAVE_LOCATION = "./data/";
    private static final String FILES_POSTFIX = ".json";

    public static void main(String[] args) {
        String url = args[0];

        try {
            List<Post> posts = crawlPosts(url);
            savePosts(posts);
        } catch (Exception e) {
            System.err.println("Error processing URL: " + url + ": " + e);
        }
    }

    private static List<Post> crawlPosts(String url) throws Exception {
        Document document = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .get();
        Elements elements = document.select("a[href^=/threads]");

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONNECTIONS);
        List<CompletableFuture<Post>> futures = new ArrayList<>();

        for (Element element : elements) {
            String postUrl = POST_URL + element.attr("href");
            CompletableFuture<Post> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return downloadPost(postUrl);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
            }, executorService);
            futures.add(future);
            Thread.sleep(PAUSE_TIME);
        }

        List<Post> posts = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        return posts;
    }

    private static void savePosts(List<Post> posts) throws IOException {

        for (Post post : posts) {
            savePost(post);
        }
    }


    static Post downloadPost(String url) throws IOException {
        Document document = Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT).get();
        String title = document.select("h1").text();
        String publishedTime = document.select("span.DateTime").text();
        String content = document.select("article.message-body").text();
        String threadPrefix = url.split("/")[5] + "_";
        return new Post(url, title, publishedTime, content, threadPrefix);
    }

    private static void savePost(Post post) throws IOException {
        String filename = SAVE_LOCATION + "_" + post.getPublishedTime() + "_" + post.getTitle().replaceAll("\\W+", "_") + FILES_POSTFIX;
        Gson gson = new Gson();
        String json = gson.toJson(post);
        Files.write(Paths.get(filename), json.getBytes(StandardCharsets.UTF_8));
    }

}

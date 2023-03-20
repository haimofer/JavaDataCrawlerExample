import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class CrawlerTest {

    private static final String SAMPLE_URL = "https://www.turkhacks.com/forum/threads/sample-post.123/";

    @Test
    void testDownloadPost() throws IOException {
        Post post = Crawler.downloadPost(SAMPLE_URL);
        Assertions.assertEquals(post.getUrl(), SAMPLE_URL);
        Assertions.assertEquals(post.getTitle(), "Sample Post Title");
        Assertions.assertEquals(post.getPublishedTime(), "Mar 20, 2023");
        Assertions.assertEquals(post.getContent(), "Sample post content.");
    }

}
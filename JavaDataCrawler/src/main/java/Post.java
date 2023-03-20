/**
 * A class representing a single post.
 */
public class Post {
    private String url;
    private String title;
    private String publishedTime;
    private String content;
    private String threadPrefix;

    /**
     * Constructs a new post with the specified properties.
     *
     * @param url           The URL of the post.
     * @param title         The title of the post.
     * @param publishedTime The published time of the post.
     * @param content       The content of the post.
     * @param threadPrefix  The wanted thread prefix
     */
    public Post(String url, String title, String publishedTime, String content, String threadPrefix) {
        this.url = url;
        this.title = title;
        this.publishedTime = publishedTime;
        this.content = content;
        this.threadPrefix = threadPrefix;
    }

    /**
     * Getters & Setters
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThreadPrefix() {
        return threadPrefix;
    }

    public void setThreadPrefix(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }
}
package cz.vaneo.kiv.ir.InformationRetrieval.model;

public class Article {

    private String downloadedDate;
    private String title;
    private String author;
    private String content;
    private String url;

    public Article(String downloadedDate, String title, String author, String content, String url) {
        this.downloadedDate = downloadedDate;
        this.title = title;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getDownloadedDate() {
        return downloadedDate;
    }

    public void setDownloadedDate(String downloadedDate) {
        this.downloadedDate = downloadedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

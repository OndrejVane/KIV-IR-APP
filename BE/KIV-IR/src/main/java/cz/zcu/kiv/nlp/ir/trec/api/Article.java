package cz.zcu.kiv.nlp.ir.trec.api;

public class Article {

    private int id;
    private String downloadedDate;
    private String title;
    private String author;
    private String content;
    private String url;
    private float score;
    private int rank;

    public Article() {
    }

    public Article(int id, String downloadedDate, String title, String author, String content, String url, float score, int rank) {
        this.id = id;
        this.downloadedDate = downloadedDate;
        this.title = title;
        this.author = author;
        this.content = content;
        this.url = url;
        this.score = score;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    @Override
    public String toString() {
        return "ID: " +this.getId()+", Article : " + this.getTitle();
    }
}

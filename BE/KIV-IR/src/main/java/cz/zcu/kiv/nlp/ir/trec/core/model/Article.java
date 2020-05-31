package cz.zcu.kiv.nlp.ir.trec.core.model;

import java.io.Serializable;

/**
 * Modelová třída pro moje stáhnutá data.
 *
 * @author ondrejvane
 */
public class Article implements Serializable {

    final static long serialVersionUID = -8852692424583653999L;

    private String id;
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

    public Article() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Article{" +
                "downloadedDate='" + downloadedDate + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}


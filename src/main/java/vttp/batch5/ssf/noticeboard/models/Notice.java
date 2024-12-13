package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;

public class Notice {

    private String title;
    private String poster;
    private Date postDate;
    private String categories;
    private String contentText;
    
    public Notice() {

    }
    
    public Notice(String title, String poster, Date postDate, String categories, String contentText) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.contentText = contentText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Override
    public String toString() {
        return title + "," + poster + "," + postDate + "," + categories + "," + contentText;
    }

}

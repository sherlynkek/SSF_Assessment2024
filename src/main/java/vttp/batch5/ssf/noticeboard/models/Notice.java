package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Notice {

    @NotEmpty(message = "Notice Title should not be empty")
    @Size(min = 3, max = 128, message = "The notice title's length must be between 3 and 128 characters")
    private String title;

    @NotEmpty(message = "Notice Poster's email should not be empty")
    @Email(message = "must be a well-formed email address")
    private String poster;

    @NotEmpty(message = "Notice Post Date should not be empty")
    @Future(message = "Date must be in the future excluding the date that the notice is created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotEmpty(message = "must include at least one category")
    private String categories;

    @NotEmpty(message = "the content of the notice should not be empty")
    private String text;
    
    private String postingId;
    
    public Notice(String string) {
        //TODO Auto-generated constructor stub
    }

    public Notice(String title, String poster, Date postDate, String categories, String text) {
        this.postingId = UUID.randomUUID().toString();

        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostingId() {
        return postingId;
    }

    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }

    @Override
    public String toString() {
        return title + "," + poster + "," + postDate + "," + categories + "," + text;
    }

}

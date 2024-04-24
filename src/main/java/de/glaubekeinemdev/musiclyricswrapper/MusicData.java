package de.glaubekeinemdev.musiclyricswrapper;

public class MusicData {

    private final String title;
    private final String author;
    private final long id;
    private final String url;
    private final String imageUrl;

    public MusicData(String title, String author, long id, String url, String imageUrl) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return "https://genius.com" + url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

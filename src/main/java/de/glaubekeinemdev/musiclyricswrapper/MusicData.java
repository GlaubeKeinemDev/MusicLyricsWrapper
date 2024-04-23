package de.glaubekeinemdev.musiclyricswrapper;

public class MusicData {

    private final String title;
    private final long id;
    private final String url;
    private final String imageUrl;

    public MusicData(String title, long id, String url, String imageUrl) {
        this.title = title;
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
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

package de.glaubekeinemdev.musiclyricswrapper;

public class MusicLyrics {

    private final String content;
    private final long id;

    public MusicLyrics(String content, long id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }
}

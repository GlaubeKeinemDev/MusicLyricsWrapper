package de.glaubekeinemdev.musiclyricswrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class MusicLyricsClient {

    private final String searchEndpoint;
    private final String lyricsEndpoint;

    private final ConcurrentHashMap<String, ArrayList<MusicData>> songCache;
    private final ConcurrentHashMap<Long, MusicLyrics> lyricsCache;

    private final RequestHelper requestHelper;

    public MusicLyricsClient() {
        this.searchEndpoint = "https://genius.com/api/search?q=";
        this.lyricsEndpoint = "https://genius.com/songs/%id%/embed.js";

        this.songCache = new ConcurrentHashMap<>();
        this.lyricsCache = new ConcurrentHashMap<>();

        this.requestHelper = new RequestHelper();
    }

    public ArrayList<MusicData> searchSong(final String query) {
        if(this.songCache.containsKey(query))
            return this.songCache.get(query);

        final String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        try {
            final URL url = new URL(this.searchEndpoint + encodedQuery);
            final HttpURLConnection connection = this.requestHelper.establishConnection(url, true);
            final String output = this.requestHelper.getRequest(connection);

            final JSONObject jsonObject = new JSONObject(output);

            if (isAnythingMissingSongRequest(jsonObject))
                return new ArrayList<>();

            final JSONArray jsonSongArray = jsonObject.getJSONObject("response").getJSONArray("hits");

            final ArrayList<MusicData> songs = new ArrayList<>();

            for (int i = 0; i < jsonSongArray.length(); i++) {
                JSONObject song = jsonSongArray.getJSONObject(i).getJSONObject("result");

                final String title = formatSpaces(song.getString("full_title"));

                songs.add(new MusicData(title, song.getLong("id"),
                        song.getString("path"), song.getString("header_image_thumbnail_url")));
            }

            if(!songs.isEmpty())
                this.songCache.put(query, songs);

            return songs;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public MusicLyrics fetchLyrics(final long id) {
        if(this.lyricsCache.containsKey(id))
            return this.lyricsCache.get(id);

        try {
            final URL url = new URL(this.lyricsEndpoint.replace("%id%", String.valueOf(id)));
            final HttpURLConnection connection = this.requestHelper.establishConnection(url, false);

            final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
            scanner.useDelimiter("\\A");

            final StringBuilder rawInputStringBuilder = new StringBuilder();

            while (scanner.hasNext())
                rawInputStringBuilder.append(scanner.next());

            if (rawInputStringBuilder.isEmpty())
                return null;

            final String lyrics = formatRawLyrics(rawInputStringBuilder.toString());
            final MusicLyrics musicLyrics = new MusicLyrics(lyrics, id);

            if(lyrics.length() > 50)
                this.lyricsCache.put(id, musicLyrics);

            return musicLyrics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MusicLyrics fetchLyrics(final MusicData musicData) {
        return fetchLyrics(musicData.getId());
    }

    public MusicLyrics fetchLyrics(final String query) {
        final ArrayList<MusicData> loadedSongs = searchSong(query);

        if (loadedSongs.isEmpty())
            return null;

        return fetchLyrics(loadedSongs.get(0));
    }

    private boolean isAnythingMissingSongRequest(final JSONObject jsonObject) {
        if (!jsonObject.has("meta"))
            return true;
        if (!jsonObject.getJSONObject("meta").has("status"))
            return true;
        final int status = jsonObject.getJSONObject("meta").getInt("status");

        if (status != 200)
            return true;

        if (!jsonObject.has("response"))
            return true;

        return !jsonObject.getJSONObject("response").has("hits");
    }

    private String formatSpaces(String input) {
        input = input.replaceAll("[\u00A0\\p{Zs}]+", " ");
        input = input.replaceAll("\\p{C}", " ");
        return input;
    }

    private String formatRawLyrics(String input) {
        input = input.replaceAll("[\\S\\s]*<div class=\\\\\\\\\\\\\"rg_embed_body\\\\\\\\\\\\\">[ (\\\\n)]*", "");
        input = input.replaceAll("[ (\\\\n)]*<\\\\/div>[\\S\\s]*", "");
        input = input.replaceAll("<[^<>]*>", "");
        input = input.replaceAll("\\\\\\\\n", "\n");
        input = input.replaceAll("\\\\'", "'");
        input = input.replaceAll("\\\\\\\\\\\\\"", "\"");

        byte[] bytes = input.getBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

}

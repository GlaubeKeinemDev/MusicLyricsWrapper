# Music Lyrics Wrapper
This Wrapper searches for lyrics for your favorite songs. You don't need to enter any credentials or register your account.

# Performance
This Wrapper uses a built-in cache to cache already searched songs and avoid getting rate-limited

# Data source
This Wrapper uses [Genius](https://genius.com/) as data source. This isn't an official API. We are not partnered with Genius or anything similar

# How to use
Create an instance of MusicLyricsClient.
```java
MusicLyricsClient client = new MusicLyricsClient();
```
You can use the ``searchSong`` method to search for songs. This returns an Arraylist of ``MusicData`` objects, because there could be multiple results for one song name.
#### Search for songs:
```java
MusicLyricsClient client = new MusicLyricsClient();
ArrayList<MusicData> songs = client.searchSong("Michael Jackson");

if(songs.isEmpty()) {
    System.out.println("No songs found");
} else {
    System.out.println(songs.get(0).getTitle());
}
```
The ``MusicData`` object just contains simple information like the title or the id (internally handled) but it also contains an image of the song if you want to have a preview. The ``MusicData`` object doesn't contain lyrics.

If you want to fetch lyrics without searching for songs you can just use the ``fetchLyrics`` method. You can search for lyrics just with your **search term** and without using the ``searchSong`` method.

It's recommended to use the ``searchSong`` method first, because there could be multiple songs matching your search term. You can filter the songs first and fetch the lyrics after you found the right song.
#### Fetch lyrics (easy way):
```java
MusicLyricsClient client = new MusicLyricsClient();
MusicLyrics lyrics = client.fetchLyrics("Michael Jackson");

if(lyrics == null) {
    System.out.println("No lyrics found");
} else {
    System.out.println("Lyrics: " + lyrics.getContent());
}
```
#### Fetch lyrics (recommended way):
```java
MusicLyricsClient client = new MusicLyricsClient();
ArrayList<MusicData> songs = client.searchSong("Michael Jackson");
MusicData firstSong = songs.get(0);

if(firstSong.getTitle().equals("my song")) {
    MusicLyrics lyrics = client.fetchLyrics(firstSong);
    
    if(lyrics == null) {
        System.out.println("No lyrics found");
    } else {
        System.out.println("Lyrics: " + lyrics.getContent());
    }
}
```
# Install

Add the repository and dependency to your project.

Replace ``x.y.z`` with [![](https://jitpack.io/v/GlaubeKeinemDev/MusicLyricsWrapper.svg)](https://jitpack.io/#GlaubeKeinemDev/MusicLyricsWrapper) - the latest version of this project!

**Gradle**
```gradle
dependencies {
   implementation 'com.github.GlaubeKeinemDev:MusicLyricsWrapper:x.y.z'
}

repositories {
    maven { url 'https://jitpack.io' }
}
```

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.GlaubeKeinemDev</groupId>
    <artifactId>MusicLyricsWrapper</artifactId>
    <version>x.y.z</version>
</dependency>
```
# Disclaimer
This repository is for private use only. Just use it to research your songs. This Wrapper is not official and we are not partnered with Genius or any relating companies.

I will take NO responsibility/liability for you and how you use the source code and which source code you are using. If you use this Wrapper or including files you agree that you use this **AT YOUR OWN RISK**.
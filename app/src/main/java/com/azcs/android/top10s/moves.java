package com.azcs.android.top10s;

/**
 * Created by 3zcs on 1/16/16.
 */
public class moves {
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String artist ;
    private String releaseDate ;

    @Override
    public String toString() {
        return  "Name   :" + getName() + "\n" +
                "Artist :" + getArtist() + "\n" +
                "Date   :" + getReleaseDate() + "\n" ;
    }
}

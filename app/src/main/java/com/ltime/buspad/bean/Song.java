package com.ltime.buspad.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {


    private String file;
    private String thumb;
    private String trackName;
    private String trackArtist;
    private String trackAlbum;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public void setTrackAlbum(String trackAlbum) {
        this.trackAlbum = trackAlbum;
    }

    public String getTrackArtist() {
        return trackArtist;
    }

    public void setTrackArtist(String trackArtist) {
        this.trackArtist = trackArtist;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(file);
        arg0.writeString(thumb);
        arg0.writeString(trackName);
        arg0.writeString(trackArtist);
        arg0.writeString(trackAlbum);
    }

    public Song(String file, String thumb, String trackName, String trackArtist, String trackAlbum) {
        this.file = file;
        this.thumb = thumb;
        this.trackName = trackName;
        this.trackArtist = trackArtist;
        this.trackAlbum = trackAlbum;
    }

    public Song() {
    }

    public static Creator<Song> CREATOR = new Creator<Song>() {

        @Override
        public Song[] newArray(int arg0) {
            return new Song[arg0];
        }

        @Override
        public Song createFromParcel(Parcel arg0) {
            return new Song(arg0.readString(), arg0.readString(), arg0.readString(), arg0.readString(), arg0.readString());
        }
    };

}

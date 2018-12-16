package edu.lewisu.cs.michaelatutterrow.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Moviee implements Parcelable {
    private String title;
    private String poster;
    private String description;
    int rank;

    public Moviee(int rank, String title, String poster, String description) {
        this.rank = rank;
        this.title = title;
        this.poster = poster;
        this.description = description;
    }

    public int getRank() {
        return rank;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Moviee(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        rank = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(description);
        dest.writeInt(rank);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Moviee> CREATOR = new Parcelable.Creator<Moviee>() {
        @Override
        public Moviee createFromParcel(Parcel in) {
            return new Moviee(in);
        }

        @Override
        public Moviee[] newArray(int size) {
            return new Moviee[size];
        }
    };
}

 package com.example.darwish.data;

 import android.os.Parcel;
 import android.os.Parcelable;

 /**
 * Created by darwish on 8/12/2016.
 */

public class jsonObject implements Parcelable {
    private String ID ;

     protected jsonObject(Parcel in) {
         ID = in.readString();
         poster = in.readString();
         overview = in.readString();
         release_date = in.readString();
         title = in.readString();

         popularity = in.readString();
         vote_count = in.readString();
         vote_average = in.readString();
     }

     public jsonObject() {
     }

     public static final Creator<jsonObject> CREATOR = new Creator<jsonObject>() {
         @Override
         public jsonObject createFromParcel(Parcel in) {
             return new jsonObject(in);
         }

         @Override
         public jsonObject[] newArray(int size) {
             return new jsonObject[size];
         }
     };

     public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    private String poster;
    private String overview ;
    private String release_date ;
    private String title ;

    private String popularity ;
    private String vote_count ;
    private String vote_average;

     @Override
     public int describeContents() {
         return 0;
     }

     @Override
     public void writeToParcel(Parcel parcel, int i) {
         parcel.writeString(ID);
         parcel.writeString(poster);
         parcel.writeString(overview);
         parcel.writeString(release_date);
         parcel.writeString(title);
         parcel.writeString(popularity);
         parcel.writeString(vote_count);
         parcel.writeString(vote_average);
     }
 }

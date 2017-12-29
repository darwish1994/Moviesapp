package com.example.darwish.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by darwish on 9/15/2016.
 */
public class TrailObject implements Parcelable {
    private String id,key,name;

    public TrailObject() {

    }

    public TrailObject(Parcel in) {
        id = in.readString();

        key = in.readString();
        name = in.readString();
    }

    public static final Creator<TrailObject> CREATOR = new Creator<TrailObject>() {
        @Override
        public TrailObject createFromParcel(Parcel in) {
            return new TrailObject(in);
        }

        @Override
        public TrailObject[] newArray(int size) {
            return new TrailObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
    }
}

package com.example.madhupatel.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    String author;
    String content;

    Review(String author, String content){
        this.author = author;
        this.content = content;
    }

    public Review(Parcel in){
        this.author = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Review createFromParcel(Parcel in){
            return new Review(in);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[i];
        }
    };
}

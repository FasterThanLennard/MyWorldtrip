package com.rhahn.myworldtrip.Data;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TimelineData implements Parcelable, Serializable {

    int id;
    String name;
    String traveldate;
    Image flag;
    int flagImageID;


    public TimelineData(int id, String name, int flagImage, String traveldate) {
        this.id = id;
        this.name = name;
        this.flagImageID = flagImage;
        this.traveldate = traveldate;

    }

    protected TimelineData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        traveldate = in.readString();
        flagImageID = in.readInt();
    }

    public String getTraveldate() {
        return traveldate;
    }

    public void setTraveldate(String traveldate) {
        this.traveldate = traveldate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getFlag() {
        return flag;
    }

    public void setFlag(Image flag) {
        this.flag = flag;
    }

    public int getFlagImageID() {
        return flagImageID;
    }

    public void setFlagImageID(int flagImageID) {
        this.flagImageID = flagImageID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(traveldate);
        parcel.writeInt(flagImageID);
    }


    public static final Creator<TimelineData> CREATOR = new Creator<TimelineData>() {
        @Override
        public TimelineData createFromParcel(Parcel in) {
            return new TimelineData(in);
        }

        @Override
        public TimelineData[] newArray(int size) {
            return new TimelineData[size];
        }
    };
}

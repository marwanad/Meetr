package data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marwan.
 */
public class Booking implements Parcelable {
    public String startTime;
    public String endTime;
    public String userId;

    protected Booking(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.userId = in.readString();
    }

    public Booking(String startTime, String endTime, String userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.userId);
    }
}

package data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Marwan.
 */
public class Room implements Parcelable {

    public String name;
    public String _id;

    public String location;
    public String image;
    public String description;

    public ArrayList<Booking> bookings;

    protected Room(Parcel in) {
        this._id = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.image = in.readString();
        this.description = in.readString();
        in.readTypedList(new ArrayList<Booking>(), Booking.CREATOR);
    }

    public Room(String id, String name, String location, String image, String description, ArrayList<Booking> bookings) {
        this._id = id;
        this.name = name;
        this.location = location;
        this.image = image;
        this.description = description;
        this.bookings = bookings;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeString(this.image);
        dest.writeString(this.description);
        dest.writeTypedList(this.bookings);
    }
}

package data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Marwan.
 */
public class Room implements Parcelable {

    public String name;
    public String _id;

    public String location;
    public String description;

    public List<String> bookings;

    protected Room(Parcel in) {
        this.name = in.readString();
        this._id = in.readString();
        this.location = in.readString();
        this.description = in.readString();
        this.bookings = in.createStringArrayList();
    }

    public Room(String name, String id, String location, String description) {
        this.name = name;
        this._id = id;
        this.location = location;
        this.description = description;

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
        dest.writeString(this.name);
        dest.writeString(this._id);
        dest.writeString(this.location);
        dest.writeString(this.description);
        dest.writeStringList(this.bookings);
    }
}

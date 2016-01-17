package data.model;

/**
 * Created by Marwan.
 */
public class BookingRequest {
    public String id;
    public String startTime;
    public String endTime;
    public String userId;

    public BookingRequest(String id, String start, String end, String user) {
        this.id = id;
        this.startTime = start;
        this.endTime = end;
        this.userId = user;
    }
}

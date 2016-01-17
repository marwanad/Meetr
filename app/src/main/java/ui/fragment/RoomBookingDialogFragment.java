package ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import listeners.OnBookingCompleteListener;

/**
 * Created by Marwan.
 */
public class RoomBookingDialogFragment extends DialogFragment {
    private int _year;
    private int _month;
    private int _day;
    private OnBookingCompleteListener _onBookingCompleteListener;
    public static final String EXTRA_ROOM_ID = "extra.roomId";
    public static final String EXTRA_DIALOG_ID = "extra.dialog";
    private String _roomId;
    private String _duration;
    private Calendar c;
    private Calendar _startCal;
    int year;
    int month;
    int day;
    int hour;
    int min;

    public static RoomBookingDialogFragment newInstance(String id, String duration) {
        RoomBookingDialogFragment f = new RoomBookingDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_ROOM_ID, id);
        args.putString(EXTRA_DIALOG_ID, duration);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _roomId = getArguments().getString(EXTRA_ROOM_ID);
        _duration = getArguments().getString(EXTRA_DIALOG_ID);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), onDatePickedListener, year, month, day);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this._onBookingCompleteListener = (OnBookingCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + "activity hasn't implemented listener yet");
        }
    }

    // hacky for now, no more dialogs
    private void completeBooking(int hourOfDay, int min) {
        Log.d("Booking", "meeting duration is" + _duration);
        Calendar endCal = Calendar.getInstance();
        endCal.set(_year, _month, _day, hourOfDay, min);

        switch (_duration) {
            case "1 minute":
                endCal.add(Calendar.MINUTE, 1);
                break;
            case "2 minute":
                endCal.add(Calendar.MINUTE, 2);
                break;
            case "30 minutes":
                endCal.add(Calendar.MINUTE, 30);
                break;
            case "1 hour":
                endCal.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case "2 hour":
                endCal.add(Calendar.HOUR_OF_DAY, 2);
                break;
        }
        _onBookingCompleteListener.onComplete(_roomId, dateToIso(_startCal.getTime()), dateToIso(endCal.getTime()));
    }

    private String dateToIso(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    private DatePickerDialog.OnDateSetListener onDatePickedListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            _year = year;
            _month = monthOfYear;
            _day = dayOfMonth;
            new TimePickerDialog(getActivity(), onStartTimeListener, hour, min, true).show();
        }
    };
    private TimePickerDialog.OnTimeSetListener onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            _startCal = Calendar.getInstance();
            _startCal.set(_year, _month, _day, hourOfDay, minute);
            completeBooking(hourOfDay, minute);
        }
    };
}
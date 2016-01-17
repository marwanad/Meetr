package ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
public class RoomBookingDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int _year;
    private int _month;
    private int _day;
    private OnBookingCompleteListener _onBookingCompleteListener;
    public static final String EXTRA_ROOM_ID = "extra.roomId";
    private String _roomId;

    public static RoomBookingDialogFragment newInstance(String id) {
        RoomBookingDialogFragment f = new RoomBookingDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_ROOM_ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _roomId = getArguments().getString(EXTRA_ROOM_ID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();
        _roomId = b.getString(EXTRA_ROOM_ID);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date date = new Date(_year, _month, _day, _startHour, _startMin);
                        _onBookingCompleteListener.onComplete(_roomId, dateToIso(date), "2016-07-17T04:21:39+00:00");
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
            }
        });
        return dialog;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this._onBookingCompleteListener = (OnBookingCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + "activity hasn't implemented listener yet");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("Booking", String.valueOf(year));
        _year = year;
        _month = monthOfYear;
        _day = dayOfMonth;
    }

    private String dateToIso(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
}
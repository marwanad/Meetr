package ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
public class RoomBookingDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int _year;
    private int _month;
    private int _day;
    private OnBookingCompleteListener _onBookingCompleteListener;
    public static final String EXTRA_ROOM_ID = "extra.roomId";
    private String _roomId;
    private Calendar c;
    private Calendar startCal;
    private Calendar endCal;

    int year;
    int month;
    int day;
    private final String[] time = {"1 minute", "30 minutes", "1 hour", "2 hour" };

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
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
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
        _year = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                startCal = Calendar.getInstance();
                startCal.set(_year, _month, _day, hourOfDay, minute);
                showEndDialog();
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();

    }

    private void completeBooking() {
        _onBookingCompleteListener.onComplete(_roomId, dateToIso(startCal.getTime()), dateToIso(endCal.getTime()));
    }

    private String dateToIso(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    private void showEndDialog() {
        final AlertDialog endDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("How long is the meeting going to last ?");
        builder.setSingleChoiceItems(time, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                dismiss();
            }
        });
        endDialog = builder.create();
        endDialog.show();
//        endCal = Calendar.getInstance();
//        endCal.set(_year, _month, _day, hourOfDay, minute);
//        completeBooking();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
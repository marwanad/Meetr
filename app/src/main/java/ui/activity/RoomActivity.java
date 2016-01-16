package ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import data.model.Room;

/**
 * Created by Marwan.
 */
public class RoomActivity extends AppCompatActivity {
    private static final String EXTRA_ROOM = "EXTRA_ROOM";

    public static Intent getStartIntent(Context context, Room room) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(EXTRA_ROOM, room);
        return intent;
    }
}

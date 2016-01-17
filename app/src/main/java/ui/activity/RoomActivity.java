package ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.model.Room;
import marwanad.meetr.R;

/**
 * Created by Marwan.
 */
public class RoomActivity extends AppCompatActivity {
    private static final String EXTRA_ROOM = "EXTRA_ROOM";
    private Room mRoom;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_collapsing)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Bind(R.id.text_description)
    TextView mDescriptionText;


    public static Intent getStartIntent(Context context, Room room) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(EXTRA_ROOM, room);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        mRoom = getIntent().getParcelableExtra(EXTRA_ROOM);
        setupToolbar();
        setUpDescriptionText();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mCollapsingToolbar.setTitle(mRoom.name);
        }
    }

    private void setUpDescriptionText() {
        mDescriptionText.setText(mRoom.description);
    }

}

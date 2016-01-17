package ui.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import data.model.Room;
import marwanad.meetr.R;
import ui.activity.RoomActivity;
import ui.fragment.RoomBookingDialogFragment;
import ui.fragment.UnLockDialogFragment;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Marwan.
 */
@LayoutId(R.layout.item_room)

public class RoomHolder extends ItemViewHolder<Room> {
    @ViewId(R.id.room_container)
    View mContainer;

    @ViewId(R.id.text_book)
    TextView mBookText;

    @ViewId(R.id.text_details)
    TextView mDetailsText;

    @ViewId(R.id.text_roomName)
    TextView mRoomText;

    @ViewId(R.id.text_location)
    TextView mLocationText;

    @ViewId(R.id.room_image)
    ImageView mRoomImage;

    @ViewId(R.id.text_unlock)
    TextView mUnlockText;

    public RoomHolder(View view) {
        super(view);
    }

    @Override
    public void onSetListeners() {
        mBookText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMeARoom();
            }
        });

        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(RoomActivity.getStartIntent(getContext(), getItem()));
            }
        });

        mDetailsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(RoomActivity.getStartIntent(getContext(), getItem()));
            }
        });
        mUnlockText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnlockDialog();
            }
        });
    }

    @Override
    public void onSetValues(Room room, PositionInfo positionInfo) {
        mRoomText.setText(room.name);
        mLocationText.setText(room.location);
        Picasso.with(getContext())
                .load(room.image).placeholder(R.drawable.ic_refresh_pink_500_18dp).error(R.drawable.ic_error_outline_pink_500_18dp)
                .into(mRoomImage);
    }

    private void getMeARoom() {
        DialogFragment newFragment = RoomBookingDialogFragment.newInstance(getItem()._id, getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("meeting.duration", "1 minute"));
        newFragment.show(((Activity) getContext()).getFragmentManager(), "roomBooking");
    }

    private void showUnlockDialog() {
        UnLockDialogFragment frag = new UnLockDialogFragment();
        frag.show(((Activity) getContext()).getFragmentManager(), "unlockFrag");
    }
}

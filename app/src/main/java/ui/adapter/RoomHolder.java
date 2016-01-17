package ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import data.model.Room;
import marwanad.meetr.R;
import ui.activity.RoomActivity;
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


    public RoomHolder(View view) {
        super(view);
    }

    private String getImageUrl(String location) {
        switch (location) {
            // handling on the client side for now
            case "EIT 1015":
                return "https://uwaterloo.ca/earth-sciences-museum/sites/ca.earth-sciences-museum/files/styles/body-500px-wide/public/uploads/images/old_museum.jpg";
            case "Room 303":
                return "http://historicalhamilton.com/media/images/3206.jpg";
            case "Room 1337":
                return "http://sais2011.uwaterloo.ca/images/UW_E5_byMatthewManjos.png";
            case "MC4063":
                return "https://upload.wikimedia.org/wikipedia/commons/8/8d/UWaterloo_MC.jpg";
            case "QNC2020":
                return "https://c1.staticflickr.com/9/8480/8189020665_d2e7aa1c61_b.jpg";
        }
        return "http://sais2011.uwaterloo.ca/images/UW_E5_byMatthewManjos.png";
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
    }

    @Override
    public void onSetValues(Room room, PositionInfo positionInfo) {
        mRoomText.setText(room.name);
        mLocationText.setText(room.location);
        Picasso.with(getContext())
                .load(getImageUrl(room.location)).placeholder(R.drawable.ic_refresh_pink_500_18dp).error(R.drawable.ic_error_outline_pink_500_18dp)
                .into(mRoomImage);
    }

    private void getMeARoom() {

    }
}

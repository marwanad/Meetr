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

    @ViewId(R.id.text_description)
    TextView mDescriptionText;

    @ViewId(R.id.room_image)
    ImageView mRoomImage;


    public RoomHolder(View view) {
        super(view);
    }

    private String getImageUrl(String name) {
//        switch (name.toLowerCase()) {
//        }
        return "http://img3.wikia.nocookie.net/__cb20091030151422/starwars/images/d/d9/Luke-rotjpromo.jpg";
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
        mDescriptionText.setText(room.description);

        Picasso.with(getContext())
                .load(getImageUrl(room.name))
                .into(mRoomImage);
    }

    private void getMeARoom() {

    }
}

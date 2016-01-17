package ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.model.Room;
import marwanad.meetr.R;
import ui.adapter.RoomHolder;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progress_indicator)
    ProgressBar mProgressBar;

    @Bind(R.id.container_swipe)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.recycler_view)
    RecyclerView mRecycleView;
    EasyRecyclerAdapter<Room> mRoomsAdapter;
    ArrayList<Room> _roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolBar();
        loadRooms();
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setSupportActionBar(mToolbar);
    }

    private void setUpRecyclerView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRoomsAdapter = new EasyRecyclerAdapter<>(this, RoomHolder.class);
        mRoomsAdapter.setItems(_roomList);
        mRecycleView.setAdapter(mRoomsAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mRoomsAdapter.setItems(new ArrayList<Room>());
                refreshRooms();
            }
        });
    }

    private void refreshRooms() {
        mSwipeRefresh.setRefreshing(false);
    }

    private void loadRooms() {
        _roomList = new ArrayList<>();
        _roomList.add(new Room("Sedra Smith", "34873489543dfdsj", "E5", "Nice meme"));
        _roomList.add(new Room("QNC", "dsfsdf3wsd", "Lol", "eat shit"));
        _roomList.add(new Room("EIT 1010", "sdfdsf", "EIT", "eat shit"));
        _roomList.add(new Room("Teach Me Master", "324rsdfs", "E5", "lel kek"));
        _roomList.add(new Room("Sedra Smith", "34873489543dfdsj", "E5", "Nice meme"));
        _roomList.add(new Room("Sedra Smith", "34873489543dfdsj", "E5", "Nice meme"));
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefresh.setRefreshing(false);
    }
}

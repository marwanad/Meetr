package ui.activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.model.BookingRequest;
import data.model.Room;
import listeners.OnBookingCompleteListener;
import marwanad.meetr.R;
import ui.adapter.RoomHolder;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.altbeacon.beacon.startup.BootstrapNotifier;


public class MainActivity extends AppCompatActivity implements OnBookingCompleteListener , BeaconConsumer {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progress_indicator)
    ProgressBar mProgressBar;

    @Bind(R.id.container_swipe)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.recycler_view)
    RecyclerView mRecycleView;
    EasyRecyclerAdapter<Room> mRoomsAdapter;
    List<Room> _roomList;
    private final String URL = "http://meetrapp.herokuapp.com/meetingRooms";
    private final String BOOK_URL = "http://meetrapp.herokuapp.com/bookMeetingRoomById";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient _client = new OkHttpClient();
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;
    private int currentUuid;
    private int lastUuid = -1;
    private boolean activateBeacon = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolBar();
        getRooms();
        setUpRecyclerView();

        // Beacon
        backgroundPowerSaver = new BackgroundPowerSaver(this);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:{
                // Reset Beacons
                lastUuid = -1;
                activateBeacon = true;
                return true;}
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Beacon beacon = beacons.iterator().next();
                    double distance = beacon.getDistance();
                    currentUuid = beacon.getServiceUuid();
                    // Action
                    Log.i("beacon", "Detected Beacon");
                    if (activateBeacon) {
                        Log.i("beacon", "The first beacon I see is about " + distance + " meters away.");
                        lastUuid = currentUuid;
                        activateBeacon = false;
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }


    private void setUpToolBar() {
        setSupportActionBar(mToolbar);
    }

    private void setUpRecyclerView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRoomsAdapter = new EasyRecyclerAdapter<>(this, RoomHolder.class);
        mSwipeRefresh.setColorSchemeResources(R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRooms();
            }
        });
    }

    private void refreshRooms() {
        mSwipeRefresh.setRefreshing(false);
    }

    private void loadRooms(String response) {
        Gson gson = new Gson();
        Room[] room = gson.fromJson(response, Room[].class);
        _roomList = Arrays.asList(room);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setListForAdapter();
            }
        });
    }

    private void sendBookingRequest(String id, String start, String end) {
        String req = createBookingRequest(id, start, end);
        RequestBody body = RequestBody.create(JSON, req);
        Request request = new Request.Builder().url(BOOK_URL).post(body).build();
        _client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
            }
        });
    }

    private String createBookingRequest(String id, String start, String end) {
        BookingRequest request = new BookingRequest(id, start, end, "marw");
        Gson gson = new Gson();
        return gson.toJson(request);
    }

    private void getRooms() {
        Request request = new Request.Builder().url(URL).build();
        _client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "Please check your connection.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                loadRooms(response.body().string());
            }
        });
    }

    private void setListForAdapter() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefresh.setRefreshing(false);
        mRoomsAdapter.setItems(_roomList);
        mRecycleView.setAdapter(mRoomsAdapter);
    }

    @Override
    public void onComplete(String id, String start, String end) {
        sendBookingRequest(id, start, end);
    }
}

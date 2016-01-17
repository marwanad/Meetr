package ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import marwanad.meetr.R;

public class UnLockDialogFragment extends DialogFragment {
    private static final String UNLOCK_URL = "http://meetrapp.herokuapp.com/unlockDoor";
    private OkHttpClient _client = new OkHttpClient();
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        unlockDoor(UNLOCK_URL);
        return dialog;
    }

    private void unlockDoor(String url) {
        Request request = new Request.Builder().url(url).build();
        _client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(getActivity(), "Please check your connection.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                dismissDialog(response.body().string());
            }
        });
    }

    private void dismissDialog(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.contains("OK")) {
                    Toast.makeText(getActivity(), "Door unlocked!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "Door remains locked", Toast.LENGTH_LONG).show();
                }
                MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.unlock);
                mp.start();
                dismiss();
            }
        });
    }
}

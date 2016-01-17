package ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import listeners.OnRoomUnlockRequest;
import marwanad.meetr.R;

public class UnLockDialogFragment extends DialogFragment {
    private OnRoomUnlockRequest _onRoomUnlockRequest;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.unlock);
        mp.start();
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
        unlockDoor();
        return dialog;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this._onRoomUnlockRequest = (OnRoomUnlockRequest) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + "activity hasn't implemented listener yet");
        }
    }

    private void unlockDoor() {
        _onRoomUnlockRequest.onRoomUnlock();
    }
}

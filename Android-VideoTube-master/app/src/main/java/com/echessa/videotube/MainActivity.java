package com.echessa.videotube;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echessa.videotube.utils.TimeAndDate;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "MainActivity";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    private String mLink = "https://www.youtube.com/watch?v=Vk8_0QaJr3I";
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();
        mData.child("Link").addValueEventListener(mValueListener);
    }

    private ValueEventListener mValueListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "onDataChange: ");
            mLink = dataSnapshot.getValue().toString();
            if (mLink.contains("https://www.youtube.com/watch")) {
                mLink = mLink.substring(32);
            }
            loadAnotherVideo();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void loadAnotherVideo() {
        if (player != null) {
            player.cueVideo(mLink);
            mData.child("Log").child(TimeAndDate.convert()).setValue("Got Another Video with URL: https://www.youtube.com/watch?v=" + mLink);
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            player.cueVideo(mLink); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
        Log.d(TAG, "onInitializationSuccess: ");
        mData.child("Log").child(TimeAndDate.convert()).setValue("onInitializationSuccess");
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "onInitializationFailure: ");
        mData.child("Log").child(TimeAndDate.convert()).setValue("onInitializationSuccess");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
        Log.d(TAG, "onActivityResult: ");
        mData.child("Log").child(TimeAndDate.convert()).setValue("onActivityResult");
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
            Log.d(TAG, "onPlaying: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onPlaying");
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
            Log.d(TAG, "onPaused: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onPause");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            Log.d(TAG, "onStopped: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onStop");
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
            Log.d(TAG, "onBuffering: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onBuffering");
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
            mData.child("Log").child(TimeAndDate.convert()).setValue("onSeekTo");
            Log.d(TAG, "onSeekTo: ");
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
            Log.d(TAG, "onLoading: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onLoading");
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
            player.play();
            mData.child("Log").child(TimeAndDate.convert()).setValue("onLoaded: https://www.youtube.com/watch?v=" + s);
            Log.d(TAG, "onLoaded: ");
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
            Log.d(TAG, "onAdStarted: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onAdStarted");
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
            Log.d(TAG, "onVideoStarted: ");
            mData.child("Log").child(TimeAndDate.convert()).setValue("onVideoStarted");
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
            mData.child("Log").child(TimeAndDate.convert()).setValue("onVideoEnd");
            Log.d(TAG, "onVideoEnded: ");
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
            mData.child("Log").child(TimeAndDate.convert()).setValue("onError: " + errorReason.toString());
            Log.d(TAG, "onError: " + errorReason.toString());
        }
    }
}

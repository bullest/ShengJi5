package com.bullest.shengji5;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {
    private RecyclerView masterCardsView;
    private RecyclerView spadeCardsView;
    private RecyclerView heartCardsView;
    private RecyclerView clubCardsView;
    private RecyclerView diamondCardsView;

    private Button sendButton;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        sendButton = (Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.getInstance().initGame();
            }
        });

        masterCardsView = (RecyclerView) findViewById(R.id.self_game_master_cards_view);
        LinearLayoutManager layoutManager_master = new LinearLayoutManager(this);
        layoutManager_master.setOrientation(LinearLayoutManager.HORIZONTAL);
        masterCardsView.setLayoutManager(layoutManager_master);
        CardsAdapter masterCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_master());
        masterCardsAdapter.notifyDataSetChanged();
        masterCardsView.setAdapter(new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_master()));

        spadeCardsView = (RecyclerView) findViewById(R.id.self_game_spade_cards_view);
        LinearLayoutManager layoutManager_spade = new LinearLayoutManager(this);
        layoutManager_spade.setOrientation(LinearLayoutManager.HORIZONTAL);
        spadeCardsView.setLayoutManager(layoutManager_spade);
        spadeCardsView.setAdapter(new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_spade()));

        heartCardsView = (RecyclerView) findViewById(R.id.self_game_heart_cards_view);
        LinearLayoutManager layoutManager_heart = new LinearLayoutManager(this);
        layoutManager_heart.setOrientation(LinearLayoutManager.HORIZONTAL);
        heartCardsView.setLayoutManager(layoutManager_heart );
        heartCardsView.setAdapter(new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_heart()));

        clubCardsView = (RecyclerView) findViewById(R.id.self_game_club_cards_view);
        LinearLayoutManager layoutManager_club = new LinearLayoutManager(this);
        layoutManager_club.setOrientation(LinearLayoutManager.HORIZONTAL);
        clubCardsView.setLayoutManager(layoutManager_club);
        clubCardsView.setAdapter(new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_club()));

        diamondCardsView = (RecyclerView) findViewById(R.id.self_game_diamond_cards_view);
        LinearLayoutManager layoutManager_diamond = new LinearLayoutManager(this);
        layoutManager_diamond.setOrientation(LinearLayoutManager.HORIZONTAL);
        diamondCardsView.setLayoutManager(layoutManager_diamond);
        diamondCardsView.setAdapter(new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_diamond()));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
package com.bullest.shengji5.welcome;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bullest.shengji5.*;
import com.bullest.shengji5.data.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private PlayerAdapter adapter;
    private Button startButton;
    private EditText mUsernameEditText;
    private WelcomeViewModel mWelcomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWelcomeViewModel = new WelcomeViewModel();
        setContentView(R.layout.activity_welcome);

        mUsernameEditText = findViewById(R.id.username);

        startButton = findViewById(R.id.start_button);

        if (mWelcomeViewModel.getAvailablePosition() == -1 ) {
            mUsernameEditText.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.INVISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mUsernameEditText.getText().toString().isEmpty() && mWelcomeViewModel.getAvailablePosition() != -1){
                    mUsernameEditText.setFocusable(false);
                    mWelcomeViewModel.addCurrentPlayer(new Player(mWelcomeViewModel.getAvailablePosition(), mUsernameEditText.getText().toString()));
                    mUsernameEditText.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.player_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mWelcomeViewModel.getPlayer().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                adapter = new PlayerAdapter(players);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (mWelcomeViewModel.isAllReady()) {
                    Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

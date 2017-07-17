package com.bullest.shengji5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private List<Player> mPlayerList = new ArrayList<Player>();
    private PlayerAdapter adapter;
    private Button startButton;
    private Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initPlayerList();

        setContentView(R.layout.activity_welcome);

        startButton = (Button) findViewById(R.id.start_button);
        exitButton = (Button) findViewById(R.id.exit_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMyselfReady();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager.getInstance().logoutUser();
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.player_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlayerAdapter(mPlayerList);
        recyclerView.setAdapter(adapter);

    }

    private void setMyselfReady() {
        if (UserManager.getInstance().getPlayerPosition() == 5){
            MatchManager.getInstance().initMatch();
        }
        SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players").child("player"+ UserManager.getInstance().getPlayerPosition());
        ref.child("isready").setValue(1);
    }

    private void setMyselfNotReady() {
        SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players").child("player"+ UserManager.getInstance().getPlayerPosition());
        ref.child("isready").setValue(0);
    }

    private void initPlayerList() {
        SyncReference ref = WilddogSync.getInstance().getReference().child("match").child("players");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mPlayerList.clear();
                if (snapshot.getValue() != null) {
                    for (int i = 1; i <= 5; i++){
                        Player player;
                        DataSnapshot playerSnapshot = snapshot.child("player"+i);
                        if (playerSnapshot.exists()){
                            player = new Player((long)playerSnapshot.child("position").getValue(), (String)playerSnapshot.child("name").getValue(), (long)playerSnapshot.child("isready").getValue());
                            mPlayerList.add(i-1, player);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                Boolean areAllReady = true;
                for (int i=0;i<5;i++){
                    areAllReady = areAllReady && mPlayerList.get(i).isReady();
                }
                if (areAllReady){

                    Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(SyncError error) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        setMyselfNotReady();
    }

}

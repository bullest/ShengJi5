package com.bullest.shengji5;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.bullest.shengji5.CARD_SUIT.JOKER;

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

    private GridView playStatusGridView;

    private Button sendButton;

    CardsAdapter masterCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_master());
    CardsAdapter spadeCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_spade());
    CardsAdapter heartCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_heart());
    CardsAdapter clubCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_club());
    CardsAdapter diamondCardsAdapter = new CardsAdapter(GameManager.getInstance().getPlayer_cards_list_diamond());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        playStatusGridView = (GridView) findViewById(R.id.players_status_view);

        sendButton = (Button)findViewById(R.id.send_button);

        masterCardsView = (RecyclerView) findViewById(R.id.self_game_master_cards_view);
        masterCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_master = new LinearLayoutManager(this);
        layoutManager_master.setOrientation(LinearLayoutManager.HORIZONTAL);
        masterCardsView.setLayoutManager(layoutManager_master);
        masterCardsView.setAdapter(masterCardsAdapter);

        spadeCardsView = (RecyclerView) findViewById(R.id.self_game_spade_cards_view);
        spadeCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_spade = new LinearLayoutManager(this);
        layoutManager_spade.setOrientation(LinearLayoutManager.HORIZONTAL);
        spadeCardsView.setLayoutManager(layoutManager_spade);
        spadeCardsView.setAdapter(spadeCardsAdapter);

        heartCardsView = (RecyclerView) findViewById(R.id.self_game_heart_cards_view);
        LinearLayoutManager layoutManager_heart = new LinearLayoutManager(this);
        layoutManager_heart.setOrientation(LinearLayoutManager.HORIZONTAL);
        heartCardsView.setLayoutManager(layoutManager_heart );
        heartCardsView.setAdapter(heartCardsAdapter);

        clubCardsView = (RecyclerView) findViewById(R.id.self_game_club_cards_view);
        LinearLayoutManager layoutManager_club = new LinearLayoutManager(this);
        layoutManager_club.setOrientation(LinearLayoutManager.HORIZONTAL);
        clubCardsView.setLayoutManager(layoutManager_club);
        clubCardsView.setAdapter(clubCardsAdapter);

        diamondCardsView = (RecyclerView) findViewById(R.id.self_game_diamond_cards_view);
        LinearLayoutManager layoutManager_diamond = new LinearLayoutManager(this);
        layoutManager_diamond.setOrientation(LinearLayoutManager.HORIZONTAL);
        diamondCardsView.setLayoutManager(layoutManager_diamond);
        diamondCardsView.setAdapter(diamondCardsAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.getInstance().initGame();
                GameManager.getInstance().dispatchCards();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onPlayerCardsChangeEvent(PlayerCardsChangeEvent event){
        Log.d("diapatching", "get");
        switch (event.getSuit()) {
            case JOKER:
                masterCardsAdapter.notifyDataSetChanged();
                break;
            case SPADE:
                spadeCardsAdapter.notifyDataSetChanged();
                break;
            case HEART:
                heartCardsAdapter.notifyDataSetChanged();
                break;
            case CLUB:
                clubCardsAdapter.notifyDataSetChanged();
                break;
            case DIAMOND:
                diamondCardsAdapter.notifyDataSetChanged();
                break;
        }
    }
}


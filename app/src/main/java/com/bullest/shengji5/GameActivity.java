package com.bullest.shengji5;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bullest.shengji5.data.CARD_SUIT;
import com.bullest.shengji5.data.Card;
import com.bullest.shengji5.data.Message;
import com.bullest.shengji5.data.PlayerStatus;
import com.bullest.shengji5.handCard.HandCardViewModel;
import com.bullest.shengji5.message.MessageAdapter;
import com.bullest.shengji5.message.MessageViewModel;
import com.bullest.shengji5.playerStatus.PlayerStatusAdapter;
import com.bullest.shengji5.playerStatus.PlayerStatusViewModel;
import com.bullest.shengji5.roundCard.RoundCardAdapter;
import com.bullest.shengji5.roundCard.RoundCardViewModel;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private RecyclerView masterCardsView;
    private RecyclerView spadeCardsView;
    private RecyclerView heartCardsView;
    private RecyclerView clubCardsView;
    private RecyclerView diamondCardsView;
    private RecyclerView messageView;
    private RecyclerView playerStatusView;

    private MessageAdapter mMessageAdapter;
    private PlayerStatusAdapter mPlayerStatusAdapter;

    private GameViewModel mGameViewModel;

    private MessageViewModel mMessageViewModel;
    private PlayerStatusViewModel mPlayerStatusViewModel;


    private List<RecyclerView> roundCardView = new ArrayList<>(5);
    private RoundCardViewModel mRoundCardViewModel;

    private HandCardViewModel mHandCardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initMessageBox();
        initPlayerStatus();
        initRoundCard();
        initHandCard();

        new BottomDialog.Builder(this)
                .setTitle("准备")
                .setContent("第一轮比赛，先亮主者坐庄。")
                .setPositiveText("开始")
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        mPlayerStatusViewModel.setSelfReady();
                    }
                })
                .show();


//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mGameViewModel.initMatch();
//                mGameViewModel.initGame();
//                mGameViewModel.dispatchCard();
//                GameManager.getInstance().initGame();
//                GameManager.getInstance().dispatchCards();
//            }
//        });
    }

    private void initRoundCard() {
        mRoundCardViewModel = new RoundCardViewModel();

        roundCardView.add(0, (RecyclerView) findViewById(R.id.player1_round_card));
        roundCardView.add(1, (RecyclerView) findViewById(R.id.player2_round_card));
        roundCardView.add(2, (RecyclerView) findViewById(R.id.player3_round_card));
        roundCardView.add(3, (RecyclerView) findViewById(R.id.player4_round_card));
        roundCardView.add(4, (RecyclerView) findViewById(R.id.player5_round_card));

        roundCardView.get(0).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roundCardView.get(1).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roundCardView.get(2).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roundCardView.get(3).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roundCardView.get(4).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final List<RoundCardAdapter> adapters = new ArrayList<>(5);

        mRoundCardViewModel.getRoundCardAt(1).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                roundCardView.get(0).setAdapter(new RoundCardAdapter(cards));
            }
        });

        mRoundCardViewModel.getRoundCardAt(2).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                roundCardView.get(1).setAdapter(new RoundCardAdapter(cards));
            }
        });

        mRoundCardViewModel.getRoundCardAt(3).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                roundCardView.get(2).setAdapter(new RoundCardAdapter(cards));
            }
        });

        mRoundCardViewModel.getRoundCardAt(4).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                roundCardView.get(3).setAdapter(new RoundCardAdapter(cards));
            }
        });

        mRoundCardViewModel.getRoundCardAt(5).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                roundCardView.get(4).setAdapter(new RoundCardAdapter(cards));
            }
        });

    }

    private void initHandCard() {
        mHandCardViewModel = new HandCardViewModel();

        masterCardsView = findViewById(R.id.self_game_master_cards_view);
        masterCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_master = new LinearLayoutManager(this);
        layoutManager_master.setOrientation(LinearLayoutManager.HORIZONTAL);
        masterCardsView.setLayoutManager(layoutManager_master);
        mHandCardViewModel.getHandCard(CARD_SUIT.JOKER).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                masterCardsView.setAdapter(new SelectableCardsAdapter(cards));
            }
        });

        spadeCardsView = findViewById(R.id.self_game_spade_cards_view);
        spadeCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_spade = new LinearLayoutManager(this);
        layoutManager_spade.setOrientation(LinearLayoutManager.HORIZONTAL);
        spadeCardsView.setLayoutManager(layoutManager_spade);
        mHandCardViewModel.getHandCard(CARD_SUIT.SPADE).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                spadeCardsView.setAdapter(new SelectableCardsAdapter(cards));
            }
        });

        heartCardsView = findViewById(R.id.self_game_heart_cards_view);
        LinearLayoutManager layoutManager_heart = new LinearLayoutManager(this);
        layoutManager_heart.setOrientation(LinearLayoutManager.HORIZONTAL);
        heartCardsView.setLayoutManager(layoutManager_heart );
        mHandCardViewModel.getHandCard(CARD_SUIT.HEART).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                heartCardsView.setAdapter(new SelectableCardsAdapter(cards));
            }
        });

        clubCardsView = findViewById(R.id.self_game_club_cards_view);
        LinearLayoutManager layoutManager_club = new LinearLayoutManager(this);
        layoutManager_club.setOrientation(LinearLayoutManager.HORIZONTAL);
        clubCardsView.setLayoutManager(layoutManager_club);
        mHandCardViewModel.getHandCard(CARD_SUIT.CLUB).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                clubCardsView.setAdapter(new SelectableCardsAdapter(cards));
            }
        });

        diamondCardsView = findViewById(R.id.self_game_diamond_cards_view);
        LinearLayoutManager layoutManager_diamond = new LinearLayoutManager(this);
        layoutManager_diamond.setOrientation(LinearLayoutManager.HORIZONTAL);
        diamondCardsView.setLayoutManager(layoutManager_diamond);
        mHandCardViewModel.getHandCard(CARD_SUIT.DIAMOND).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                diamondCardsView.setAdapter(new SelectableCardsAdapter(cards));
            }
        });
    }

    private void initPlayerStatus() {
        playerStatusView = findViewById(R.id.players_status_view);
        GridLayoutManager playerStatusLayoutManager = new GridLayoutManager(this, 2);
        playerStatusView.setLayoutManager(playerStatusLayoutManager);
        playerStatusView.setHasFixedSize(true);

        mPlayerStatusViewModel = new PlayerStatusViewModel();

        PlayerStatusViewModel mPlayerStatusViewModel = new PlayerStatusViewModel();

//        if (SelfSingleton.getInstance().isHoster()) {
//            mPlayerStatusViewModel.resetAllPlayerStatus();
//        }

        mPlayerStatusViewModel.resetAllPlayerStatus();

        mPlayerStatusViewModel.getOtherPlayerStatus().observe(this, new Observer<List<PlayerStatus>>() {
            @Override
            public void onChanged(@Nullable List<PlayerStatus>  playerStatus) {
                mPlayerStatusAdapter = new PlayerStatusAdapter(playerStatus);
                playerStatusView.setAdapter(mPlayerStatusAdapter);
                mPlayerStatusAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initMessageBox() {
        messageView = findViewById(R.id.message_recycler_view);
        LinearLayoutManager messageLayoutManager = new LinearLayoutManager(this);
        messageLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageView.setLayoutManager(messageLayoutManager);

        mMessageViewModel = new MessageViewModel();

        mMessageViewModel.getMessage().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                mMessageAdapter = new MessageAdapter(messages);
                messageView.setAdapter(mMessageAdapter);
                mMessageAdapter.notifyDataSetChanged();
            }
        });
    }
}


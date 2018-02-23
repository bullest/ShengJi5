package com.bullest.shengji5;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.bullest.shengji5.data.Message;
import com.bullest.shengji5.message.MessageAdapter;
import com.bullest.shengji5.message.MessageViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    private RecyclerView masterCardsView;
    private RecyclerView spadeCardsView;
    private RecyclerView heartCardsView;
    private RecyclerView clubCardsView;
    private RecyclerView diamondCardsView;
    private RecyclerView messageView;

    private MessageAdapter mMessageAdapter;

    private GridView playStatusGridView;

    private GameViewModel mGameViewModel;

    private MessageViewModel mMessageViewModel;
    private EditText messageInputView;

    private Button sendButton;

    private

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

        initMessageBox();

        playStatusGridView = findViewById(R.id.players_status_view);

        masterCardsView = findViewById(R.id.self_game_master_cards_view);
        masterCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_master = new LinearLayoutManager(this);
        layoutManager_master.setOrientation(LinearLayoutManager.HORIZONTAL);
        masterCardsView.setLayoutManager(layoutManager_master);
        masterCardsView.setAdapter(masterCardsAdapter);

        spadeCardsView = findViewById(R.id.self_game_spade_cards_view);
        spadeCardsView.setHasFixedSize(false);
        LinearLayoutManager layoutManager_spade = new LinearLayoutManager(this);
        layoutManager_spade.setOrientation(LinearLayoutManager.HORIZONTAL);
        spadeCardsView.setLayoutManager(layoutManager_spade);
        spadeCardsView.setAdapter(spadeCardsAdapter);

        heartCardsView = findViewById(R.id.self_game_heart_cards_view);
        LinearLayoutManager layoutManager_heart = new LinearLayoutManager(this);
        layoutManager_heart.setOrientation(LinearLayoutManager.HORIZONTAL);
        heartCardsView.setLayoutManager(layoutManager_heart );
        heartCardsView.setAdapter(heartCardsAdapter);

        clubCardsView = findViewById(R.id.self_game_club_cards_view);
        LinearLayoutManager layoutManager_club = new LinearLayoutManager(this);
        layoutManager_club.setOrientation(LinearLayoutManager.HORIZONTAL);
        clubCardsView.setLayoutManager(layoutManager_club);
        clubCardsView.setAdapter(clubCardsAdapter);

        diamondCardsView = findViewById(R.id.self_game_diamond_cards_view);
        LinearLayoutManager layoutManager_diamond = new LinearLayoutManager(this);
        layoutManager_diamond.setOrientation(LinearLayoutManager.HORIZONTAL);
        diamondCardsView.setLayoutManager(layoutManager_diamond);
        diamondCardsView.setAdapter(diamondCardsAdapter);

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

    private void initMessageBox() {
        messageView = findViewById(R.id.message_recycler_view);
        messageInputView = findViewById(R.id.message_box_view);
        sendButton = findViewById(R.id.send_button);
        LinearLayoutManager messageLayoutManager = new LinearLayoutManager(this);
        messageLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageView.setLayoutManager(messageLayoutManager);

        mMessageViewModel = new MessageViewModel();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageInputView.getText().toString();
                if (!message.isEmpty()) {
                    mMessageViewModel.addMessage(message, false);
                }
                messageInputView.setText("");
                messageInputView.clearFocus();
                hideKeyboard(GameActivity.this);
            }
        });

        mMessageViewModel.getMessage().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                mMessageAdapter = new MessageAdapter(messages);
                messageView.setAdapter(mMessageAdapter);
                mMessageAdapter.notifyDataSetChanged();
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


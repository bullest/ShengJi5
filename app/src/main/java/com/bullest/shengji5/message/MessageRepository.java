package com.bullest.shengji5.message;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bullest.shengji5.data.Message;
import com.bullest.shengji5.PlayerRepository;
import com.bullest.shengji5.data.Player;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunfezhang on 2/12/18.
 */

public class MessageRepository {
    private static final int MESSAGE_NUMBER = 6;
    private static final Object syncLock = new Object();
    private static MessageRepository instant;
    CollectionReference messageReference = FirebaseFirestore.getInstance().collection("Messages");
    final MutableLiveData<List<Message>> message = new MutableLiveData<>();

    public MessageRepository() {
        messageReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    message.setValue(documentSnapshots.toObjects(Message.class));
                }
            }
        });
    }

    public static MessageRepository getInstance() {
        synchronized (syncLock) {
            if (instant == null) {
                instant = new MessageRepository();
            }
        }
        return instant;
    }

    public LiveData<List<Message>> getMessage() {
        return message;
    }

    public void addMessage(Message message) {
        messageReference.add(message);
    }
}

package com.nikede.chat.Model;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikede.chat.Crypt.Crypt;
import com.nikede.chat.Database.KeysBaseHelper;
import com.nikede.chat.Database.KeysCursorWrapper;
import com.nikede.chat.Database.KeysSchema;
import com.nikede.chat.MainActivity;
import com.nikede.chat.R;

import org.bouncycastle.util.encoders.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;
    private Context mContext;
    private int key;

    public Chat(String sender, String receiver, String message, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return Crypt.decode(mContext, message, key);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

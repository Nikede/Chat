package com.nikede.chat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nikede.chat.Database.KeysBaseHelper;
import com.nikede.chat.Database.KeysCursorWrapper;
import com.nikede.chat.Database.KeysSchema;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.bouncycastle.util.encoders.Base64;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText email, password;
    Button btn_login;

    FirebaseAuth auth;

    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        KeysCursorWrapper cursor = queryKeys(null, null);
                                        cursor.moveToFirst();
                                        int pblcKey = 0;
                                        if (!cursor.isAfterLast()) {
                                            pblcKey = cursor.getKey();
                                        }
                                        if (pblcKey == 0) {
                                            FirebaseUser firebaseUser = auth.getCurrentUser();
                                            assert firebaseUser != null;
                                            String userid = firebaseUser.getUid();
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                            HashMap<String, String> hashMap = new HashMap<>();
                                            try {
                                                int key = new Random().nextInt(19) + 2;
                                                int A = (int) Math.pow(5, key);
                                                hashMap.put("key", Integer.toString(A % 23));

                                                SQLiteDatabase mDatabase = new KeysBaseHelper(LoginActivity.this).getWritableDatabase();
                                                mDatabase.insert(KeysSchema.KeysTable.NAME, null, getContentValues(userid, key));
                                            } catch (Exception e) {
                                                Toast.makeText(LoginActivity.this, getString(R.string.smth_went_wrong), Toast.LENGTH_SHORT).show();
                                            }

                                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private KeysCursorWrapper queryKeys(String whereClause, String[] whereArgs) {
        Cursor cursor = new KeysBaseHelper(LoginActivity.this).getWritableDatabase().query(
                KeysSchema.KeysTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new KeysCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(String userId, int key) {
        ContentValues values = new ContentValues();
        values.put(KeysSchema.KeysTable.Cols.sUserId, userId);
        values.put(KeysSchema.KeysTable.Cols.sKey, Integer.toString(key));
        return values;
    }
}

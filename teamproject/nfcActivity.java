package com.teamproject;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class nfcActivity extends AppCompatActivity {

    private TextView    textName, dvID;
    private Button      btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        // 아이디 값 찾아주기
        textName    = findViewById(R.id.textName);
        dvID        = findViewById(R.id.dvID);
        btn_logout  = findViewById(R.id.btn_logout);


        Intent intent   = getIntent();
        String userName = intent.getStringExtra("inputName");
        String phoneID  = intent.getStringExtra("inputNumber");

        // 사용자 이름 넣어줌
        textName.setText(userName);
        dvID.setText(phoneID);

        // NFC
//        NfcAdapter sNfcAdapter =  NfcAdapter.getDefaultAdapter(this) ;
//
//        if (sNfcAdapter == null) {
//            // NFC 미지원단말
//            Toast.makeText(getApplicationContext(), "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
//            return;
//        }


        // 로그아웃

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(nfcActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();

                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(nfcActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });


    }
}



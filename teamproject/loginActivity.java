package com.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {

    private EditText    textID, textPW;
    private Button      login, ok;
    String              loginID, loginPW, loginName, loginNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 아이디 값 찾아주기
        textID  = findViewById(R.id.textID);
        textPW  = findViewById(R.id.textPW);
        login   = findViewById(R.id.login);
        ok      = findViewById(R.id.ok);


        // 자동로그인
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginID     = auto.getString("inputID", null);
        loginPW     = auto.getString("inputPW", null);
        loginName   = auto.getString("inputName", null);
        //serial number 구하면 지우기
        loginNumber = auto.getString("inputNumber", null);

        // 아이디 비번 저장 되어 있을 시
        if(loginID != null && loginPW != null){
            Toast.makeText(loginActivity.this, loginName + "님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(loginActivity.this, nfcActivity.class);
            intent.putExtra("inputName", loginName);
            //serial number 구하면 지우기
            intent.putExtra("inputNumber", loginNumber);
            startActivity(intent);
            finish();
        }
        // 아이디 비번 저장 안되있을 시
        else if(loginID == null && loginPW == null) {


            // 회원가입 버튼 클릭 시 수행
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // 로그인 버튼 클릭 시 수행
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 변수에 입력값을 담음
                    String userID = textID.getText().toString();
                    String userPW = textPW.getText().toString();

                    // 시리얼 넘버
                    final String serial = Build.SERIAL;

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean sign_up = jsonObject.getBoolean("sign_up");
                                if (sign_up) { // 로그인 성공한 경우

                                    // 정보 저장
                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor autoLogin = auto.edit();
                                    autoLogin.putString("inputID", textID.getText().toString());
                                    autoLogin.putString("inputPW", textPW.getText().toString());

                                    String userName = jsonObject.getString("name");
                                    String phoneID = serial;
                                    autoLogin.putString("inputName", userName);
                                    //serial number 구하면 지우기
                                    autoLogin.putString("inputNumber", phoneID);

                                    autoLogin.commit();

                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(loginActivity.this, nfcActivity.class);

                                    intent.putExtra("inputName", userName);
                                    //serial number 구하면 지우기
                                    intent.putExtra("inputNumber", phoneID);
                                    startActivity(intent);
                                } else { // 로그인 실패한 경우
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    // 서버로 Volley를 이용해 요청
                    LoginRequest loginRequest = new LoginRequest(userID, userPW, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                    queue.add(loginRequest);

                }
            });

        }
    }
}

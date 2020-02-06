package com.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText    textName, textID, textPW;
    private Button      ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아이디 값 찾아주기
        textName    = findViewById(R.id.textName);
        textID      = findViewById(R.id.textID);
        textPW      = findViewById(R.id.textPW);

        // 회원가입 버튼 클릭 시 수행
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // 변수에 입력값을 담음
                String userName = textName.getText().toString();
                String userID   = textID.getText().toString();
                String userPW   = textPW.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean sign_up = jsonObject.getBoolean("sign_up");
                            if(sign_up){ // 회원가입 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원가입 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                                startActivity(intent);
                            } else { // 회원가입 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해 요청
                RegisterRequest registerRequest = new RegisterRequest(userName, userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(registerRequest);

            }
        });

    }
}

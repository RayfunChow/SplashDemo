package com.example.asus.splashdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton loginButton=findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatEditText usernameEdit=findViewById(R.id.usernameEdit);
                AppCompatEditText passwordEdit=findViewById(R.id.passwordEdit);
                String username=usernameEdit.getText().toString();
                String password=passwordEdit.getText().toString();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginService loginService=retrofit.create(LoginService.class);

                Call<Result> loginCall = loginService.login(username, password);

                loginCall.enqueue(new Callback<Result>() {
                    private static final String TAG = "aaa";

                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()){
                            Log.i(TAG, response.raw().toString());
                            Result result = response.body();
                            Log.i(TAG, result.toString());
                            if (result.isSuccess()){
                                Toast.makeText(MainActivity.this, "登陆成功",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "登陆失败",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "登陆失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        //通信失败，处理失败结果
                        Log.w(TAG,t.getMessage());
                        t.printStackTrace();
                    }
                });

            }
        });

    }
}

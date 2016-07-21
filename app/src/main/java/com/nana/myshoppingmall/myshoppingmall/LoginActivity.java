package com.nana.myshoppingmall.myshoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nana.myshoppingmall.myshoppingmall.api.request.PostLoginRequest;
import com.nana.myshoppingmall.myshoppingmall.api.response.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PostLoginRequest.OnPostLoginRequestListener {

    private TextView tvRegister;
    private Button btnLogin;
    private AppPreference appPref;
    private EditText edtUsername, edtPass;

    private PostLoginRequest postLoginRequest;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login Top");

        appPref = new AppPreference(LoginActivity.this);

        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        edtUsername = (EditText) findViewById(R.id.edt_user);
        edtPass = (EditText) findViewById(R.id.edt_pass);

        postLoginRequest = new PostLoginRequest();
        postLoginRequest.setOnPostLoginRequestListener(this);
        progDialog = new ProgressDialog(LoginActivity.this);
        progDialog.setTitle("Login");
        progDialog.setMessage("Please wait...");

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        boolean isLogin = false;
        switch (v.getId())
        {
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.btn_login:
                String username = edtUsername.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this, "All field are required", Toast.LENGTH_SHORT).show();
                else
                {
                    //appPref.setUsername(username);
                    //intent = new Intent(LoginActivity.this, CategoryActivity.class);
                    //isLogin = true;

                    RequestParams params = new RequestParams();
                    params.put("username", username);
                    params.put("password", pass);

                    postLoginRequest.setParams(params);
                    progDialog.show();
                    postLoginRequest.callApi();

                }
                //intent = new Intent(LoginActivity.this, CategoryActivity.class);
                break;
        }

        if(intent != null)
        {
            startActivity(intent);
            //if(isLogin) finish(); //finish buat destroy sehingga bila setelah login dan pindah ke home begitu di back tidak masuk ke halaman login
        }

    }

    @Override
    public void onPostLoginSuccess(User user) {
        progDialog.cancel();
        appPref.setUserID(user.getUserId());
        appPref.setUsername(user.getUsername());
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostLoginFailure(String errorMessage) {
        progDialog.cancel();
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        postLoginRequest.cancelRequest();
        super.onDestroy();
    }
}

package com.nana.myshoppingmall.myshoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nana.myshoppingmall.myshoppingmall.api.request.PostRegisterRequest;
import com.nana.myshoppingmall.myshoppingmall.api.response.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements PostRegisterRequest.OnPostRegisterRequestListener, View.OnClickListener {

    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.cb_toc)
    CheckBox cbToc;
    @BindView(R.id.btn_Register)
    Button btnRegister;

    private ProgressDialog progDialog;
    private PostRegisterRequest postRegisterRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progDialog = new ProgressDialog(RegisterActivity.this);
        progDialog.setTitle("Register");
        progDialog.setMessage("Please wait...");

        postRegisterRequest = new PostRegisterRequest();
        postRegisterRequest.setOnPostRegisterRequestListener(this);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_Register) {
            String username = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(email))
                Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            else {
                if(cbToc.isChecked()) {
                    RequestParams params = new RequestParams();
                    params.put("username", username);
                    params.put("password", pass);
                    params.put("email", email);

                    postRegisterRequest.setPostRequestParams(params);
                    progDialog.show();
                    postRegisterRequest.callApi();
                }
                else Toast.makeText(RegisterActivity.this, "You haven't checked the TOC", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onPostRegisterSuccess(BaseResponse response) {
        progDialog.cancel();
        Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostRegisterFailure(String errorMessage) {
        progDialog.cancel();
        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT);

    }

    @Override
    protected void onDestroy() {
        postRegisterRequest.cancelRequest();
        super.onDestroy();
    }
}

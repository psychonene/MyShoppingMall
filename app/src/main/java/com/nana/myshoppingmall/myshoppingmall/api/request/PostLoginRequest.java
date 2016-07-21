package com.nana.myshoppingmall.myshoppingmall.api.request;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nana.myshoppingmall.myshoppingmall.LoginActivity;
import com.nana.myshoppingmall.myshoppingmall.api.BaseApi;
import com.nana.myshoppingmall.myshoppingmall.api.response.User;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 21/07/2016.
 */
public class PostLoginRequest extends BaseApi {

    AsyncHttpClient client;
    OnPostLoginRequestListener onPostLoginRequestListener;
    RequestParams params;

    public PostLoginRequest() {
        client = getHttpClient();
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    public void setClient(AsyncHttpClient client) {
        this.client = client;
    }

    public OnPostLoginRequestListener getOnPostLoginRequestListener() {
        return onPostLoginRequestListener;
    }

    public void setOnPostLoginRequestListener(OnPostLoginRequestListener onPostLoginRequestListener) {
        this.onPostLoginRequestListener = onPostLoginRequestListener;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    @Override
    public void callApi() {
        super.callApi();
        client.post(POST_LOGIN, getParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        User user = getUser(response);
                        if( user != null && user.getStatus().equals("200")) getOnPostLoginRequestListener().onPostLoginSuccess(user);
                        else getOnPostLoginRequestListener().onPostLoginFailure("Invalid Response");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        getOnPostLoginRequestListener().onPostLoginFailure("Login failed");
                    }
                }
        );
    }

    private User getUser(String response)
    {
        User user = null;

        try {
            JSONObject jObj = new JSONObject(response);

            user = new User();
            user.setStatus(jObj.getString("status"));
            user.setMessage(jObj.getString("message"));

            if(user.getStatus().equals("200")) {
                //JSONObject userObj = jObj.getJSONObject("user");
                //user.setUserId(userObj.getString("user_id"));
                //user.setUsername(userObj.getString("username"));
                //user.setEmail(userObj.getString("email"));

                JSONObject userObj = jObj.optJSONObject("user");
                user.setUserId(userObj.optString("user_id"));
                user.setUsername(userObj.optString("username"));
                user.setEmail(userObj.optString("email"));
            }


        }catch (Exception e) {
            user=null;
        }

        return user;
    }

    @Override
    public void cancelRequest() {
        if(client != null) client.cancelAllRequests(true);
        super.cancelRequest();
    }

    public interface OnPostLoginRequestListener{
        void onPostLoginSuccess(User user);
        void onPostLoginFailure(String errorMessage);

    }
}

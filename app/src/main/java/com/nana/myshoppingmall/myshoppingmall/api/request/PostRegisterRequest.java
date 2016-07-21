package com.nana.myshoppingmall.myshoppingmall.api.request;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nana.myshoppingmall.myshoppingmall.api.BaseApi;
import com.nana.myshoppingmall.myshoppingmall.api.response.BaseResponse;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 21/07/2016.
 */
public class PostRegisterRequest extends BaseApi {

    private OnPostRegisterRequestListener onPostRegisterRequestListener;
    private RequestParams postRequestParams;
    private AsyncHttpClient client;

    public PostRegisterRequest() {
        client = getHttpClient();
    }

    public OnPostRegisterRequestListener getOnPostRegisterRequestListener() {
        return onPostRegisterRequestListener;
    }

    public void setOnPostRegisterRequestListener(OnPostRegisterRequestListener onPostRegisterRequestListener) {
        this.onPostRegisterRequestListener = onPostRegisterRequestListener;
    }

    public RequestParams getPostRequestParams() {
        return postRequestParams;
    }

    public void setPostRequestParams(RequestParams postRequestParams) {
        this.postRequestParams = postRequestParams;
    }

    @Override
    public void callApi() {
        super.callApi();
        client.post(POST_REGISTER, getPostRequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                BaseResponse mBaseResponse = getBaseResponse(response);
                if(mBaseResponse.getStatus().equals("200")) getOnPostRegisterRequestListener().onPostRegisterSuccess(mBaseResponse);
                else getOnPostRegisterRequestListener().onPostRegisterFailure(mBaseResponse.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnPostRegisterRequestListener().onPostRegisterFailure("Invalid Response");
            }
        });
    }

    @Override
    public void cancelRequest() {
        super.cancelRequest();

        if(client != null) client.cancelAllRequests(true);
    }

    private BaseResponse getBaseResponse(String response){
        BaseResponse baseResponse=null;
        try{
            JSONObject jsonObject = new JSONObject(response);
            //if(jsonObject.getString("status").equals("200")){
                baseResponse = new BaseResponse();
                baseResponse.setStatus(jsonObject.getString("status"));
                baseResponse.setMessage(jsonObject.getString("message"));
            //}

        }
        catch (Exception e){return null;}
        return baseResponse;
    }

    public interface OnPostRegisterRequestListener{
        void onPostRegisterSuccess(BaseResponse response);
        void onPostRegisterFailure(String errorMessage);

    }
}

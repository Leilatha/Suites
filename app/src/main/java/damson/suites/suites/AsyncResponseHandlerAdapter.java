package damson.suites.suites;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Andy on 5/17/2016.
 */
public class AsyncResponseHandlerAdapter<B> extends AsyncHttpResponseHandler {

    AsyncResponseHandler<B> cc;
    Class bb;
    Class listType;


    public AsyncResponseHandlerAdapter (Class bb, AsyncResponseHandler<B> cclass) {
        this.bb = bb;
        cc = cclass;
        listType = null;
    }

    public AsyncResponseHandlerAdapter (Class bb, Class listtype, AsyncResponseHandler<B> cclass) {
        this.bb = bb;
        cc = cclass;
        listType = listtype;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        if(bb == null) {
            cc.onSuccess(null, statusCode, headers, response); //For the "empty response" case
            return;
        }
        B res = null;
        try {
            if(List.class.isAssignableFrom(bb) && listType != null) {
                res = DBHelper.mapper.readValue(response,
                        DBHelper.mapper.getTypeFactory().constructCollectionType(List.class, listType));
            }
            else {
                res = DBHelper.mapper.readValue(response, DBHelper.mapper.constructType(bb));
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        cc.onSuccess(res, statusCode, headers, response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
        if (statusCode == 401) {
            cc.onLoginFailure(headers, errorResponse, e); // i.e. validation failed
        } else {
            cc.onFailure(statusCode, headers, errorResponse, e);
        }
    }

    @Override
    public void onFinish() {
        cc.onFinish();
    }
}

package damson.suites.suites;

import cz.msebera.android.httpclient.Header;

/**
 * B: The response type, ie DBGenericResult, DBGroceryListResponse
 * Created by Andy on 5/17/2016.
 */
public abstract class AsyncResponseHandler<B>  {
    public abstract void onSuccess(B response, int statusCode, Header[] headers,
                                   byte[] errorResponse); // Handle response
    public abstract void onFailure(int statusCode, Header[] headers,
                                   byte[] errorResponse, Throwable e);
    public abstract void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e);  // Called by onFailure
    public void onStart() {}
    public void onRetry() {}
    public void onFinish() {}
}

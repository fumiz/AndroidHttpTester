package me.fumiz.android.test.http;

/**
 * Handler used when change mServer behavior.
 * User: fumiz
 * Date: 11/09/26
 * Time: 21:12
 */
public interface TestServerHandler {
    /**
     * Called by TestServer#start 's head
     */
    public void onStart();

    /**
     * Called by TestServer#stop 's tail
     */
    public void onStop();
}

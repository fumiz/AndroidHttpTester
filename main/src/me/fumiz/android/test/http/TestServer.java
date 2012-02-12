package me.fumiz.android.test.http;

import java.util.concurrent.CountDownLatch;

/**
 * TestServer interface.
 * User: fumiz
 * Date: 11/09/26
 * Time: 21:07
 */
public abstract class TestServer {
    protected CountDownLatch mStartSignal = new CountDownLatch(1);
    protected CountDownLatch mStopSignal = new CountDownLatch(1);

    /**
     * port number used by TestServer
     */
    private final int mPortNumber;

    /**
     * Handler called at Server receive client's request
     */
    protected TestRequestHandler mRequestHandler = null;

    /**
     * Handler called at fired TestServer event
     */
    protected TestServerHandler mServerHandler = null;

    /**
     * init with port number
     * @param portNumber listening port number
     */
    public TestServer(int portNumber) {
        mPortNumber = portNumber;
    }

    /**
     * Start HTTPd.
     * warning: This method must be sync method
     */
    public abstract void start();

    /**
     * Stop HTTPd.
     * warning: This method must be sync method
     */
    public abstract void stop();

    /**
     * wait during server start process
     * @throws InterruptedException throws when interrupted awaiting process
     */
    public void startSync() throws InterruptedException {
        mStartSignal.await();
    }

    /**
     * wait during server stop process
     * @throws InterruptedException throws when interrupted awaiting process
     */
    public void stopSync() throws InterruptedException {
        mStopSignal.await();
    }

    /**
     * get port number
     * @return port number
     */
    public int getPortNumber() {
        return mPortNumber;
    }

    /**
     * Set RequestHandler.
     * Class implements TestServer must call TestRequestHandler#onRequest when Server catch request.
     * @param handler handler used request
     */
    public void setRequestHandler(TestRequestHandler handler) {
        mRequestHandler = handler;
    }

    /**
     * Set ServerHandler.
     * Class implements TestServer must call TestServerHandler#onStart and #onStop
     * when TestServer#start head and TestServer#stop tail
     * @param handler handler used mServer actions
     */
    public void setServerHandler(TestServerHandler handler) {
        mServerHandler = handler;
    }

    /**
     *
     * @param request
     * @return
     */
    protected TestResponse onRequest(TestRequest request) {
        return mRequestHandler.onRequest(request);
    }
}

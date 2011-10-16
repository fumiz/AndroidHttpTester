package me.fumiz.android.test.http;

/**
 * Handler used Server caught HTTP requests.
 * User: fumiz
 * Date: 11/09/26
 * Time: 21:11
 */
public interface TestRequestHandler {
    /**
     * called on request.
     * @param request request object
     * @return response object
     */
    public TestResponse onRequest(TestRequest request);
}

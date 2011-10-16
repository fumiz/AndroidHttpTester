package me.fumiz.test.android.test.http;

import android.test.AndroidTestCase;
import me.fumiz.android.test.http.TestResponse;

/**
 * User: fumiz
 * Date: 11/10/16
 * Time: 16:21
 */
public class TestResponseTest extends AndroidTestCase {
    public void testGetMimeType() {
        assertEquals("text/plain", new TestResponse(TestResponse.MIME_TEXTPLAIN, null).getMimeType());
        assertEquals("text/html", new TestResponse(TestResponse.MIME_TEXTHTML, null).getMimeType());
        assertEquals("application/json", new TestResponse(TestResponse.MIME_JSON, null).getMimeType());
    }

    public void testGetBody() {
        assertEquals("foo", new TestResponse(null, "foo").getBody());
        assertEquals("foo\nbar", new TestResponse(null, "foo\nbar").getBody());
        assertEquals("foo\n\nbar", new TestResponse(null, "foo\n\nbar").getBody());
        assertEquals("マルチバイト文字列", new TestResponse(null, "マルチバイト文字列").getBody());
    }

    public void testGetStatusCode() {
        assertEquals("200 OK", new TestResponse(TestResponse.HTTP_OK, null, null).getStatusCode());
        assertEquals("400 Bad Request", new TestResponse(TestResponse.HTTP_BAD_REQUEST, null, null).getStatusCode());
        assertEquals("401 Unauthorized", new TestResponse(TestResponse.HTTP_UNAUTHORIZED, null, null).getStatusCode());
        assertEquals("403 Forbidden", new TestResponse(TestResponse.HTTP_FORBIDDEN, null, null).getStatusCode());
        assertEquals("404 Not Found", new TestResponse(TestResponse.HTTP_NOTFOUND, null, null).getStatusCode());
    }
}

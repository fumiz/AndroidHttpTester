package me.fumiz.test.android.test.http;

import me.fumiz.android.test.http.HttpTestCase;
import me.fumiz.android.test.http.TestRequest;
import me.fumiz.android.test.http.TestRequestHandler;
import me.fumiz.android.test.http.TestResponse;
import me.fumiz.android.test.http.impl.nano.NanoTestServer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: fumiz
 * Date: 11/09/26
 * Time: 22:04
 */
public class HttpTestCaseTest extends HttpTestCase {
    public void testCreateUrl() {
        int portNumber = getPortNumber();
        assertEquals(String.format("http://127.0.0.1:%d/sample/path/to/file.html",portNumber), createUrl("/sample/path/to/file.html"));
    }

    public void testGetPortNumber() {
        assertEquals(sDefaultPortNumber, getPortNumber());
    }

    public void testCreateTestServer() {
        assertEquals(NanoTestServer.class, createTestServer().getClass());
    }

    public void testGetResponseTest() throws IOException {
        final ExceptionContainer<AssertionError> container = new ExceptionContainer<AssertionError>();
        // set server-side handler
        setRequestHandler(new TestRequestHandler() {
            public TestResponse onRequest(TestRequest request) {
                // test server reached request data
                Properties params = request.getParams();
                Properties headers = request.getHeaders();
                try {
                    assertEquals(TestRequest.METHOD_GET, request.getMethod());

                    assertEquals("/gettest", request.getPath());

                    assertEquals(2, params.size());
                    assertEquals("text", params.getProperty("querystring"));
                    assertEquals("value", params.getProperty("second"));

                    assertEquals("testheadervalue", headers.getProperty("testheader"));
                    assertEquals("sampleheadervalue", headers.getProperty("sampleheader"));
                } catch (AssertionError e) {
                    container.set(e);
                }
                return new TestResponse(TestResponse.MIME_TEXTPLAIN, "aaaabbbcc");
            }
        });

        // make get request
        HttpGet request = new HttpGet(createUrl("/gettest?querystring=text&second=value"));
        request.addHeader("testheader", "testheadervalue");
        request.addHeader("sampleheader", "sampleheadervalue");

        // send request
        HttpClient client = new DefaultHttpClient();
        String ret = client.execute(request, new ResponseHandler<String>() {
            public String handleResponse(HttpResponse httpResponse) throws IOException {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        });

        // test response
        assertEquals("aaaabbbcc", ret);

        // if AssertionError occurred at server-side handler, rethrow it
        if (container.get() != null) {
            throw container.get();
        }
    }

    public void testPostResponseTest() throws IOException {
        final ExceptionContainer<AssertionError> container = new ExceptionContainer<AssertionError>();
        // set server-side handler
        setRequestHandler(new TestRequestHandler() {
            public TestResponse onRequest(TestRequest request) {
                // test server reached request data
                Properties params = request.getParams();
                Properties headers = request.getHeaders();
                try {
                    assertEquals(TestRequest.METHOD_POST, request.getMethod());

                    assertEquals("/posttest", request.getPath());

                    assertEquals(1, params.size());
                    assertEquals("bar", params.getProperty("foo"));

                    assertEquals("testheadervalue", headers.getProperty("testheader"));
                    assertEquals("sampleheadervalue", headers.getProperty("sampleheader"));
                } catch (AssertionError e) {
                    container.set(e);
                }
                return new TestResponse(TestResponse.MIME_TEXTPLAIN, "aaaabbbcc");
            }
        });

        // make POST request
        HttpPost request = new HttpPost(createUrl("/posttest"));
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("foo", "bar"));
        request.setEntity(new UrlEncodedFormEntity(params));

        request.addHeader("testheader", "testheadervalue");
        request.addHeader("sampleheader", "sampleheadervalue");

        // send request
        HttpClient client = new DefaultHttpClient();
        String ret = client.execute(request, new ResponseHandler<String>() {
            public String handleResponse(HttpResponse httpResponse) throws IOException {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        });
        
        // test response
        assertEquals("aaaabbbcc", ret);

        // if AssertionError occurred at server-side handler, rethrow it
        if (container.get() != null) {
            throw container.get();
        }
    }

    public void testExecution() throws IOException {
        HttpPost request = new HttpPost(createUrl("/post.php"));

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("foo","body"));
        request.setEntity(new UrlEncodedFormEntity(params));

        String response = execute(new TestRequestHandler() {
            public TestResponse onRequest(TestRequest request) {
                assertEquals("/post.php", request.getPath());
                assertEquals(TestRequest.METHOD_POST, request.getMethod());

                Properties params = request.getParams();
                assertEquals(1, params.size());
                assertEquals("body", params.getProperty("foo"));

                /*
                Map<String, byte[]> files = request.getFiles();
                assertEquals(0, files.size());
*/

                return new TestResponse(TestResponse.HTTP_OK, TestResponse.MIME_TEXTPLAIN, "\n多\n字节\n\n进行反应的确认\n\n");
            }
        }, request, new ResponseHandler<String>() {
            public String handleResponse(HttpResponse httpResponse) throws IOException {
                assertEquals(200, httpResponse.getStatusLine().getStatusCode());
                assertEquals("OK", httpResponse.getStatusLine().getReasonPhrase());
                assertEquals("text/plain", httpResponse.getHeaders("Content-Type")[0].getValue());
                return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
        });
        assertEquals("\n多\n字节\n\n进行反应的确认\n\n", response);
    }
}

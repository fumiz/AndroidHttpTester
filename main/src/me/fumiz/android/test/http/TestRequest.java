package me.fumiz.android.test.http;

import java.util.Map;
import java.util.Properties;

/**
 * HTTP Request Container
 * User: fumiz
 * Date: 11/09/26
 * Time: 21:10
 */
public class TestRequest {
    /**
     * HTTP Method POST
     */
    public static final String METHOD_POST = "POST";
    /**
     * HTTP Method GET
     */
    public static final String METHOD_GET = "GET";
    /**
     * HTTP Method PUT
     */
    public static final String METHOD_PUT = "PUT";
    /**
     * HTTP Method DELETE
     */
    public static final String METHOD_DELETE = "DELETE";

    /**
     * path from Server Root
     * http://example.com/test/request/foo.cgi
     * -> /test/request/foo.cgi
     */
    private String mPath;
    /**
     * HTTP Method
     * POST | GET | PUT | DELETE
     */
    private String mMethod;
    /**
     * HTTP Headers
     */
    private Properties mHeaders;
    /**
     * HTTP Request Queries
     */
    private Properties mParams;
    /**
     * file binaries
     */
    private Map<String, byte[]> mFiles;

    /**
     * initialize with basic information
     * @param path request path from document root
     * @param method HTTP Method
     * @param headers HTTP Headers
     * @param params Request Parameters
     * @param files attached files
     */
    public TestRequest(String path, String method, Properties headers, Properties params, Map<String, byte[]> files) {
        mPath = path;
        mMethod = method;
        mHeaders = headers;
        mParams = params;
        mFiles = files;
    }

    /**
     * get path
     * @return request path
     */
    public String getPath() {
        return mPath;
    }

    /**
     * get HTTP Method
     * @return http method string
     */
    public String getMethod() {
        return mMethod;
    }

    /**
     * get HTTP Headers
     * @return Header Strings by Properties
     */
    public Properties getHeaders() {
        return mHeaders;
    }

    /**
     * get HTTP Params
     * @return Param Strings by Properties
     */
    public Properties getParams() {
        return mParams;
    }

    /**
     * get Files
     * @return File binaries by Dictionary
     */
    public Map<String, byte[]> getFiles() {
        return mFiles;
    }
}

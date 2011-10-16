package me.fumiz.test.android.test.http;

import android.test.AndroidTestCase;
import me.fumiz.android.test.http.TestRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: fumiz
 * Date: 11/10/16
 * Time: 16:21
 */
public class TestRequestTest extends AndroidTestCase {
    public void testGetPath() {
        assertEquals("/", new TestRequest("/",null,null,null,null).getPath());
        assertEquals("/foo/bar", new TestRequest("/foo/bar",null,null,null,null).getPath());
        assertEquals("/foo/bar?query=strings&sample=example", new TestRequest("/foo/bar?query=strings&sample=example",null,null,null,null).getPath());
    }

    public void testGetMethod() {
        assertEquals("GET", new TestRequest(null, TestRequest.METHOD_GET, null, null, null).getMethod());
        assertEquals("POST", new TestRequest(null, TestRequest.METHOD_POST, null, null, null).getMethod());
        assertEquals("DELETE", new TestRequest(null, TestRequest.METHOD_DELETE, null, null, null).getMethod());
        assertEquals("PUT", new TestRequest(null, TestRequest.METHOD_PUT, null, null, null).getMethod());
    }

    public void testGetHeaders() {
        Properties props = new Properties();
        props.setProperty("foo", "bar");
        props.setProperty("multibytevalue", "マルチバイト文字列");
        props.setProperty("多字节钥匙", "multibytekey");
        props.setProperty("多字节钥匙和多字节价值", "어떠한 값이 들어 있는 것 같다");
        props.setProperty("empty", "");

        Properties objProps = new TestRequest(null, null, props, null, null).getHeaders();
        assertEquals(5, objProps.size());
        assertEquals("bar", objProps.getProperty("foo"));
        assertEquals("マルチバイト文字列", objProps.getProperty("multibytevalue"));
        assertEquals("multibytekey", objProps.getProperty("多字节钥匙"));
        assertEquals("어떠한 값이 들어 있는 것 같다", objProps.getProperty("多字节钥匙和多字节价值"));
        assertEquals("", objProps.getProperty("empty"));
    }

    public void testGetParams() {
        Properties props = new Properties();
        props.setProperty("foo", "bar");
        props.setProperty("multibytevalue", "マルチバイト文字列");
        props.setProperty("多字节钥匙", "multibytekey");
        props.setProperty("多字节钥匙和多字节价值", "어떠한 값이 들어 있는 것 같다");
        props.setProperty("empty", "");

        Properties objProps = new TestRequest(null, null, null, props, null).getParams();
        assertEquals(5, objProps.size());
        assertEquals("bar", objProps.getProperty("foo"));
        assertEquals("マルチバイト文字列", objProps.getProperty("multibytevalue"));
        assertEquals("multibytekey", objProps.getProperty("多字节钥匙"));
        assertEquals("어떠한 값이 들어 있는 것 같다", objProps.getProperty("多字节钥匙和多字节价值"));
        assertEquals("", objProps.getProperty("empty"));
    }

    public void testGetFiles() {
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        map.put("file1", new byte[]{ 0x73, 0x74, 0x72, 0x75, 0x63, 0x74, 0x20, 0x4C, 0x4F, 0x47, 0x46, 0x4F, 0x4E, 0x54, 0x0D, 0x0A, 0x7B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20, 0x20, 0x20, 0x20, 0x6C, 0x66, 0x48, 0x65, 0x69, 0x67, 0x68, 0x74, 0x3B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20, 0x20, 0x20, 0x20, 0x6C, 0x66, 0x57, 0x69, 0x64, 0x74, 0x68, 0x3B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20 });
        map.put("多字节钥匙", new byte[]{ 0x1F, 0x50, 0x30, 0x4F, 0x20, 0x20, 0x1A, 0x3A, 0x69, 0x10, 0x62, 0x28, 0x08, 0x00, 0x2B, 0x30, 0x30, 0x3D, 0x29, 0x00, 0x2F, 0x46, 0x3A, 0x5C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x05, 0x3A });
        map.put("empty", new byte[]{ });

        Map<String, byte[]> objMap = new TestRequest(null, null, null, null, map).getFiles();
        assertTrue(Arrays.equals(new byte[]{ 0x73, 0x74, 0x72, 0x75, 0x63, 0x74, 0x20, 0x4C, 0x4F, 0x47, 0x46, 0x4F, 0x4E, 0x54, 0x0D, 0x0A, 0x7B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20, 0x20, 0x20, 0x20, 0x6C, 0x66, 0x48, 0x65, 0x69, 0x67, 0x68, 0x74, 0x3B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20, 0x20, 0x20, 0x20, 0x6C, 0x66, 0x57, 0x69, 0x64, 0x74, 0x68, 0x3B, 0x0D, 0x0A, 0x09, 0x4C, 0x4F, 0x4E, 0x47, 0x20 }, objMap.get("file1")));
        assertTrue(Arrays.equals(new byte[]{ 0x1F, 0x50, 0x30, 0x4F, 0x20, 0x20, 0x1A, 0x3A, 0x69, 0x10, 0x62, 0x28, 0x08, 0x00, 0x2B, 0x30, 0x30, 0x3D, 0x29, 0x00, 0x2F, 0x46, 0x3A, 0x5C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x05, 0x3A }, objMap.get("多字节钥匙")));
        assertTrue(Arrays.equals(new byte[]{ }, objMap.get("empty")));
    }
}

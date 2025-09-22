package com.hm.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NovelZSTDOnce {
    /**
     * 现在很多站点都是SSL对数据传输进行加密，这也让普通的HttpConnection无法正常的获取该页面的内容，
     * 而Jsoup起初也对此没有做出相应的处理，
     * 想了一下是否可以让Jsoup可以识别所有的SSL加密过的页面，查询了一些资料，发现可以为本地HttpsURLConnection配置一个“万能证书”，其原理是就是：
     * 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
     * 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
     * 后来Jsoup Connection提供了validateTLSCertificates(boolean validate)//是否进行TLS证书验证,不推荐
     */
//	static {
//		try {
//			// 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
//			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//				public boolean verify(String hostname, SSLSession session) {
//					return true;
//				}
//			});
//			// 创建随机证书生成工厂
//			//SSLContext context = SSLContext.getInstance("TLS");
//			SSLContext context = SSLContext.getInstance("TLSv1.2");
//                        context.init(null, new X509TrustManager[] { new X509TrustManager() {
//				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				}
// 
//				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				}
// 
//				public X509Certificate[] getAcceptedIssuers() {
//					return new X509Certificate[0];
//				}
//			} }, new SecureRandom());
// 
//			// 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
//			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
    public Document get(String uri) throws Exception {
        try {
            Thread.sleep(1000);
            // 5000是设置连接超时时间，单位ms
            // 5000是设置连接超时时间，单位ms
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
            connection.setRequestMethod("GET");
            //是否允许缓存，默认true。
            connection.setUseCaches(Boolean.FALSE);
            //是否开启输出输入，如果是post使用true。默认是false
            //connection.setDoOutput(Boolean.TRUE);
            //connection.setDoInput(Boolean.TRUE);
            //设置请求头信息
            connection.addRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            connection.addRequestProperty("accept-encoding", "gzip, deflate, br, zstd");
            connection.addRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            connection.addRequestProperty("cookie", "articlevisited=1");
            connection.addRequestProperty("priority", "u=0, i");
            connection.addRequestProperty("referer", "https://www.wukanshu.cc/books/1855619044/");
            connection.addRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"135\", \"Not-A.Brand\";v=\"8\", \"Chromium\";v=\"135\"");
            connection.addRequestProperty("sec-ch-ua-mobile", "?0");
            connection.addRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            connection.addRequestProperty("sec-fetch-dest", "document");
            connection.addRequestProperty("sec-fetch-mode", "navigate");
            connection.addRequestProperty("sec-fetch-site", "same-origin");
            connection.addRequestProperty("sec-fetch-user", "?1");
            connection.addRequestProperty("upgrade-insecure-requests", "1");
            connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36");

            //设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(90000);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(90000);
            //开始请求
            Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", uri);
            //TODO ---
            // return Jsoup.connect(uri).timeout(90000).get();
            return doc;
        } catch (Exception e) {
            System.out.println("---" + uri);
            System.out.println(e.getMessage());
            Thread.sleep(2000);
            return get(uri);
        }
    }

    public static byte[] hexStringToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    // 搜索
    @Test
    public void Demo1() throws Exception {

        NovelZSTDOnce t = new NovelZSTDOnce();
        Document doc = null;
        String index = "https://www.wukanshu.cc/book/27700/";
        String pre = "https://www.wukanshu.cc"; //搜索id
        doc = t.get(index);
        Elements novels = doc.select("#list");
        Elements novelsPages = novels.select("a");
        File novel = new File("F:\\temp\\幻想世界穿越法则 by虚无之刃.txt");
        boolean f = false;
        for (Element novelsPage : novelsPages) {
            String href = novelsPage.attr("href");
            String title = novelsPage.text();
            if (f) {
                if (title.contains("第2487章 数据世界")) {
                    f=false;
                } else {
                    continue;
                }
            }
            FileOutputStream otStream = new FileOutputStream(novel, true);
            otStream.write("\n\n".getBytes());
            otStream.write(title.getBytes());
            otStream.write("\n\n".getBytes());
            otStream.flush();
            otStream.close();
            System.out.println(title);
            download(t, pre, href, novel);
        }

        // 分页
        System.out.println("End");
    }

    private void download(NovelZSTDOnce t, String pre, String contentUrl, File novel) throws Exception {
        Document contentPage;
        contentPage = t.get(pre + contentUrl);
        String content = contentPage.select("#content").html();
        content = content.replaceAll("<p>(.*)</p>", "$1");

        FileOutputStream otStream = new FileOutputStream(novel, true);
        otStream.write("\n  ".getBytes());
        otStream.write(content.getBytes());
        otStream.write("\n  ".getBytes());
        otStream.flush();

        otStream.close();
        Thread.sleep(2000);
    }

}

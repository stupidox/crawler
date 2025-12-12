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

public class NovelZSTDList {
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
            connection.addRequestProperty("connection", "keep-alive");
            connection.addRequestProperty("content-length", "10388");
            connection.addRequestProperty("content-type", "text/html; charset=utf-8");
            connection.addRequestProperty("date", "Mon, 21 Apr 2025 11:46:31 GMT");
            connection.addRequestProperty("expires", "Mon, 21 Apr 2025 12:46:31 GMT");
            connection.addRequestProperty("server", "nginx");
            connection.addRequestProperty("set-cookie", "vv=1745235991; expires=Sun, 15 Feb 2026 11:46:31 GMT; Max-Age=25920000; path=/");
            connection.addRequestProperty("shuqi-expires", "3600");
            connection.addRequestProperty("strict-transport-security", "max-age=31536000");
            connection.addRequestProperty("vary", "Accept-Encoding");
            connection.addRequestProperty("x-frame-options", "DENY");
            connection.addRequestProperty("x-powered-by", "com.zhuishushenqi.free2023");
            connection.addRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            connection.addRequestProperty("accept-encoding", "gzip, deflate, br, zstd");
            connection.addRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            connection.addRequestProperty("connection", "keep-alive");
            connection.addRequestProperty("cookie", "user_sex=3128; vv=1745235250; novel_128264=5445817%7C1745235250; qd_vt=1745235250");
            connection.addRequestProperty("host", "m.175zw.org");
            connection.addRequestProperty("referer", "https://m.175zw.org/128264/4/");
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

    // 搜索
    @Test
    public void Demo1() throws Exception {

        NovelZSTDList t = new NovelZSTDList();
        Document doc = null;
        String index = "https://www.axxsw.net/index/162/162468/";
        String pre = "https://www.axxsw.net"; //搜索id
        File novel = new File("F:\\temp\\幻想世界穿越法则2 by虚无之刃.txt");
        boolean f = false;
        for (int i = 1; i < 31; i++) {
            doc = t.get(index + i + "/");
            Elements novels = doc.select(".read");
            Elements novelsPages = novels.select("li").select("a");
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
                otStream.write("\n".getBytes());
                otStream.write(title.getBytes());
                otStream.write("\n".getBytes());
                otStream.flush();
                otStream.close();
                download(t, pre, href, novel, 1);
            }
        }

        // 分页
        System.out.println("End");
    }

    private void download(NovelZSTDList t, String pre, String contentUrl, File novel, int next) throws Exception {
        Document contentPage;
        int idx = contentUrl.lastIndexOf("/");
        int ht = contentUrl.lastIndexOf(".");
        contentPage = t.get(pre + contentUrl.substring(0, idx + 1) + next + contentUrl.substring(ht));
        String title = contentPage.select(".headline").text();

        System.out.println(title);
        String content = contentPage.select(".content").html();
        content = content.replaceAll("<br>\\s*<br>", "\n").replaceAll("<br>", "\n").replaceAll("<p></p>", "\n")
                .replaceAll("<p>(.*)</p>", "$1").replaceAll("<a[^>]*>.*</a>", "");

        FileOutputStream otStream = new FileOutputStream(novel, true);
        // 分页
        int i1 = title.lastIndexOf("(");
        int i2 = title.lastIndexOf("/");
        int i3 = title.lastIndexOf(")");
        if (i1 > 0 && i2 > 0) {
            int a = Integer.parseInt(title.substring(i1 + 1, i2));
            int b = Integer.parseInt(title.substring(i2 + 1, i3));
            otStream.write(content.getBytes());
            if (a >= b) {
                otStream.write("\n  ".getBytes());
            }
            otStream.flush();

            otStream.close();
            Thread.sleep(2000);
            if (a < b) {
                download(t, pre, contentUrl, novel, ++next);
            }
        } else {
            otStream.write("\n  ".getBytes());
            otStream.write(content.getBytes());
            otStream.write("\n  ".getBytes());
            otStream.flush();

            otStream.close();
            Thread.sleep(2000);
        }
    }

}

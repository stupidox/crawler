package com.hm.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class NovelGZIPOnce {
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
            connection.addRequestProperty("accept-encoding", "gzip, deflate");
            connection.addRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            connection.addRequestProperty("cache-control", "max-age=0");
            connection.addRequestProperty("connection", "keep-alive");
            connection.addRequestProperty("cookie", "bookclick=45741%7C");
            connection.addRequestProperty("host", "www.xxdzs3.com");
            connection.addRequestProperty("referer", "http://www.xxdzs3.com/e-book_download/45741.html");
            connection.addRequestProperty("upgrade-insecure-requests", "1");
            connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");

            //设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(90000);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(90000);
            //开始请求
            Document doc = Jsoup.parse(new GZIPInputStream(connection.getInputStream()), "UTF-8", uri);
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
        NovelGZIPOnce t = new NovelGZIPOnce();
        Document doc;
        boolean f = false;
        String index;
        index = "http://www.llskw.org/shu/146051_index.html";
        String pre = "http://www.llskw.org";
        doc = t.get(index);
        Elements novels = doc.select(".m-intro").get(1).select(".showInfo");
        Elements novelsPages = novels.select("li").select("a");
        File novel = new File("F:\\temp\\奥特杂兵？在外叫我宇宙警察.txt");
        for (Element novelsPage : novelsPages) {
            String href = novelsPage.attr("href");
            String title = novelsPage.text();
            if (f) {
                if (title.contains("第422章")) {
                    f=false;
                } else {
                    continue;
                }
            }

            download(t, pre, href, title, novel);
        }

        // 分页
        System.out.println("End");
    }

    private void download(NovelGZIPOnce t, String pre, String contentUrl, String title, File novel) throws Exception {
        Document page;
        page = t.get(pre + contentUrl);
        Elements s1 = page.select("#content");
        String content = s1.html();
        content = content.replaceAll("<div[^莎]*</div>", "").replaceAll("<br>", "\n").replaceAll("\n \n", "\n").replaceAll("&nbsp;", " ");
        System.out.println(title);
        FileOutputStream otStream = new FileOutputStream(novel, true);
        otStream.write("\n\n".getBytes());
        otStream.write((title).getBytes());
        otStream.write("\n\n".getBytes());
        otStream.write(content.getBytes());
        otStream.write("\n".getBytes());
        otStream.flush();
        otStream.close();
        Thread.sleep(1000);
    }

}

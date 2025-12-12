package com.hm.demo.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Novel {
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


    public Document get(String url) throws Exception {
        try {
            Thread.sleep(1000);
            // 5000是设置连接超时时间，单位ms
            Connection connect = Jsoup.connect(url);
            return connect
                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("accept-encoding", "gzip, deflate, br")
                    .header("accept-language", "zh-CN,zh;q=0.9")
                    .header("cache-control", "max-age=0")
                    .header("cookie", "PHPSESSID=85q52i0cbcngjk2md01ftdrlto")
                    .header("referer", "https://m.ttshu66.com/1_1533/")
                    .header("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"")
                    .header("sec-ch-ua-mobile", "?0")
                    .header("sec-ch-ua-platform", "\"Windows\"")
                    .header("sec-fetch-dest", "document")
                    .header("sec-fetch-mode", "navigate")
                    .header("sec-fetch-site", "same-origin")
                    .header("sec-fetch-user", "?1")
                    .header("upgrade-insecure-requests", "1")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                    .timeout(90000)
                    .maxBodySize(Integer.MAX_VALUE)//设置文档长度
                    .get();
            //编码处理
            //return Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (Exception e) {
            System.out.println("---" + url);
            System.out.println(e.getMessage());
            if (e.getMessage().contains("recv failed")) {
                Thread.sleep(10000);
                return get(url);
            } else if (e.getMessage().contains("timed out")) {
                Thread.sleep(10000);
                return get(url);
            }
        }
        return null;
    }

    // 搜索
    @Test
    public void Demo1() throws Exception {

        Novel t = new Novel();
        Document doc = null;
        String index = "https://www.feibzw.com/Html/34685/index.html";
        String indexa = "https://www.feibzw.com/Html/34685/"; //搜索id
        doc = t.get(index);
        Elements novels = doc.select(".chapterlist");
        Elements novelsPages = novels.select("li");
        File novel = new File("F:\\temp\\漫威：王者降临.txt");
        int i = 0;
        for (Element novelsPage : novelsPages) {
            if (i < 260) {
                ++i;
                continue;
            }
            String href = novelsPage.select("a").attr("href");
            download(t, indexa, href, novel);
        }

        // 分页
        System.out.println("End");
    }

    private void download(Novel t, String indexa, String contentUrl, File novel) throws Exception {
        Document contentPage;
        contentPage = t.get(indexa + contentUrl);
        String title = contentPage.select(".chaptertitle").text();
        int pot = title.indexOf("（");
        if (pot > 0) {
            title = title.substring(0, pot);
        }
        String content = contentPage.select("#content").toString().replaceAll("<div[^>]*>", "")
                .replaceAll("</[^>]*>", "").replaceAll("&nbsp;", " ").replaceAll("<p>", "")
                .replaceAll("<p .*","").replaceAll("<ins>", "").trim();

        FileOutputStream otStream = new FileOutputStream(novel, true);
        otStream.write(title.getBytes());
        otStream.write("\n\n  ".getBytes());
        otStream.write(content.getBytes());
        otStream.write("\n  ".getBytes());
        otStream.flush();

        otStream.close();
        Thread.sleep(2000);
    }

}

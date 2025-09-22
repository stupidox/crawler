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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelZSTDNextPage {
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

        NovelZSTDNextPage t = new NovelZSTDNextPage();
        Document doc = null;
        String index = "https://www.zhuoshuge.com/book/29069/7342665.html";
        String pre = "https://www.zhuoshuge.com"; //搜索id
        File novel = new File("F:\\temp\\反派：我的母亲是大帝-乐福不受.txt");
        download(t, pre, "/book/29069/7342665.html", "", novel);

        // 分页
        System.out.println("End");
    }

    private void download(NovelZSTDNextPage t, String pre, String contentUrl, String lastTitle, File novel) throws Exception {
        Document page;
        page = t.get(pre + contentUrl);
        Elements contentPage = page.select("#novelbody");
        if (!contentPage.isEmpty()) {
            String nextContentUrl = contentPage.select("#next1").attr("data");
            String title = contentPage.select("#chaptername").text();
            System.out.println(title);
            if (title.contains("-《")) {
                title = title.substring(0, title.indexOf("-《"));
            } else {
                System.err.println(title);
            }
            FileOutputStream otStream = new FileOutputStream(novel, true);
            if (!title.equals(lastTitle)) {
                otStream.write("\n\n  ".getBytes());
                otStream.write(title.getBytes());
                otStream.write("\n\n  ".getBytes());
                otStream.flush();
            }

            String content = contentPage.select("#novelcontent").html();
            Pattern pattern = Pattern.compile("qsbs\\.bb\\('.*'\\)");
            Matcher matcher = pattern.matcher(content);
            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                String c = matcher.group();
                try {
                    sb.append(new String(Base64.getDecoder().decode(c.substring(c.indexOf("'")+1, c.lastIndexOf("'"))), StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            content = sb.toString();
            content = content.replaceAll("<style>.*</style>", "").replaceAll("<p .*</p>", "").replaceAll("<p>([^<]*)</p>", "$1\n");
            otStream.write(content.getBytes());
            otStream.flush();
            otStream.close();
            Thread.sleep(2000);
            download(t, pre, nextContentUrl, title, novel);
        } else {
            contentPage = page.select(".word_read");
            String nextContentUrl = contentPage.select(".read_btn").get(0).select("a").get(3).attr("href");
            String title = contentPage.select("h3").text();
            String[] ts = title.split(" ");
            if (ts.length > 1) {
                title = ts[0] + " " + ts[1].substring(0, ts[1].indexOf("（"));
            } else {
                title = ts[0];
            }
            System.out.println(title);
            FileOutputStream otStream = new FileOutputStream(novel, true);
            if (!title.equals(lastTitle)) {
                otStream.write("\n\n  ".getBytes());
                otStream.write(title.getBytes());
                otStream.write("\n  ".getBytes());
                otStream.flush();
            }

            String content = contentPage.html();
            content = content.replaceAll("<h3>.*</h3>", "").replaceAll("<div.*</div>", "").replaceAll("<p>(.*)</p>", "$1");
            otStream.write(content.getBytes());
            otStream.flush();
            otStream.close();
            Thread.sleep(2000);
            download(t, pre, nextContentUrl, title, novel);
        }
    }

}

package com.hm.demo.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class Novel4 {
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
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("Accept-Encoding","gzip, deflate, br, zstd")
                    .header("Accept-Language","zh-CN,zh;q=0.9")
                    .header("Cache-Control","max-age=0")
                    .header("Cookie","lg=cn; PbootSystem=2v1t8vvpgoq944fsqpdpmejfvl; v44625=1; Readed=44625-21750662")
                    .header("Priority","u=0, i")
                    .header("Referer","https://cn.bing.com/")
                    .header("Sec-Ch-Ua","\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
                    .header("Sec-Ch-Ua-Mobile","?0")
                    .header("Sec-Ch-Ua-Platform","\"Windows\"")
                    .header("Sec-Fetch-Dest","document")
                    .header("Sec-Fetch-Mode","navigate")
                    .header("Sec-Fetch-Site","cross-site")
                    .header("Sec-Fetch-User","?1")
                    .header("Upgrade-Insecure-Requests","1")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
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

        Novel4 t = new Novel4();
        Document doc = null;
        String index = "https://www.yunjingxs.com/emzny/";
        String indexa = "https://www.yunjingxs.com/"; //搜索id
        doc = t.get(index);
        Elements novels = doc.select(".chapter_list");
        Elements novelsPages = novels.select("a");
        File novel = new File("F:\\temp\\恶魔在纽约.txt");
        int i = 300;
        for (Element novelsPage : novelsPages) {
            if (i < 260) {
                ++i;
                continue;
            }
            String href = novelsPage.attr("href");
            download(t, indexa, href, novel);
        }

        // 分页
        System.out.println("End");
    }

    private void download(Novel4 t, String indexa, String contentUrl, File novel) throws Exception {
        Document contentPage;
        contentPage = t.get(indexa + contentUrl);
        String title = contentPage.select(".style_h1").text();
        System.out.println(title);
        String content = contentPage.select("#bodybox").toString().replaceAll("<div[^>]*>", "")
                .replaceAll("</[^>]*>", "").replaceAll("&nbsp;", " ").replaceAll("<p>", "")
                .replaceAll("<p .*>","").replaceAll("<ins>", "").trim();

        FileOutputStream otStream = new FileOutputStream(novel, true);
        otStream.write(title.getBytes());
        otStream.write("\n\n  ".getBytes());
        otStream.write(content.getBytes());
        otStream.write("\n\n  ".getBytes());
        otStream.flush();

        otStream.close();
        Thread.sleep(2000);
    }

}

package com.hm.demo.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Pictures {

    static boolean append = false;

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
    /**
     *
     * @param url
     *            访问路径
     * @return
     * @throws Exception
     */
    public Document getDocument(String url) throws Exception {
        try {
            Thread.sleep(1000);
            // 5000是设置连接超时时间，单位ms
            Connection connect = Jsoup.connect(url)	;
            return connect.timeout(5000)

                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("accept-encoding", "gzip, deflate, br")
                    .header("accept-language", "zh-CN,zh;q=0.9")
                    .header("cache-control", "no-cache")
                    .header("cookie", "UM_distinctid=176be913a4f1a5-0b9946b4e42fc6-5c19341b-100200-176be913a50289; CNZZDATA1259034672=212605359-1609514070-%7C1610967761; jzzomlastsearchtime=1610968117; BD_UPN=1088")
                    .header("pragma", "no-cache")
                    .header("referer", "https://www.656g.com/")
                    .header("sec-ch-ua", "\"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"")
                    .header("sec-ch-ua-mobile", "?0")
                    .header("sec-fetch-dest", "document")
                    .header("sec-fetch-mode", "navigate")
                    .header("sec-fetch-site", "same-origin")
                    .header("sec-fetch-user", "?1")
                    .header("upgrade-insecure-requests", "1")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36")
                    .header("Authorization", "Basic 123456789123456789")
                    .timeout(90000)
                    .maxBodySize(Integer.MAX_VALUE)//设置文档长度
                    .get();
            //编码处理
            //return Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (Exception e) {
            System.out.println("---"+url);
            System.out.println(e.getMessage());
            if (e.getMessage().contains("recv failed")) {
                Thread.sleep(10000);
                return getDocument(url);
            }
            else if (e.getMessage().contains("timed out")) {
                Thread.sleep(10000);
                return getDocument(url);
            }
        }
        return null;
    }

    @Test
    public void Demo0() throws Exception {
        Demo1("D:\\private\\pictures\\best\\范冰冰\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\古力娜扎\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\迪丽热巴\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\陈都灵\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\陈瑶\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\程潇\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\高圆圆\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\景甜\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\林志玲\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\Lisa\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\刘诗诗\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\刘亦菲\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\柳智敏\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\申有娜\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\唐嫣\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\佟丽娅\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\杨幂\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\杨颖\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\张靓颖\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\张元英\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\张予曦\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\赵今麦\\000", true,
                "");
        Demo1("D:\\private\\pictures\\best\\朱珠\\000", true,
                "");
        Demo1("D:\\private\\pictures\\0wa33", false,
                "");
    }

    // 微信图片
    public void Demo1(String path, boolean addNew, String... uris) throws Exception{
        for (String uri : uris) {
            if (StringUtils.isEmpty(uri.trim())) {
                return;
            }
            Pictures t = new Pictures();
            Document doc = null;
            FileOutputStream fos = null;
            doc = t.getDocument(uri);
            Elements e0 = doc.select("#js_content");
            Elements e1 = null;
            HashSet<String> urLSet = new LinkedHashSet<>(); // 自动去重

            // 常规页面模式
            e1 = e0.select("img");
            for (Element element : e1) {
                urLSet.add(element.attr("data-src"));
            }

            if (urLSet.isEmpty()) {
                // 微信左右滑动图片模式
                Pattern cdnUrlPattern = Pattern.compile(
                        "cdn_url\\s*[:=]\\s*(['\"]+)(https[^'\"\\s,;}]+)\\1",
                        Pattern.CASE_INSENSITIVE // 忽略大小写，匹配CDN_URL、Cdn_Url等
                );

                // 4. 匹配并提取非空的cdn_url值
                Matcher matcher = cdnUrlPattern.matcher(doc.html());
                while (matcher.find()) {
                    // group(2)是匹配到的cdn_url值（group(1)是单/双引号，忽略）
                    String cdnUrl = matcher.group(2).trim();
                    // 过滤空值（正则已限制非空，这里做双重保险）
                    if (!cdnUrl.isEmpty()) {
                        urLSet.add(cdnUrl);
                    }
                }
            }

            // 计算出最后一个文件夹名
            String fileName;
            File parentFile = new File(path);
            File[] files = parentFile.listFiles();
            List<File> newFiles = Arrays.asList(files).stream().sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
            if (addNew) {
                fileName = String.format("%03d", Integer.parseInt(newFiles.get(newFiles.size() - 1).getName()) + 1);
            } else {
                fileName = newFiles.get(newFiles.size() - 1).getName();
            }

            File dir = new File(path, fileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            int j = 1;
            for (String imgUrl : urLSet) {
                try {
                    while (new File(dir,  String.format("%03d", j) + ".jpg").exists() ||
                            new File(dir,  String.format("%03d", j) + ".png").exists()) {
                        ++j;
                    }
                    File file = new File(dir,  String.format("%03d", j) + ".jpg");
                    // 下载网络文件
                    int byteread = 0;
                    URL url;
                    url = new URL(imgUrl);
                    URLConnection conn = url.openConnection();
                    InputStream inStream = conn.getInputStream();
                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[120400];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        // bytesum += byteread;
                        fos.write(buffer, 0, byteread);
                    }
                    // System.out.println(bytesum);
                    j++;
                    Thread.sleep(100);
                } catch (FileNotFoundException e) {
                    System.err.println("File not Found");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        fos.close();
                        // Thread.sleep(100);
                    }

                }
            }
            WindowsUtils.openDir(dir);
            // 用到的目录路径写入文件
            if (!append) {
                WindowsUtils.writeToFile(dir, "D:\\private\\dirs.txt", false);
                append = true;
            } else {
                WindowsUtils.writeToFile(dir, "D:\\private\\dirs.txt", true);
            }
            System.out.println("End");
        }
    }
}

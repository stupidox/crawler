package com.hm.demo.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Pictures {
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

    // 搜索
    public void Demo3() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        ArrayList<String> list = new ArrayList<String>();
        int a = 0; //列表第几页
        String indexa = "https://www.656g.com/e/search/result/index.php?page=";
        String name = "林允"; //文件名
        String indexb = "&searchid=1978"; //搜索id
        int aa = 1; //文件夹编号
        doc = t.getDocument(indexa + a + indexb);
        Elements ef3 = doc.select(".page");
        Elements ef4 = ef3.select("a");
        Iterator<Element> it1 = ef4.iterator();
        List<String> pages = new ArrayList<String>();
        pages.add(indexa + a + indexb);
        while(it1.hasNext()) {
            Element href = it1.next();
            Integer pageIdx = null;
            try{
                pageIdx = Integer.valueOf(href.text());
            } catch(Exception e) {
                continue;
            }
            if (pageIdx > 1) {
                pages.add(indexa + (pageIdx - 1) + indexb);
            }
        }

        // 分页
        for (int k = 0; k < pages.size(); k++) {
            doc = t.getDocument(pages.get(k));
            Elements ef1 = doc.select(".l-pub");
            Elements ef2 = ef1.select("li");
            Iterator<Element> it = ef2.iterator();
            list.clear();
            while (it.hasNext()) {
                Element ee = it.next();
                Elements ee1 = ee.select("a");
                String path = ee1.attr("href");
                int idx = path.lastIndexOf(".");
                list.add("https://www.656g.com" + path.substring(0, idx));
            }
            String u = null;
            // 分页内，第几个页面
            for (int j = 0; j < list.size(); j++) {
                String s1 = list.get(j);
                String s2 = ".html";
                int len = 99999;
                boolean f = true;
                //			int bytesum = 0;
                String ss = "D:\\private\\pictures\\beauty\\" + name + "\\" + String.format("%03d", aa) + "\\";
                File dir = new File(ss);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                for (int i = 1; i < len; i++) {
                    File file = new File(ss + String.format("%03d", i) + ".jpg");
                    if (len != 99999 && file.exists()) {
                        continue;
                    }
                    // 下载网络文件
                    int byteread = 0;

                    URL url;
                    if (i > 1) {
                        u = s1 + "_" + i + s2;
                    } else {
                        u = s1 + s2;
                    }
                    doc = t.getDocument(u);

                    FileOutputStream fos = null;
                    try {

                        if (f) {
                            Elements a1 = doc.select(".pic");
                            Elements a2 = a1.select(".w1200");
                            Elements a3 = a2.select("h1");
                            String ttt = a3.text();
                            int ii1 = ttt.lastIndexOf("/");
                            int ii2 = ttt.lastIndexOf(")");
                            len = Integer.parseInt(ttt.substring(ii1 + 1, ii2)) + 1;
                            f = false;
                        }
                        if (len == 99999 && !dir.exists()) {
                            dir.mkdirs();
                        }
                        if (len != 99999 && file.exists()) {
                            continue;
                        }
                        Elements e1 = doc.select(".pic-main");
                        Elements e2 = e1.select("img");
                        String imgURL = e2.attr("src");
                        url = new URL(imgURL);
                        URLConnection conn = url.openConnection();
                        InputStream inStream = conn.getInputStream();

                        fos = new FileOutputStream(file);

                        byte[] buffer = new byte[1204 * 1024];
                        while ((byteread = inStream.read(buffer)) != -1) {
                            //						bytesum += byteread;
                            Thread.sleep(1);
                            fos.write(buffer, 0, byteread);
                        }
                        //					System.out.println(bytesum);
                    } catch (FileNotFoundException e) {
                        continue;
                    } catch (IOException e) {
                        System.out.println(u);
                        System.out.println(e.getMessage());
                    } finally {
                        if (fos != null) {
                            fos.close();
                            //					Thread.sleep(100);
                        }
                    }

                }
                ++aa;
            }
        }
        System.out.println("End");
    }

    // 唯美
    public void Demo4() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        List<String> list = new ArrayList<>();
        int a = 1; //列表第几页
        String indexa = "https://www.656g.com/weimei/nvsheng/index";
        String prefix = "https://www.656g.com";
        String suffix = ".html";
        List<Page> pages = new ArrayList<>();

        while(true) {
            System.out.println("---------------" + a);
            pages.clear();
            if (a == 1) {
                doc = t.getDocument(indexa + suffix);
            } else {
                doc = t.getDocument(indexa + "_" + a + suffix);
            }
            Elements ef3 = doc.select(".m-list");
            Elements ef4 = ef3.select("a");
            Iterator<Element> it1 = ef4.iterator();

            while (it1.hasNext()) {
                Element href = it1.next();
                Page page = new Page();
                page.setUrl(prefix + href.attr("href"));
                page.setTitle(href.attr("title"));
                pages.add(page);
            }

            // 分页
            for (int k = 0; k < pages.size(); k++) {
                int aa = 1; //文件夹编号
                Page page = pages.get(k);
                int len = 1<<7;
                for (int i = 1; i <= len; i++) {
                    String imgPath = page.getUrl();
                    if (i > 1) {
                        int idx = imgPath.lastIndexOf(".");
                        imgPath = imgPath.substring(0, idx) + "_" + i + imgPath.substring(idx);
                    }
                    doc = t.getDocument(imgPath);

                    Elements ef1 = doc.select(".pic");
                    Elements ef2 = ef1.select(".w1200");
                    if (len == 1<<7) {
                        Elements ef5 = ef2.select("h1");
                        String text = ef5.text();
                        int idx1 = text.lastIndexOf("/");
                        int idx2 = text.lastIndexOf(")");
                        len = Integer.parseInt(text.substring(idx1+1, idx2));
                    }
                    Elements ef8 = ef2.select(".pic-main");
                    Elements ef7 = ef8.select("img");
                    Iterator<Element> it = ef7.iterator();
                    list.clear();
                    while (it.hasNext()) {
                        Element ee = it.next();
                        list.add(ee.attr("src"));
                    }
                    String u = null;
                    // 详情页
                    String title = page.getTitle().replace("?", "？").replace(":", "：").replace("*", "8").
                            replace("/", "、").replace("\\", "、").replace("|", "丨").
                            replace("<", "《").replace(">", "》").replace("\"", "“");
                    if (title.contains("乌克兰模特")) {
                        continue;
                    }
                    String ss = "D:\\private\\pictures\\weimei\\" + title + "\\" + String.format("%03d", aa);
                    File dir = new File(ss);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    int aaa = 1; //文件编号
                    for (int j = 0; j < list.size(); j++) {
                        u = list.get(j);
                        //			int bytesum = 0;
                        File file = new File(ss + "\\" + String.format("%03d", aaa) + u.substring(u.lastIndexOf(".")));
                        if (file.exists()) {
                            continue;
                        }
                        // 下载网络文件
                        int byteread = 0;
                        FileOutputStream fos = null;
                        try {
                            URL url = new URL(u);
                            URLConnection conn = url.openConnection();
                            InputStream inStream = conn.getInputStream();

                            fos = new FileOutputStream(file);

                            byte[] buffer = new byte[1204 * 1024];
                            while ((byteread = inStream.read(buffer)) != -1) {
                                //						bytesum += byteread;
                                Thread.sleep(1);
                                fos.write(buffer, 0, byteread);
                            }
                            //					System.out.println(bytesum);
                        } catch (FileNotFoundException e) {
                            continue;
                        } catch (IOException e) {
                            System.out.println(u);
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println(u);
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                fos.close();
                                //					Thread.sleep(100);
                            }
                        }
                        aaa++;
                    }
                    ++aa;
                }
            }
            a++;
            System.out.println("End");
        }
    }

    // 韩漫
    @Test
    public void Demo8() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        List<String> list = new ArrayList<>();
        String path = "D:\\private\\pictures\\comic02\\韩漫";
        String name = "姐姐富家女（富家女姐姐）";
        int a = 1; //列表第几页
        String indexa = "http://www.artouredu.com/json/chapter.php?ph=0&tempid=3&zpid=247&page=0&line=48&orderby=asc";
        URL u1 = new URL(indexa);
        HttpURLConnection conn = (HttpURLConnection)u1.openConnection();
        conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cookie", "DedeStUUID=1bfc43fea7889; DedeStUUID__ckMd5=c0be18f8fd0a5e02; PHPSESSID=br23akrmb6j48qk9eo21vli1q7");
        conn.setRequestProperty("Host", "www.artouredu.com");
        conn.setRequestProperty("Pragma", "no-cache");
        conn.setRequestProperty("Referer", "http://www.artouredu.com/comic/247");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1204 * 1024];
        int byteread = 1;
        while ((byteread = inStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, byteread));
        }
        conn.disconnect();
        String s = sb.toString();
        Hanman haman = JSON.parseObject(s, Hanman.class);
        List<PicPath> length = haman.getLength();
        String prefix = "https://www.artouredu.com/json/info.php?id=";
        String pre = "https://www.artouredu.com";
        String suffix = "&type=1";
        for (PicPath p : length) {
            String url = p.getUrl();
            int c = url.lastIndexOf("/");
            int b = url.lastIndexOf(".");
            String ss = url.substring(c+1, b);
            String u = prefix + ss + suffix;
            URL u2 = new URL(u);
            HttpURLConnection cc = (HttpURLConnection)u2.openConnection();
            cc.setRequestProperty("Accept", "*/*");
            cc.setRequestProperty("Accept-Encoding", "gzip, deflate");
            cc.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            cc.setRequestProperty("Connection", "keep-alive");
            cc.setRequestProperty("Cookie", "DedeStUUID=1bfc43fea7889; DedeStUUID__ckMd5=c0be18f8fd0a5e02; PHPSESSID=br23akrmb6j48qk9eo21vli1q7");
            cc.setRequestProperty("Host", "www.artouredu.com");
            cc.setRequestProperty("Referer", pre + url);
            cc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
            cc.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            cc.setRequestMethod("GET");
            InputStream inputStream = cc.getInputStream();
            sb.setLength(0);
            while ((byteread = inputStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, byteread));
            }
            cc.disconnect();
            String s1 = sb.toString();
            PicObj o1 = JSON.parseObject(s1, PicObj.class);
            List<PicDT> l1 = o1.getData().get(0).getList();
            l1.sort(Comparator.comparing(PicDT::getNum));
            File dir = new File(path, name);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            int f = 1;
            File d1 = new File(dir, String.format("%03d", a));
            if (!d1.exists()) {
                d1.mkdirs();
            }

            for (PicDT dt : l1) {
                File file = new File(d1, String.format("%03d", f) + ".jpg");
                if (file.exists()) {
                    ++f;
                    continue;
                }
                URL pp = new URL(dt.getImg());
                HttpURLConnection sc = (HttpURLConnection)pp.openConnection();
                sc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
                sc.setRequestProperty("Accept-Encoding", "gzip, deflate");
                sc.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                sc.setRequestProperty("Cache-Control", "no-cache");
                sc.setRequestProperty("Connection", "keep-alive");
                sc.setRequestProperty("Host", "pic.tmsmh.com");
                sc.setRequestProperty("Pragma", "no-cache");
                sc.setRequestProperty("Referer", pre + url);
                sc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
                sc.setRequestProperty("Upgrade-Insecure-Requests", "1");
                sc.setConnectTimeout(10000);
                sc.setReadTimeout(10000);
                sc.setRequestMethod("GET");
                InputStream is = sc.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer1 = new byte[1204*1024];
                while ((byteread = is.read(buffer1)) != -1) {
                    fos.write(buffer, 0, byteread);
                }
                fos.flush();
                fos.close();
                is.close();
                ++f;
                Thread.sleep(100);
            }
            ++a;
        }
        // 分页
    }


    public void Demo1() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        ArrayList<String> list = new ArrayList<String>();
        doc = t.getDocument("https://www.656g.com/e/search/result/?searchid=1205");
        Elements ef1 = doc.select(".l-pub");
        Elements ef2 = ef1.select("li");
        Iterator<Element> it = ef2.iterator();
        while (it.hasNext()) {
            Element ee = it.next();
            Elements ee1 = ee.select("a");
            String path = ee1.attr("href");
            int idx = path.lastIndexOf(".");
            list.add("https://www.656g.com" + path.substring(0, idx));
        }



        int aa = 1;
        String u = null;
        for (int j = 0; j < list.size(); j++) {
            String s1 = list.get(j);
            String s2 = ".html";
            int len = 999;
            boolean f = true;
//			int bytesum = 0;
            String ss = "D:\\private\\pictures\\beauty\\米线线\\" + String.format("%03d", aa) + "\\";
            File dir = new File(ss);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            for (int i = 1; i < len; i++) {
                File file = new File(ss + String.format("%03d", i) + ".jpg");
                if (len != 999 && file.exists()) {
                    continue;
                }
                // 下载网络文件
                int byteread = 0;

                URL url;
                if (i > 1) {
                    u = s1 + "_" + i + s2;
                } else {
                    u = s1 + s2;
                }
                doc = t.getDocument(u);

                FileOutputStream fos = null;
                try {

                    if (f) {
                        Elements a1 = doc.select(".pic");
                        Elements a2 = a1.select(".w1200");
                        Elements a3	 = a2.select("h1");
                        String ttt = a3.text();
                        int ii1 = ttt.lastIndexOf("/");
                        int ii2 = ttt.lastIndexOf(")");
                        len = Integer.parseInt(ttt.substring(ii1+1, ii2))+1;
                        f = false;
                    }
                    if (len == 999 && file.exists()) {
                        continue;
                    }
                    Elements e1 = doc.select(".pic-main");
                    Elements e2 = e1.select("img");
                    String imgURL = e2.attr("src");
                    url = new URL(imgURL);
                    URLConnection conn = url.openConnection();
                    InputStream inStream = conn.getInputStream();

                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[1204*1024];
                    while ((byteread = inStream.read(buffer)) != -1) {
//						bytesum += byteread;
                        fos.write(buffer, 0, byteread);
                    }
//					System.out.println(bytesum);
                } catch (FileNotFoundException e) {
                    continue;
                } catch (IOException e) {
                    System.out.println(u);
                    System.out.println(e.getMessage());
                } finally {
                    if (fos != null) {
                        fos.close();
                        //					Thread.sleep(100);
                    }
                }

            }
            ++aa;
        }
        System.out.println("End");
    }

    // 一页一张
    public void Demo2() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        int aa = 4;
        String ss = "D:\\private\\pictures\\coser\\\\铁板烧鬼舞\\" + String.format("%03d", aa) + "\\";
        File dir = new File(ss);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String s1 = "https://www.656g.com/meinv/xinggan/28244";
        String s2 = ".html";
        for (int i = 1; i < 999; i++) {
            File file = new File(ss + String.format("%03d", i) + ".jpg");
            if (file.exists()) {
                continue;
            }
            // 下载网络文件
            int byteread = 0;
            URL url;
            if (i > 1) {
                doc = t.getDocument(s1 + "_" + i + s2);
            } else {
                doc = t.getDocument(s1 + s2);
            }
            FileOutputStream fos = null;
            try {
                Elements e1 = doc.select(".pic-main");
                Elements e2 = e1.select("img");
                String imgURL = e2.attr("src");
                url = new URL(imgURL);
                URLConnection conn = url.openConnection();
                InputStream inStream = conn.getInputStream();
                fos = new FileOutputStream(file);

                byte[] buffer = new byte[120400];
                while ((byteread = inStream.read(buffer)) != -1) {
//						bytesum += byteread;
                    fos.write(buffer, 0, byteread);
                }
//					System.out.println(bytesum);
            } catch (FileNotFoundException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                    //					Thread.sleep(100);
                }
            }

        }
        System.out.println("End");
    }

    // 一页多张
    @Test
    public void Demo5() throws Exception{
        Pictures t = new Pictures();
        Document doc1 = null;
        int aa = 7; //文件夹
        String ss = "D:\\private\\pictures\\coser\\一小央泽\\" + String.format("%03d", aa) + "\\";
        File dir = new File(ss);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int n = 2;
        boolean f = true;
        int i = 1;
        for (int j=1; j < n; j++) {
            String s1 = "https://www.656g.com/weimei/nvsheng/51643";
            String s2 = ".html";
            FileOutputStream fos = null;
            if (j > 1) {
                doc1 = t.getDocument(s1 + "_" + j + s2);
            } else {
                doc1 = t.getDocument(s1 + s2);
            }
            if (f) {
                f = false;
                Elements e6 = doc1.select(".pic");
                Elements e7 = e6.select(".w1200");
                Elements e8 = e7.select("h1");
                String text = e8.text();
                int idx = text.indexOf("/");
                if (idx != -1) {
                    n = Integer.parseInt(text.substring(idx+1, idx+2)) + 1;
                }
            }
            Elements e1 = doc1.select(".pic-main");
            Elements e2 = e1.select("img");
            Iterator<Element> it = e2.iterator();
            while (it.hasNext()) {
                try {
                    File file = new File(ss + String.format("%03d", i) + ".jpg");
                    if (file.exists()) {
                        i++;
                        it.next();
                        continue;
                    }
                    // 下载网络文件
                    int byteread = 0;
                    URL url;
                    Element e3 = it.next();
                    String imgURL = e3.attr("src");
                    url = new URL(imgURL);
                    URLConnection conn = url.openConnection();
                    InputStream inStream = conn.getInputStream();
                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[120400];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        //							bytesum += byteread;
                        fos.write(buffer, 0, byteread);
                    }
                    //						System.out.println(bytesum);
                    i++;
                } catch (FileNotFoundException e) {
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        fos.close();
                        //					Thread.sleep(100);
                    }

                }
            }
        }

        System.out.println("End");
    }

    // 韩漫
    @Test
    public void Demo6() throws Exception{
        Pictures t = new Pictures();
        Document doc1 = null;
        String ss = "D:\\private\\pictures\\comic02\\韩漫\\";
        String s1 = "https://www.hanmanzj.club/thread-12976-1-1.html";
        FileOutputStream fos = null;
        doc1 = t.getDocument(s1);
        Elements e1 = doc1.select("#thread_subject");
        String text = e1.text();
        String[] s = text.split(" ");

        File dir = new File(ss + s[0], String.format("%03d", Integer.valueOf(s[1].substring(1, s[1].length() - 1))));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Elements e2 = doc1.select(".t_f img");
        Iterator<Element> it = e2.iterator();
        int i = 1;
        while (it.hasNext()) {
            try {
                File file = new File(dir, String.format("%03d", i) + ".jpg");
                if (file.exists()) {
                    i++;
                    it.next();
                    continue;
                }
                // 下载网络文件
                int byteread = 0;
                URL url;
                Element e3 = it.next();
                String imgURL = e3.attr("file");
                url = new URL("https://www.hanmanzj.club/" + imgURL);
                URLConnection conn = url.openConnection();
                InputStream inStream = conn.getInputStream();
                fos = new FileOutputStream(file);

                byte[] buffer = new byte[120400];
                while ((byteread = inStream.read(buffer)) != -1) {
                    //							bytesum += byteread;
                    fos.write(buffer, 0, byteread);
                }
                //						System.out.println(bytesum);
                i++;
                Thread.sleep(100);
            } catch (FileNotFoundException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                    //					Thread.sleep(100);
                }

            }
        }

        System.out.println("End");
    }

    // 韩漫
    @Test
    public void Demo7() throws Exception{
        Pictures t = new Pictures();
        Document doc = null;
        String ss = "D:\\private\\pictures\\comic02\\韩漫\\";
        String s1 = "https://www.hanmanzj.club/forum-371-1.html";
        FileOutputStream fos = null;
        doc = t.getDocument(s1);
        Element e1 = doc.select(".ptn.xg2 strong").get(0);
        String text = e1.text();

        File dir = new File(ss, text.substring(1, text.length() - 1));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Elements e8 = doc.select("#waterfall .c.cl a");
        int j = 1;
        for (Element element : e8) {
            File dir1 = new File(dir, String.format("%03d", j));
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            String l = element.attr("href");
            Document doc1 = t.getDocument(l);
            Elements e2 = doc1.select(".t_f img");
            Iterator<Element> it = e2.iterator();
            int i = 1;
            while (it.hasNext()) {
                try {
                    File file = new File(dir1,  String.format("%03d", i) + ".jpg");
                    if (file.exists()) {
                        i++;
                        it.next();
                        continue;
                    }
                    // 下载网络文件
                    int byteread = 0;
                    URL url;
                    Element e3 = it.next();
                    String imgURL = e3.attr("file");
                    url = new URL("https://www.hanmanzj.club/" + imgURL);
                    URLConnection conn = url.openConnection();
                    InputStream inStream = conn.getInputStream();
                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[120400];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        //							bytesum += byteread;
                        fos.write(buffer, 0, byteread);
                    }
                    //						System.out.println(bytesum);
                    i++;
                    Thread.sleep(100);
                } catch (FileNotFoundException e) {
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        fos.close();
                        //					Thread.sleep(100);
                    }

                }
            }
            ++j;
        }

        System.out.println("End");
    }
}

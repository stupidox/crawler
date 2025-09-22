package com.hm.demo.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class Video {

    /**
     *
     * @param url
     *            访问路径
     * @return
     */
    public Document getDocument(String url) {
        try {
            Thread.sleep(1000);
            // 5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000)
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36")
                    .maxBodySize(Integer.MAX_VALUE)//设置文档长度
                    .get();
            //编码处理
            //return Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //斗鱼录像
    @Test
    public void Demo1() throws Exception{
        String s1 = "https://videows1.douyucdn.cn/live/high_2170191220200119150304-upload-ab3e/bf9ef27239dd48e78395bbef0b2eb176_00000";
        String s2 = ".ts?k=5f16349a2ad7808b10d445ab2f57ec38&t=5e2715b4&nlimit=5&u=49472258&ct=web_share&vid=12516937&pt=2&cdn=ws&d=ODI1MjI3Nzd8aDVvdXRlcnBsYXllcg==";

        int bytesum = 0;

        for (int i = 1; i < 60; i++) {

            // 下载网络文件
            int byteread = 0;

            URL url;
            if (i<10) {
                url = new URL(s1 + "0" + i + s2);
            }else{
                url = new URL(s1 + i + s2);
            }

            FileOutputStream fos = null;
            try {
                URLConnection conn = url.openConnection();
                InputStream inStream = conn.getInputStream();
                File file = new File("D:\\11\\1\\00.mp4");
                if (file.exists()) {
                    fos = new FileOutputStream(file, true);
                } else {
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                }

                byte[] buffer = new byte[120400];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fos.write(buffer, 0, byteread);
                }
                System.out.println(bytesum);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fos.close();
            }

        }

    }

    //b站
    @Test
    public void Demo2() throws Exception{

        int bytesum = 0;

        // 下载网络文件
        int byteread = 0;

        URL url = new URL("https://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/84/20/41272084/41272084_da2-1-30064.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1572622626&gen=playurl&os=ks3u&oi=2002925310&trid=d8329333e8cb4356831aa8fe3d7fc081u&platform=pc&upsig=1414cade78f2c766f0ae5070b15736a7&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=40149226");

        FileOutputStream fos = null;
        try {
            URLConnection conn = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;

            httpURLConnection.setRequestProperty("Accept-Charset", "identity");
            httpURLConnection.setRequestProperty("If-Range", "3bbe337a88a1b20a906ad9d96d4a558a");
//            httpURLConnection.setRequestProperty("Range", "bytes=0-27676176");//视频长度
            httpURLConnection.setRequestProperty("Referer", "https://www.bilibili.com/video/av24570287");
            httpURLConnection.setRequestProperty("Sec-Fetch-Mode", "cors");
            httpURLConnection.setRequestProperty("Sec-Fetch-Site", "cross-site");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
            InputStream inStream = conn.getInputStream();
            File file = new File("D:\\迅雷下载\\5.mp4");
            if (file.exists()) {
                fos = new FileOutputStream(file, true);
            } else {
                file.createNewFile();
                fos = new FileOutputStream(file);
            }

            byte[] buffer = new byte[120400];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fos.write(buffer, 0, byteread);
                System.out.println(bytesum/1024.0/1024.0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }

    }

    @Test
    public void Demo7() throws Exception{

        int bytesum = 0;

        // 下载网络文件
        int byteread = 0;

        URL url = new URL("https://webim.tim.qq.com/v4/openim/longpolling?websdkappid=537048168&v=1.7.0&platform=10&tinyid=144115200459282885&a2=2ca75e8cec8e637e6304c9504a19399335efbaa405a85fdc985f6ce1855bf2cab8ae643cd81e85efc19bcb5d25ea954d8544df4e24e54874a1aea588ac465cec3d502b66bce2eeb2&contenttype=json&sdkappid=1400029396&accounttype=9967&apn=1&reqtime=1580414859");

        try {
            URLConnection conn = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;

            httpURLConnection.setRequestProperty("Accept", "*/*");
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            httpURLConnection.setRequestProperty("Content-Length", "93");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Host", "webim.tim.qq.com");
            httpURLConnection.setRequestProperty("Origin", "https://msg.douyu.com");
            httpURLConnection.setRequestProperty("Referer", "https://msg.douyu.com/web/index.html?t=1580412400807&close=1");
            httpURLConnection.setRequestProperty("Sec-Fetch-Mode", "cors");
            httpURLConnection.setRequestProperty("Sec-Fetch-Site", "cross-site");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

            InputStream inStream = conn.getInputStream();
            StringBuffer sb = new StringBuffer();

            byte[] buffer = new byte[120400];
            while ((byteread = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, byteread));
//				System.out.println(bytesum/1024.0/1024.0);
            }
            int i = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    //爱奇艺
    @Test
    public void Demo3() throws Exception{

        int bytesum = 0;

        // 下载网络文件
        int byteread = 0;

        URL url = new URL("http://mpvideo.qpic.cn/tjg_3253951001_50000_09e976cfdfe7480c9904e9c27ac4c0c0.f10002.mp4?dis_k=28bd3b7e45208baf7929321154b9276c&dis_t=1579446084");

        FileOutputStream fos = null;
        try {
            URLConnection conn = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;

//			httpURLConnection.setRequestProperty(":authority", "ry429n.jomodns.com");//视频长度
//			httpURLConnection.setRequestProperty(":method", "GET");//视频长度
//			httpURLConnection.setRequestProperty(":path", "/r/bdcdnct.inter.71edge.com/videos/vts/20191126/13/f4/cd52351183d33b53753319dade0a19ab.ts?key=0d749a459948385fff0abba3fdd762d06&dis_k=11b17e07dbc7d7fde114e42432b09fd1&dis_t=1574840301&dis_dz=CT-HuBei_WuHan&dis_st=49&src=iqiyi.com&dis_hit=0&uuid=1b13891f-5dde27ed-24b&sgti=14_f1fd50b58622f0617df6c4b8df95a300_1574839954520&start=43992064&qd_uid=0&qd_tm=1574839958292&qdv=1&cross-domain=1&ve=&dfp=&contentlength=1021952&qd_src=01010031010000000000&qd_p=0&sd=739920&pv=0.1&qd_tvid=9749815600&qd_vip=0&qd_ip=0&stauto=0&end=45014016&qd_k=9d3568b20f78c1fbf045c44c5255524f&ori=pcw&num=7682");//视频长度
//			httpURLConnection.setRequestProperty(":scheme", "https");//视频长度
//			httpURLConnection.setRequestProperty("referer", "https://www.iqiyi.com/v_19ruzj8gv0.html?vfm=2008_aldbd");//视频长度
            httpURLConnection.setRequestProperty("Accept", "*/*");//视频长度
            httpURLConnection.setRequestProperty("Host", "mpvideo.qpic.cn");
            httpURLConnection.setRequestProperty("Sec-Fetch-Mode", "cors");
            httpURLConnection.setRequestProperty("Sec-Fetch-Site", "cross-site");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
            InputStream inStream = conn.getInputStream();
            File file = new File("D:\\11\\1\\1\\23.mp4");
            if (file.exists()) {
                fos = new FileOutputStream(file, true);
            } else {
                file.createNewFile();
                fos = new FileOutputStream(file);
            }

            byte[] buffer = new byte[120400];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fos.write(buffer, 0, byteread);
//				System.out.println(bytesum/1024.0/1024.0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }

    }
    int sums = 0;
    //爱奇艺
//	@Test
    public void Demo4(String surl) throws Exception{


//		URL url = new URL("http://data.video.iqiyi.com/videos/vts/20191126/13/f4/d72be28f54b146648753782eca3425d5.ts?qdv=1&qd_uid=0&qd_tvid=9749815600&qd_vip=0&qd_src=01010031010000000000&qd_tm=1574860400206&qd_ip=0&qd_p=0&qd_k=69b93b4e6f761842d62d7dd305c0675b&ve=&sgti=14_f1fd50b58622f0617df6c4b8df95a300_1574860396615&dfp=&qd_sc=14ae97b607594a2c15c9f2d2442996b8&pv=0.1&cross-domain=1&stauto=0");
        URL url = new URL(surl);

        try {
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection)conn;

            con.setRequestProperty("Origin", "https://www.iqiyi.com");//视频长度
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");//视频长度
            con.setRequestProperty("Connection", "keep-alive");//视频长度
            con.setRequestProperty("referer", "https://www.iqiyi.com/v_19ruzj8gv0.html?vfm=2008_aldbd");//视频长度
            con.setRequestProperty("Accept", "*/*");//视频长度
            con.setRequestProperty("Host", "data.video.iqiyi.com");
            con.setRequestProperty("Sec-Fetch-Mode", "cors");
            con.setRequestProperty("Sec-Fetch-Site", "same-site");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
//			out = new OutputStreamWriter(con.getOutputStream(),"utf-8");//解决传参时中文乱码
//			String json = "{\"start\":\"0\",\"end\":\"1508512\",\"contentlength\":\"1508512\",\"sd\":\"2214680\",\"qdv\":\"1\",\"qd_uid\":\"0\",\"qd_tvid\":\"9749815600\",\"qd_vip\":\"0\",\"qd_src\":\"01010031010000000000\",\"qd_tm\":\"1574841059771\",\"qd_ip\":\"0\",\"qd_p\":\"0\",\"qd_k\":\"4b0b59ecf3d2dd433fea9c69a6ea38f9\",\"ve\":\"sgti: 14_f1fd50b58622f0617df6c4b8df95a300_1574841055780\",\"dfp\":\"qd_sc: 6b43a76bf95c7730688aa976a3a8af25\",\"pv\":\"0.1\",\"cross-domain\":\"1\",\"stauto\":\"0\",}";
//			out.write(json);
//			out.flush();
            int byteread = 0;
            InputStream inStream = con.getInputStream();
            byte[] buffer = new byte[120400];
            StringBuffer sb = new StringBuffer();
            while ((byteread = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, byteread));
            }
            System.out.println(sb);
            JSONObject po = JSONArray.parseObject(sb.toString());
            Object ob = po.get("l");
            if(true){




                // 下载网络文件
                int byteread1 = 0;

                URL url1 = new URL(ob.toString());

                FileOutputStream fos1 = null;
                try {
                    URLConnection conn1 = url1.openConnection();
                    HttpURLConnection httpURLConnection = (HttpURLConnection)conn1;

                    httpURLConnection.setRequestProperty(":authority", "ry429n.jomodns.com");//视频长度
                    httpURLConnection.setRequestProperty(":method", "GET");//视频长度
                    httpURLConnection.setRequestProperty(":path", "/r/bdcdnct.inter.71edge.com/videos/vts/20191126/13/f4/cd52351183d33b53753319dade0a19ab.ts?key=0d749a459948385fff0abba3fdd762d06&dis_k=11b17e07dbc7d7fde114e42432b09fd1&dis_t=1574840301&dis_dz=CT-HuBei_WuHan&dis_st=49&src=iqiyi.com&dis_hit=0&uuid=1b13891f-5dde27ed-24b&sgti=14_f1fd50b58622f0617df6c4b8df95a300_1574839954520&start=43992064&qd_uid=0&qd_tm=1574839958292&qdv=1&cross-domain=1&ve=&dfp=&contentlength=1021952&qd_src=01010031010000000000&qd_p=0&sd=739920&pv=0.1&qd_tvid=9749815600&qd_vip=0&qd_ip=0&stauto=0&end=45014016&qd_k=9d3568b20f78c1fbf045c44c5255524f&ori=pcw&num=7682");//视频长度
                    httpURLConnection.setRequestProperty(":scheme", "https");//视频长度
                    httpURLConnection.setRequestProperty("referer", "https://www.iqiyi.com/v_19ruzj8gv0.html?vfm=2008_aldbd");//视频长度
                    httpURLConnection.setRequestProperty("Accept", "*/*");//视频长度
                    httpURLConnection.setRequestProperty("Host", "mpvideo.qpic.cn");
                    httpURLConnection.setRequestProperty("Sec-Fetch-Mode", "cors");
                    httpURLConnection.setRequestProperty("Sec-Fetch-Site", "cross-site");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
                    InputStream inStream1 = conn1.getInputStream();
                    File file1 = new File("D:\\11\\庆余年\\04.mp4");
                    if (file1.exists()) {
                        fos1 = new FileOutputStream(file1, true);
                    } else {
                        file1.createNewFile();
                        fos1 = new FileOutputStream(file1);
                    }

                    byte[] buffer1 = new byte[1204*1024];
                    while ((byteread1 = inStream1.read(buffer1)) != -1) {
                        sums += byteread1;
                        fos1.write(buffer1, 0, byteread1);
                        System.out.println(sums/1024.0/1024.0);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    fos1.close();
                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    //爱奇艺
    @Test
    public void Demo5() throws Exception{


        URL url = new URL("https://cache.video.iqiyi.com/dash?tvid=9749854500&bid=500&vid=c197778c4b7c7aeef9c19cf6acd5acb8&src=01010031010000000000&vt=0&rs=1&uid=&ori=pcw&ps=0&k_uid=f1fd50b58622f0617df6c4b8df95a300&pt=0&d=0&s=&lid=&cf=&ct=&authKey=d919284d900d6acfd9aaaa16ce5d8acb&k_tag=1&ost=0&ppt=0&dfp=a1ae8d38a463b24d818c7581888ac6ade1e45b45f3bbe7ed4093968ad798435b3e&locale=zh_cn&prio=%7B%22ff%22%3A%22f4v%22%2C%22code%22%3A2%7D&pck=&k_err_retries=0&up=&qd_v=2&tm=1574937124176&qdy=a&qds=0&k_ft1=143486267424772&k_ft4=68689924&k_ft5=1&bop=%7B%22version%22%3A%2210.0%22%2C%22dfp%22%3A%22a1ae8d38a463b24d818c7581888ac6ade1e45b45f3bbe7ed4093968ad798435b3e%22%7D&ut=0&vf=133f49a640adb7ec1b9e66d40a778206");

        try {
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection)conn;

            con.setRequestProperty(":authority", "cache.video.iqiyi.com");//视频长度
            con.setRequestProperty(":method", "GET");//视频长度
            con.setRequestProperty(":path", "/dash?tvid=9779862700&bid=500&vid=1280b83570693cb03e2fa0266080ed92&src=01010031010000000000&vt=0&rs=1&uid=&ori=pcw&ps=0&k_uid=f1fd50b58622f0617df6c4b8df95a300&pt=0&d=0&s=&lid=&cf=&ct=&authKey=d00cc721ea98f3bdabdeb8bbdd79ebff&k_tag=1&ost=0&ppt=0&dfp=a1ae8d38a463b24d818c7581888ac6ade1e45b45f3bbe7ed4093968ad798435b3e&locale=zh_cn&prio=%7B%22ff%22%3A%22f4v%22%2C%22code%22%3A2%7D&pck=&k_err_retries=0&up=&qd_v=2&tm=1574865917240&qdy=a&qds=0&k_ft1=143486267424772&k_ft4=68689924&k_ft5=1&bop=%7B%22version%22%3A%2210.0%22%2C%22dfp%22%3A%22a1ae8d38a463b24d818c7581888ac6ade1e45b45f3bbe7ed4093968ad798435b3e%22%7D&ut=0&vf=21925997aaa90097526647437763e74e");//视频长度
            con.setRequestProperty(":scheme", "https");//视频长度
            con.setRequestProperty("cache-control", "no-cache");//视频长度
            con.setRequestProperty("accept", "application/json, text/javascript");//视频长度
            con.setRequestProperty("accept-encoding", "gzip, deflate, br");//视频长度
            con.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");//视频长度
            con.setRequestProperty("cookie", "P00004=-1218498186.1512751974.85b8a34dd1; _ga=GA1.2.1802238476.1515511032; QP001=1; QC118=%7B%22color%22%3A%22FFFFFF%22%2C%22channelConfig%22%3A0%2C%22hadTip%22%3A1%7D; QC006=15f3dd1caee2f55b5445047deebb4164; T00404=570d8b79e01c34511933f7b320b8b8bf; QC173=0; QC170=1; uid=0; cf=1524734721; QP0017=100; QP0018=100; QP008=0; IMS=IggQARj_moDrBSokCiBlN2VmZDZhOThhYzE4N2MzNmE4YTJmNTFlZDMxZDQ2NhAA; QC005=f1fd50b58622f0617df6c4b8df95a300; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216de8521ab31ad-03a3e51c432b91-3d375b01-1049088-16de8521ab42b6%22%2C%22%24device_id%22%3A%2216de8521ab31ad-03a3e51c432b91-3d375b01-1049088-16de8521ab42b6%22%2C%22props%22%3A%7B%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; QP0013=; QC175=%7B%22upd%22%3Atrue%2C%22ct%22%3A%22%22%7D; unrepeatCookie=600000000%2C600000001%2C; QC178=true; QP0025=1; QP0010=1; QP007=2100; QC008=1512751907.1574839954.1574851045.30; nu=0; QILINPUSH=1; Hm_lvt_53b7374a63c37483e5dd97d78d9bb36e=1574843689,1574851045,1574863831,1574864331; QC007=DIRECT; QC010=211223953; QC159=%7B%22color%22%3A%22FFFFFF%22%2C%22channelConfig%22%3A1%2C%22hadTip%22%3A1%2C%22speed%22%3A13%2C%22density%22%3A30%2C%22opacity%22%3A86%2C%22isFilterColorFont%22%3A1%2C%22proofShield%22%3A0%2C%22forcedFontSize%22%3A24%2C%22isFilterImage%22%3A1%2C%22isOpen%22%3A0%2C%22isset%22%3A1%2C%22hideRoleTip%22%3A1%7D; __dfp=a1ae8d38a463b24d818c7581888ac6ade1e45b45f3bbe7ed4093968ad798435b3e@1576135953902@1574839954902; websocket=false; TQC002=type%3Djspfmc140109%26pla%3D11%26uid%3Df1fd50b58622f0617df6c4b8df95a300%26ppuid%3D%26brs%3DCHROME%26pgtype%3Dplay%26purl%3Dhttps%3A%252F%252Fwww.iqiyi.com%252Fv_19ruzj9ny0.html%3Fvfm%253D2008_aldbd%26cid%3D1%26tmplt%3D%26tm1%3D8518%2C0; Hm_lpvt_53b7374a63c37483e5dd97d78d9bb36e=1574865917");//视频长度
            con.setRequestProperty("Origin", "https://www.iqiyi.com");//视频长度
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");//视频长度
            con.setRequestProperty("Connection", "keep-alive");//视频长度
            con.setRequestProperty("referer", "https://www.iqiyi.com/v_19ruzj9ny0.html?vfm=2008_aldbd");//视频长度
            con.setRequestProperty("Host", "data.video.iqiyi.com");
            con.setRequestProperty("Sec-Fetch-Mode", "cors");
            con.setRequestProperty("Sec-Fetch-Site", "same-site");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
//			out = new OutputStreamWriter(con.getOutputStream(),"utf-8");//解决传参时中文乱码
//			String json = "{\"start\":\"0\",\"end\":\"1508512\",\"contentlength\":\"1508512\",\"sd\":\"2214680\",\"qdv\":\"1\",\"qd_uid\":\"0\",\"qd_tvid\":\"9749815600\",\"qd_vip\":\"0\",\"qd_src\":\"01010031010000000000\",\"qd_tm\":\"1574841059771\",\"qd_ip\":\"0\",\"qd_p\":\"0\",\"qd_k\":\"4b0b59ecf3d2dd433fea9c69a6ea38f9\",\"ve\":\"sgti: 14_f1fd50b58622f0617df6c4b8df95a300_1574841055780\",\"dfp\":\"qd_sc: 6b43a76bf95c7730688aa976a3a8af25\",\"pv\":\"0.1\",\"cross-domain\":\"1\",\"stauto\":\"0\",}";
//			out.write(json);
//			out.flush();
            int byteread = 0;
            Map<String, List<String>> headerFields = con.getHeaderFields();
            Set<String> k = headerFields.keySet();
            for (String st : k) {
                List<String> list = headerFields.get(st);
                for (String string : list) {
                    System.out.println(string);
                }

            }
            InputStream inStream = con.getInputStream();

            byte[] buffer = new byte[120400];
            List<byte[]> list = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            int l = 0;
            while ((byteread = inStream.read(buffer)) != -1) {
                if (byteread == 120400) {
                    list.add(buffer);
                    l += 120400;
                }else{
                    l += byteread;
                    list.add(Arrays.copyOfRange(buffer, 0, byteread));
                }
            }
            con.disconnect();
            ListIterator<byte[]> it = list.listIterator();
            byte[] b = new byte[l];
            l = 0;
            while(it.hasNext()){
                byte[] next = it.next();
                System.arraycopy(next, 0, b, l, next.length);
                l += next.length;
            }
            byte[] newb = unGzip(b);
            sb.append(new String(newb));
            JSONObject po = JSONArray.parseObject(sb.toString());
            JSONObject p2 = po.getJSONObject("data");
            JSONObject p3 = p2.getJSONObject("program");
            JSONArray p4 = p3.getJSONArray("video");

            JSONObject p5 = (JSONObject)p4.get(0);
            String str = p5.getString("m3u8");
            for (Object object : p4) {
                p5 = (JSONObject)object;
                str = p5.getString("m3u8");
                if(!StringUtils.isEmpty(str)){
                    break;
                }
            }

            String s1 = "&pv=0.1&cross-domain=1&stauto=0";
            String[] ss = str.split("#EXTINF:[0-9]");

            List<String> ll = new ArrayList<>();
            for (String st : ss) {
                if (st.contains("http")) {

                    if(st.contains("#EXT-X-ENDLIST")){
                        st = st.split("#EXT-X-ENDLIST")[0];
                    }
                    st = st.substring(0, st.length() - 1).substring(2)+s1;
                    int i1 = st.indexOf("?");
                    int i2 = st.indexOf("qdv");
                    st = st.substring(0, i1+1)+st.substring(i2, st.length());
                    if (!ll.contains(st)) {
                        ll.add(st);
                    }
                }
            }

            ListIterator<String> itt = ll.listIterator();
            while(itt.hasNext()){
                String u = itt.next();
                Demo4(u);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    public static byte[] unGzip(byte[] content) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content));
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gis.read(buffer)) != -1) {
                baos.write(buffer, 0, n);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteToHexString(byte[] bytes) {
        if(bytes == null)
            return "";
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return decompressed;
    }

    static int readUByte(InputStream in) throws IOException {
        int b = in.read();
        if (b == -1) {
            throw new EOFException();
        }
        if (b < -1 || b > 255) {
        }
        return b;
    }
}

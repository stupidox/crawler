package com.hm.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
            Document doc = Jsoup.parse(connection.getInputStream(), "gb2312", uri);
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
    public StringBuffer getText(String uri) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            Thread.sleep(1000);
            // 5000是设置连接超时时间，单位ms
            // 5000是设置连接超时时间，单位ms
            URL url = new URL("https://jpbqg5.com/api/reader_js.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
            connection.setRequestMethod("POST");
            //是否允许缓存，默认true。
            connection.setUseCaches(Boolean.FALSE);
            //是否开启输出输入，如果是post使用true。默认是false
            //connection.setDoOutput(Boolean.TRUE);
            //connection.setDoInput(Boolean.TRUE);
            //设置请求头信息
            connection.addRequestProperty("accept", "*/*");
            connection.addRequestProperty("accept-encoding", "gzip, deflate, br, zstd");
            connection.addRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            connection.addRequestProperty("connection", "keep-alive");
            connection.addRequestProperty("content-length", "40");
            connection.addRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.addRequestProperty("host", "jpbqg5.com");
            connection.addRequestProperty("origin", "https://jpbqg5.com");
            connection.addRequestProperty("referer", uri);
            connection.addRequestProperty("sec-ch-ua", "\"Not A(Brand\";v=\"8\", \"Chromium\";v=\"132\", \"Google Chrome\";v=\"132\"");
            connection.addRequestProperty("sec-ch-ua-mobile", "?0");
            connection.addRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            connection.addRequestProperty("sec-fetch-dest", "empty");
            connection.addRequestProperty("sec-fetch-mode", "cors");
            connection.addRequestProperty("sec-fetch-site", "same-origin");
            connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36");
            connection.addRequestProperty("x-requested-with", "XMLHttpRequest");
            //设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(90000);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(90000);
            // 输入 输出 都打开
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //开始连接
            connection.connect();
            //开始请求
            OutputStream out = connection.getOutputStream();
            out.write(("articleid=72408&pid=1&chapterid="
                    + uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."))).getBytes());
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb = sb.append(line);
            }
            br.close();
            //TODO ---
            // return Jsoup.connect(uri).timeout(90000).get();
            return sb;
        } catch (Exception e) {
            System.out.println("---" + uri);
            System.out.println(e.getMessage());
            Thread.sleep(2000);
            return getText(uri);
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
        NovelGZIPOnce t = new NovelGZIPOnce();
        Document doc = null;
        boolean f = false;
        String index;
        index = "http://www.xxdzs3.com/e-book/45741.html";
        String pre = "https://www.00ksb.com";
        doc = t.get(index);
        Elements novels = doc.select("#ml").select(".list-item");
        Elements novelsPages = novels.select("li").select("a");
        File novel = new File("F:\\temp\\极限诱惑.txt");
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

            download(t, pre, href, novel);
        }

        // 分页
        System.out.println("End");
    }

    private void download(NovelGZIPOnce t, String pre, String contentUrl, File novel) throws Exception {
        Document page;
        page = t.get(contentUrl);
        Elements s1 = page.select("#content");
        String title = null;
        if (m < str.length) {
            title = s1.select("h1").get(0).text().replace("极限诱惑-", "") + " " + str[m++];
        } else {
            title = s1.select("h1").get(0).text();
        }
        String content = s1.select(".chapter").toString();
        content = content.replaceAll("<font[^莎]*</font>", "").replaceAll("<div[^>]*>", "").replaceAll("</div[^>]*>", "").replaceAll("<br>", "\n");
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

    static String[] str = ("枕边的青春肉体\n" +
            "年轻的感觉真好\n" +
            "不能说的秘密\n" +
            "性感小野猫\n" +
            "梦想没了\n" +
            "你不会是思/春了吧\n" +
            "好女孩也喜欢\n" +
            "卖身抵债\n" +
            "湿润的花蕊\n" +
            "罚你让我热起来\n" +
            "美女老师\n" +
            "同性恋女友\n" +
            "美丽的沉睡者\n" +
            "是销魂是离愁\n" +
            "再见，我的朋友们和王八蛋们\n" +
            "接下来的日子，请相信我一次\n" +
            "洗澡当然要一起洗\n" +
            "领导审查\n" +
            "三人行，必有我师\n" +
            "雷霆色诱\n" +
            "美人心事\n" +
            "梦想再现\n" +
            "骑着马儿过草原\n" +
            "不入色海，如何度人\n" +
            "一江春水向东流\n" +
            "的方式有很多种\n" +
            "免费大餐\n" +
            "麻辣本色\n" +
            "不入流的角色\n" +
            "为你动情\n" +
            "我是你的男人\n" +
            "青春的炫耀\n" +
            "惊人的猜想\n" +
            "他绝不是寻常人物\n" +
            "脱光一群女人很容易\n" +
            "酒壮美人胆\n" +
            "销魂的初夜\n" +
            "婉婷\n" +
            "无法抗拒的魅力\n" +
            "我就是你的礼物\n" +
            "欲仙欲死\n" +
            "欢喜冤家\n" +
            "大杀四方\n" +
            "意乱情迷\n" +
            "密码传情\n" +
            "爱心泛滥\n" +
            "泰式按摩\n" +
            "一男三女\n" +
            "美女超进化\n" +
            "绝色母女\n" +
            "色诱计划\n" +
            "死神降临\n" +
            "调叫人妻\n" +
            "职场幸骚扰\n" +
            "双美花开\n" +
            "床/上联盟\n" +
            "红粉佳人\n" +
            "韩国群美\n" +
            "精益求精\n" +
            "乘龙快婿\n" +
            "疯狂2+1\n" +
            "美人多情\n" +
            "车轮大战\n" +
            "重磅炸弹\n" +
            "美女叠罗汉\n" +
            "绝色艳星\n" +
            "美艳逼人\n" +
            "春/梦无痕\n" +
            "岳母嫂子来接种\n" +
            "母女姑嫂双双飞1\n" +
            "战斗的快感\n" +
            "美女主播的诱惑\n" +
            "母女姑嫂双双飞2\n" +
            "母女姑嫂双双飞3\n" +
            "母女姑嫂双双飞4\n" +
            "风云突变\n" +
            "敌人美妻最鲜嫩\n" +
            "现场直播\n" +
            "火热连线\n" +
            "银浪翻滚\n" +
            "重炮轰击\n" +
            "五凤一龙\n" +
            "角色扮演\n" +
            "五花十眼\n" +
            "拍摄现场\n" +
            "上对床睡错人\n" +
            "小姨妈来兴师问罪\n" +
            "旋转餐厅旋转爱\n" +
            "餐厅美少女\n" +
            "在美女身上插上主权的标识\n" +
            "人妻欲念\n" +
            "花儿灌溉\n" +
            "宝儿姨妈的迷幻激情\n" +
            "金发美女来取经\n" +
            "中外混战\n" +
            "迷乱冲刺\n" +
            "十女连环\n" +
            "肉/体列车\n" +
            "妈妈现在就想要\n" +
            "被你玩死了\n" +
            "疯狂报复\n" +
            "将错就错\n" +
            "美女小姨\n" +
            "龌龊男人\n" +
            "迷幻能力\n" +
            "美女分级\n" +
            "运筹帷幄\n" +
            "美人赌注\n" +
            "集体车震\n" +
            "火热香吻\n" +
            "香软温柔\n" +
            "乱点秋香\n" +
            "电视台 化妆间 女主播 乱\n" +
            "主播台大战\n" +
            "绝色主播自拍大片\n" +
            "美艳姐妹引君入瓮\n" +
            "双珠合璧姐妹同心\n" +
            "好姐妹一起上！\n" +
            "火热连环\n" +
            "嫂子说再来一次好不好\n" +
            "岳母大人的要求\n" +
            "娇妻美嫂岳母\n" +
            "挤奶女工\n" +
            "卧底双胞胎\n" +
            "情趣用品店的秘密\n" +
            "众美女的产品展示\n" +
            "美女老师们的开放日\n" +
            "多情女校长的震撼教育\n" +
            "被美女骗去相亲\n" +
            "请好好享用我们的宝贝吧\n" +
            "第一次的极致快感\n" +
            "可爱人妻的深夜客房服务\n" +
            "人家还想要\n" +
            "是美女就全拿下\n" +
            "绝色母女的疼爱\n" +
            "家族聚会双双飞\n" +
            "帝王游戏1vs12\n" +
            "美少女们的樱花大战\n" +
            "瑶池仙女们的牛奶浴\n" +
            "绝色美女争夺战\n" +
            "是什么在诱惑我\n" +
            "你身上有我想要的东西\n" +
            "绝色女警的热情\n" +
            "双胞胎姐妹的香饵诱惑\n" +
            "火热陷阱\n" +
            "嫩模聚会\n" +
            "一男六女\n" +
            "强行搭车的女警官\n" +
            "被人抓住了把柄\n" +
            "就想被你爱爱\n" +
            "美少妇激情看护\n" +
            "巡诊的美女医生团\n" +
            "和女医生们研究病情\n" +
            "车轮大战\n" +
            "美女医生中的极品\n" +
            "美女们的特异功能\n" +
            "赌神再现\n" +
            "扫黑风暴\n" +
            "宝儿姨妈的热情\n" +
            "双宿双飞\n" +
            "美女空姐送上门强行四重奏\n" +
            "调叫日本美女商务代表\n" +
            "美女/奶/牛的强悍反击\n" +
            "日本人/妻招待会\n" +
            "名模到访姐妹争宠\n" +
            "试衣间的乱战\n" +
            "电梯惊魂\n" +
            "温泉温柔\n" +
            "集体按摩\n" +
            "爱的催眠\n" +
            "美女彩绘\n" +
            "最高境界\n" +
            "解除咒语\n" +
            "诱惑与危机\n" +
            "迷乱世界\n" +
            "基因异常\n" +
            "美艳牺牲\n" +
            "吸血鬼之王\n" +
            "最伟大的后宫\n" +
            "老大的女人\n" +
            "极限诱惑\n" +
            "美女乱战\n" +
            "非礼勿视\n" +
            "日本女人\n" +
            "成人礼\n" +
            "温泉疗法\n" +
            "给美女充电\n" +
            "罪恶之城\n" +
            "迷路的小乔\n" +
            "性感武器\n" +
            "冰火女人\n" +
            "群女混战\n" +
            "绝色老板娘人妻之惑\n" +
            "老婆蜜友\n" +
            "色诱计划\n" +
            "大欢喜佛\n" +
            "国色天香\n" +
            "王者归来\n" +
            "推了小龙女\n" +
            "人妻丽香\n" +
            "大战舞娘\n" +
            "老板的女儿\n" +
            "突破禁忌\n" +
            "以一当十，一骑当千\n" +
            "火热冲撞\n" +
            "极度堕落\n" +
            "美女药引\n" +
            "美女催眠\n" +
            "抢你的女人\n" +
            "研究女人的方法\n" +
            "母子出击\n" +
            "妈妈，我要尿尿！\n" +
            "火热深入\n" +
            "穿着女警的衣服上床\n" +
            "姐姐带你出去耍流氓\n" +
            "征用人妻治病\n" +
            "采花大战御女神功\n" +
            "天神姐夫妖媚小姨\n" +
            "大乔小乔双美夹机\n" +
            "情荡录音间\n" +
            "姐妹连环猛攻\n" +
            "三女连环战\n" +
            "蜜桃女孩\n" +
            "两位美嫂的火爆逆袭\n" +
            "岳母美嫂的热情考验\n" +
            "岳母和少女团体的联合攻击\n" +
            "惊射少女杨玉环\n" +
            "贵妃还是女仆？\n" +
            "神女妖娆花心荡漾\n" +
            "把警视厅的女人们统统变成性奴\n" +
            "野猫姐妹八凤迎龙\n" +
            "警花卧底面试集体受惊\n" +
            "岳母美嫂的火热礼物\n" +
            "抱着岳母迎接小姨妈\n" +
            "人夫前轮番爆爆美娇娘").split("\n");

    static int m = 0;
}

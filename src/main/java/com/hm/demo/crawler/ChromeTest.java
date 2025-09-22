package com.hm.demo.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChromeTest {
    static String blank = "                                                                                                     ";

    public static void main(String[] args) throws Exception {
        // download("社团学姊", "https://hman86.com/manga-read/321656232", "社团学姊-", 221, 0);
        // download("我的合租女室友是不是过于淫荡了", "https://hman86.com/manga-read/321656884", "[3D]我的合租女室友是不是过于淫荡了-", 0, 0);
        // download("你的女友正在出轨中", "https://hman86.com/manga-read/321656885", "[3D]你的女友正在出轨中-", 0, 0);
        download("秘密教学", "https://hman86.com/manga-read/321655496", "秘密教学-", 290, 0);
        download("人妻猎人", "https://hman86.com/manga-read/321655430", "异界猎妻人-", 108, 0);
        // download("[3D]我的美腿女友和她的内向表弟", "https://hman86.com/manga-read/321655009", "[3D]我的美腿女友和她的内向表弟-", 0, 0);
        // download("[3D]我和妈妈的秘密游戏", "https://hman86.com/manga-read/321654987", "我和妈妈的秘密游戏[3D]-", 0, 0);
        // download("[3D]新姐姐的味道", "https://hman86.com/manga-read/321654776", "[3D]新姐姐的味道-", 1, 0);
        // download("[3D]我成瞭大反派", "https://hman86.com/manga-read/321654720", "[3D]我成瞭大反派-", 54, 0);
        // download("[3D]除去巫山不是云[艷母]", "https://hman86.com/manga-read/321655516", "[3D]除去巫山不是云[艷母]-", 10, 44);
        // download("[3D]我的超能力", "https://hman86.com/manga-read/321656503", "[3D]我的超能力-", 11, 26);
        // download("[3D]妈妈与女友互换身体", "https://hman86.com/manga-read/321655282", "妈妈与女友互换身体[3D]-", 0, 0);
        // download("[3D]总裁的夫人沈卉宜01-04+后传", "https://hman86.com/manga-read/321654847", "[3D]总裁的夫人沈卉宜01-04+后传-", 12, 27);
        // download("[3D]我成瞭大反派_第二季", "https://hman86.com/manga-read/321656776", "[3D]我成瞭大反派_第二季-", 100, 0);
    }

    /**
     *
     * @param name
     * @param link
     * @param sub
     * @param jump 话数-1或index
     * @param x 最后一张的数字
     * @throws Exception
     */
    public static void download(String name, String link, String sub, int jump, int x) throws Exception {
        // 启动chromedriver 如果启动chrome失败，需要更新ChromeDriver
        // https://googlechromelabs.github.io/chrome-for-testing/
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.bin", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        // 设置浏览器文件默认下载路径
        // 创建ChromeOptions对象
        ChromeOptions chromeOptions = new ChromeOptions();
        // 创建HashMap对象和指定下载路径
        HashMap<String, Object> chromePrefs = new HashMap<>();
        // 设置默认下载路径
        // 禁止弹窗
        chromePrefs.put("profile.default_content_settings.popups", 0);
        // 下载地址
        chromePrefs.put("download.default_directory", "E:\\private\\pics");
        // 禁止图片加载
        //chromePrefs.put("profile.managed_default_content_settings.images", 2);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver(chromeOptions);

        // 配置信息
        ChromeOptions chromeOption = new ChromeOptions();
        // 通过addArguments方法添加参数设置
        // 设置浏览器是否以无头模式（不显示界面）运行
        //chromeOption.addArguments("--headless");
        // 启动最大化
        chromeOption.addArguments("--start-maximized");
        // 设置中文简体
        chromeOption.addArguments("--lang=zh-CN");
        // 关闭GPU 关闭图片渲染
        chromeOption.addArguments("--disable-gpu");
        // 设置浏览器打开窗口的大小,非必要属性
        chromeOption.addArguments("--window-size=2360,1280");
        // 打开控制台
        chromeOption.addArguments("--auto-open-devtools-for-tabs");
        // 消除安全校验
        chromeOption.addArguments("--allow-running-insecure-content");
        // 忽略与证书相关的错误
        chromeOption.addArguments("--ignore-certificate-errors");

        // 操作
        loopGet(driver, link, "第一个标签页", 0);
        Thread.sleep(5000);
        try {
            WebElement cancelButton = driver.findElement(By.id("cancel"));
            cancelButton.click();
        } catch (Exception e) {
        }
        WebElement el1 = driver.findElement(By.id("glist-1"));
        WebElement el2 = el1.findElement(By.className("scroll-content"));
        List<WebElement> dirList = el2.findElements(By.tagName("a"));
        Robot rb = new Robot();
        rb.mouseMove(-1, -1);
        rb.mouseMove((int) (643/1.5), (int) (789/1.5));
        int size = dirList.size();
        // 用来删除.webp的list
        List<String> webpList = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            // 跳过已下载页面
            if (j < jump) {
                continue;
            }
            System.out.print(blank + j + "/" + (size - 1));
            // 跳转图片页面
            Thread.sleep(1000);
            String uri = dirList.get(j).getAttribute("href");
            String title = dirList.get(j).getAttribute("title");
            if (title.startsWith(sub)) {
                title = title.substring(sub.length());
            }
            while (title.endsWith(".")) {
                title = title.substring(0, title.length() - 1);
            }
            title = removeChar(title);
            driver.switchTo().newWindow(WindowType.TAB);
            loopGet(driver, uri, title, -1);

            try {
                WebElement cancelButton = driver.findElement(By.id("cancel"));
                cancelButton.click();
            } catch (Exception e) {
            }

            // 下载图片
            WebElement main = driver.findElement(By.id("main"));
            List<WebElement> imgList = main.findElements(By.tagName("img"));
            System.out.println("\t" + imgList.size());
            for (int i = 0; i < imgList.size(); i++) {
                // 跳过已下载页面
                if (j <= jump && i < x) {
                    continue;
                }
                Thread.sleep(1000);
                WebElement img = imgList.get(i);
                String url = img.getAttribute("data-original");
                driver.switchTo().newWindow(WindowType.TAB);
                loopGet(driver, url, title, (i+1));
                Thread.sleep(1000);
                WebElement imgE = null;
                try {
                    imgE = driver.findElement(By.tagName("img"));
                } catch (Exception e) {
                    driver.close();
                    driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size()-1]);
                    continue;
                }
                if (!imgE.isDisplayed()) {
                    driver.close();
                    driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size()-1]);
                    continue;
                }
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON3_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_MASK);
                Thread.sleep(300);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(300);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(300);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(300);
                numToString(i + 1);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                driver.close();
                driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size()-1]);
                Thread.sleep(1000);

                // 移动图片
                File webpFile = new File("C:\\Users\\Luke\\Downloads\\" + String.format("%03d", i + 1) + ".webp");
                File jpgFile = new File("C:\\Users\\Luke\\Downloads\\" + String.format("%03d", i + 1) + ".jpg");
                File pngFile = new File("C:\\Users\\Luke\\Downloads\\" + String.format("%03d", i + 1) + ".png");
                if (webpFile.exists()) {
                    File dest = new File("E:\\private\\pics\\" + name + "\\" +
                            title + "\\" + String.format("%03d", i + 1) + ".webp");
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    webpFile.renameTo(dest);
                    new File(webpFile.getAbsolutePath()).delete();
                    // webp转成png
                    try {
                        Pictures1.webpToPng(dest.getAbsolutePath(),
                                "E:\\private\\pics\\" + name + "\\" + title + "\\" + String.format("%03d", i + 1) + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        i--;
                    }
                } else if(jpgFile.exists()) {
                    File dest = new File("E:\\private\\pics\\" + name + "\\" +
                            title + "\\" + String.format("%03d", i + 1) + ".jpg");
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    jpgFile.renameTo(dest);
                    new File(jpgFile.getAbsolutePath()).delete();
                } else if(pngFile.exists()) {
                    File dest = new File("E:\\private\\pics\\" + name + "\\" +
                            title + "\\" + String.format("%03d", i + 1) + ".png");
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    pngFile.renameTo(dest);
                    new File(pngFile.getAbsolutePath()).delete();
                }
            }
            driver.close();
            driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size()-1]);

            // 删除.webp文件
            if (webpList.size() >= 2) {
                webpList.remove(0);
            }
            webpList.add("E:\\private\\pics\\" + name + "\\" + title);
            deleteFilesInDir(webpList);
        }
        driver.quit();
    }

    private static void deleteFilesInDir(List<String> webpList) throws Exception {
        if (webpList.size() > 0) {
            for (String path : webpList) {
                File dir = new File(path);
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].getName().endsWith(".webp")) {
                            files[i].delete();
                        }
                    }
                }
            }
        }
    }

    private static void loopGet(WebDriver driver, String uri, String title, int i) throws Exception {
        try {
            driver.get(uri);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n" + blank + "再次尝试： " + title + " " + i);
            Robot rb = new Robot();
            rb.mouseMove(-1, -1);
            rb.mouseMove((int) (165/1.5), (int) (110/1.5));
            rb.mousePress(InputEvent.BUTTON1_MASK);
            Thread.sleep(1);
            rb.mouseRelease(InputEvent.BUTTON1_MASK);
            rb.mouseMove(-1, -1);
            rb.mouseMove((int) (643/1.5), (int) (789/1.5));
            driver.close();
            driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size()-1]);
            driver.switchTo().newWindow(WindowType.TAB);
            loopGet(driver, uri, title, i);
        }
    }

    // 去除windows不能包含的特殊字符
    private static String removeChar(String title) {
        if (title.contains("\\")) {
            title = title.replaceAll("\\\\", "");
        }
        if (title.contains("/")) {
            title = title.replaceAll("/", "");
        }
        if (title.contains("?")) {
            title = title.replaceAll("\\?", "？");
        }
        if (title.contains("<")) {
            title = title.replaceAll("<", "《");
        }
        if (title.contains(">")) {
            title = title.replaceAll(">", "》");
        }
        if (title.contains("*")) {
            title = title.replaceAll("\\*", "x");
        }
        if (title.contains("|")) {
            title = title.replaceAll("\\|", " ");
        }
        if (title.contains(":")) {
            title = title.replaceAll(":", "：");
        }
        if (title.contains("\"")) {
            title = title.replaceAll("\"", "”");
        }
        return title;
    }

    public static void numToString(int i) throws Exception {
        String str = String.format("%03d", i);
        char[] chars = str.toCharArray();
        Robot robot = new Robot();
        Thread.sleep(1500);
        for (char c : chars) {
            if (c == '0') {
                robot.keyPress(KeyEvent.VK_NUMPAD0);
                robot.keyRelease(KeyEvent.VK_NUMPAD0);
                Thread.sleep(100);
            } else if (c == '1') {
                robot.keyPress(KeyEvent.VK_NUMPAD1);
                robot.keyRelease(KeyEvent.VK_NUMPAD1);
                Thread.sleep(100);

            } else if (c == '2') {
                robot.keyPress(KeyEvent.VK_NUMPAD2);
                robot.keyRelease(KeyEvent.VK_NUMPAD2);
                Thread.sleep(100);
            } else if (c == '3') {
                robot.keyPress(KeyEvent.VK_NUMPAD3);
                robot.keyRelease(KeyEvent.VK_NUMPAD3);
                Thread.sleep(100);
            } else if (c == '4') {
                robot.keyPress(KeyEvent.VK_NUMPAD4);
                robot.keyRelease(KeyEvent.VK_NUMPAD4);
                Thread.sleep(100);
            } else if (c == '5') {
                robot.keyPress(KeyEvent.VK_NUMPAD5);
                robot.keyRelease(KeyEvent.VK_NUMPAD5);
                Thread.sleep(100);
            } else if (c == '6') {
                robot.keyPress(KeyEvent.VK_NUMPAD6);
                robot.keyRelease(KeyEvent.VK_NUMPAD6);
                Thread.sleep(100);
            } else if (c == '7') {
                robot.keyPress(KeyEvent.VK_NUMPAD7);
                robot.keyRelease(KeyEvent.VK_NUMPAD7);
                Thread.sleep(100);
            } else if (c == '8') {
                robot.keyPress(KeyEvent.VK_NUMPAD8);
                robot.keyRelease(KeyEvent.VK_NUMPAD8);
                Thread.sleep(100);
            } else if (c == '9') {
                robot.keyPress(KeyEvent.VK_NUMPAD9);
                robot.keyRelease(KeyEvent.VK_NUMPAD9);
                Thread.sleep(100);
            }
        }
    }
}

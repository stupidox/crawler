package com.hm.demo.crawler;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class WindowsUtils {

    /**
     * 打开windows资源管理器
     * @param dir 目录
     */
    public static void openDir(File dir) throws Exception {
        if (Objects.nonNull(dir) && dir.exists()) {
            Desktop.getDesktop().open(dir);
        }
    }

    /**
     * 本地文件当做数据库
     * @param dir 要保存的路径
     * @param path 数据库文件
     * @param append 是否覆盖，true 覆盖，false 追加
     */
    public static void writeToFile(File dir, String path, boolean append) throws Exception {
        File databaseFile = new File(path);
        if (!databaseFile.exists()) {
            databaseFile.getParentFile().mkdirs();
            databaseFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(path, append);
        // 换行
        if (append) {
            fos.write("\n".getBytes());
        }
        fos.write(dir.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\").getBytes());
        fos.close();
    }
}

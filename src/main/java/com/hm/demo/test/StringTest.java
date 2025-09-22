package com.hm.demo.test;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StringTest {
    public static void main(String[] args) throws Exception {
        File dir = new File("E:\\private\\pics\\[3D]我成瞭大反派");
        File[] files = dir.listFiles();
        if (files.length > 0) {
            List<File> fileList = Arrays.asList(files);
            List<File> newFiles = fileList.stream().sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
            for (File file : fileList) {
                System.out.println(file.getName());
            }
            for (File file : newFiles) {
                System.out.println(file.getName());
            }
        }
    }
}

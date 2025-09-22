package com.hm.demo.crawler;


import org.apache.commons.text.StringEscapeUtils;

public class NCREcode {
    public static void main(String[] args) {
        String s = "哈哈&shy;&#x6027;&shy;";
        System.out.println(StringEscapeUtils.unescapeHtml3(s));
    }
}

package com.hm.demo.crawler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PicPath implements Serializable {
    private static final long serialVersionUID = 1L;
    private int price;
    private int gold;
    private String num;
    private String url;
    private String name;
    private String stime;
    private String img;
    private String img2;
}

package com.hm.demo.crawler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PicDT implements Serializable {
    private static final long serialVersionUID = 1L;
    private int num;
    private String img;
}

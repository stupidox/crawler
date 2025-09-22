package com.hm.demo.crawler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Hanman implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalPage;
    private List<PicPath> length;
}

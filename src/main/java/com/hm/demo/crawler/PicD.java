package com.hm.demo.crawler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PicD implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<PicDT> list;
}

package com.hjp123.demo.bean;

import lombok.Data;

@Data
public class Dynamic {

    private int qid;
    private String qtitle;
    private String qdescription;
    private long qcreator;
    private int qcomment_count;
    private int qview_count;
    private int qlike_count;
    private long cid;
    private long cparent_id;
    private int ctype;
    private long ccommentator;
    private long gmtCreate;
    private String ccontent;

}

package com.hjp123.demo.dto;

import com.hjp123.demo.bean.Comment;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import lombok.Data;

import java.util.List;

@Data
public class DynamicDTO {

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
    private Comment ccomment;
    private User commentuser;
    private Question commentquestion;
    private User user;

    @Override
    public String toString() {
        return "DynamicDTO{" +
                "qid=" + qid +
                ", qtitle='" + qtitle + '\'' +
                ", qdescription='" + qdescription + '\'' +
                ", qcreator=" + qcreator +
                ", qcomment_count=" + qcomment_count +
                ", qview_count=" + qview_count +
                ", qlike_count=" + qlike_count +
                ", cparent_id=" + cparent_id +
                ", ctype=" + ctype +
                ", ccommentator=" + ccommentator +
                ", gmtCreate=" + gmtCreate +
                ", ccontent='" + ccontent + '\'' +
                ", ccomment=" + ccomment +
                ", user=" + user +
                '}';
    }
}

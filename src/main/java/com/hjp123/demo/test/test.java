package com.hjp123.demo.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class test {

    @Test
    public void test(){
        String a = " ,, ,a,,,    ,,bc,d行家[][[[+ व = त्व，ण + ढ = ण्ढ，स + थ = स्थ。}},.';;]],.//??!!,,dd,,,ef,,";

        ArrayList endArray = new ArrayList<String>();

        if (StringUtils.isNotBlank(a)) {
            //将用户输入的中文逗号转换为英文逗号
            String replace = StringUtils.replace(a, "，", ",");
            //去除首尾空格
            String deleteWhitespace = StringUtils.deleteWhitespace(replace);

            String replaceAll = deleteWhitespace.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5,，]+", "");

            for (int i = 0;i<replaceAll.length();i++){
                endArray.add(replaceAll.charAt(i));
            }

            ListIterator<String> iterator = endArray.listIterator();

            Object next1 = null;
            Object next2 = null;
            while (iterator.hasNext()){
                next2 = iterator.next();
                if (next1 != null){
                    if (next1.equals(next2)){
                        if(next2.toString().contains(",")) {
                            iterator.remove();
                        }
                    }else {
                        next1 = next2;
                    }
                }else {
                    next1 = next2;
                }
            }
            String listString = (String) endArray.stream().map(Object::toString)
                    .collect(Collectors.joining(""));
            //移除开头标点
            String start = StringUtils.removeStart(listString, ",");
            //移除结尾标点
            String end = StringUtils.removeEnd(start, ",");
            System.out.println(a);
            System.out.println(end);
        }else {

        }

    }

}

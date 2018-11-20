package com.example.dictionary;

import org.litepal.crud.DataSupport;

/*
定义Word类
 */
public class Word extends DataSupport{
    private String EnW;//英文
    private String ChW;//中文
    public Word(String EnW,String ChW){
        this.EnW=EnW;
        this.ChW=ChW;
    }

    public void setEnW(String EnW){
        this.EnW=EnW;
    }

    public void setChW(String ChW){
        this.ChW=ChW;
    }

    public String getEnW(){
        return EnW;
    }

    public String getChW(){
        return ChW;
    }

    public String toString(){
        return EnW+ChW;
    }
}

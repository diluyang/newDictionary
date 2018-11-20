package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
用百度翻译查阅词典中某个词的定义
 */
public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        //获取单词
        Intent intent=getIntent();
        String data=intent.getStringExtra("word");

        //用webview显示返回的结果
        WebView webView=findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://fanyi.baidu.com/?aldtype=85#en/zh/"+data);
    }
}

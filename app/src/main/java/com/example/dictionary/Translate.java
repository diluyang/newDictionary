package com.example.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
用于在线翻译
 */
public class Translate extends AppCompatActivity {

    EditText editText;//编辑要翻译的内容
    TextView textView;//显示翻译的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);

        editText=findViewById(R.id.en_text);
        editText.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView=findViewById(R.id.ch_text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        //翻译单词
        Button word_button=findViewById(R.id.word_button);
        word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWordRequest();
            }
        });

        //翻译句子
        Button sentence_button=findViewById(R.id.sentence_button);
        sentence_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSentenceRequest();
            }
        });
    }

    /*
    发送请求（单词）
     */
    public void sendWordRequest(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
//                            .url("http://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#en/zh/"+editText.getText())
//                            .url("https://translate.google.cn/#en/zh-CN/"+editText.getText())
                            .url("http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w="+editText.getText().toString())
                            .build();

                    Response response = client.newCall(request).execute();//获取返回的数据
                    String responseData = response.body().string();

                    parseWithJSON(responseData);//解析返回的数据
//                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    发送请求（句子）
     */
    public void sendSentenceRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String[] split=editText.getText().toString().split(" ");
                    StringBuilder sentence=new StringBuilder();
                    sentence.append(split[0]);
                    for(int i=1;i<split.length;i++){
                        sentence.append("%20"+split[i]);
                    }
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
//                            .url("http://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#en/zh/"+editText.getText())
//                            .url("https://translate.google.cn/#en/zh-CN/"+editText.getText())
                            .url("http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w="+sentence)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseWithJSON(responseData);
//                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    用json解析返回的数据
     */
    private void parseWithJSON(String jsonData){
        try{
            JSONObject return_data=new JSONObject(jsonData);//转化成json数据格式
            JSONObject content=return_data.getJSONObject("content");
            StringBuilder result=new StringBuilder();
            if(isAEnglishWord()){
                JSONArray mean=content.getJSONArray("word_mean");//json数组
                for(int i=0;i<mean.length();i++){
                    String kind=mean.getString(i);
                    StringBuilder builder=new StringBuilder(kind);
                    result.append("\n"+builder);//获得的数据添加到下一行
////                Log.d("试验",kind);
                }
            }else{
                String out=content.getString("out");
                StringBuilder builder=new StringBuilder(out);
                result.append(builder);
            }
            textView.setText(result);//显示结果
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    判断是否是单词
     */
    public boolean isAEnglishWord(){
        String word=editText.getText().toString();
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);//获取字母
            int value=Integer.valueOf(c);//获取该字母的ascii码

            //判断是否在a—z或A—Z的范围内
            if(!(value>=65&&value<=90||value>=97&&value<=122)){
                return false;
            }
        }
        return true;
    }

    //显示直接返回的数据内容（没用）
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }
}

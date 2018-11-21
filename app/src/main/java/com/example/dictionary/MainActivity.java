package com.example.dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    List<Word> wordList=new ArrayList<>();
    WordAdapter wordAdapter;
    Word newWord;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout drawerLayout;
//    View include;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //标题栏
        Toolbar toolbar=findViewById(R.id.toolbar);
        LitePal.getDatabase();
        setSupportActionBar(toolbar);

        //初始化词典
        initWord();
//        Log.d("顺序","列表前");
//        showLog();

        //RecyclerView设置
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wordAdapter=new WordAdapter(wordList);
        recyclerView.setAdapter(wordAdapter);

        //侧滑布局
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.drawer);
        }

        //保存数据
        SharedPreferences load=getSharedPreferences("data",MODE_PRIVATE);
        Set<String> temp=new HashSet<>();
//        temp.add("");
        Set<String> load_data=load.getStringSet("save_data",temp);
        Iterator<String> iter=load_data.iterator();

        if(iter.hasNext()){
            wordList.clear();
        }
        while (iter.hasNext()){
            String[] split=iter.next().split(" ");
            Word word=new Word(split[0],split[1]);
            wordList.add(word);
        }


    }


    /*
    退出时保存数据
     */
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        Set<String> save = new HashSet<>();
        for (Word word : wordList) {
            save.add(word.getEnW() + " " + word.getChW());
        }
        editor.putStringSet("save_data", save);
        editor.apply();
    }

    /*
    添加菜单
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toobar,menu);
        return true;
    }


    /*
    菜单栏选项
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            //添加新的单词
            case R.id.add_new:
                Intent intent=new Intent(this,Add.class);
                startActivityForResult(intent,1);
                break;

                //清空词典
            case R.id.clear:
                wordList.clear();
                wordAdapter.notifyDataSetChanged();
                break;
//
        }
        return true;
    }

    /*
    intent返回的数据
     */
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String[] returnData=data.getStringArrayExtra("return_data");
                    newWord=new Word(returnData[0],returnData[1]);
                    wordList.add(newWord);
                }
        }
    }

    /*
    预设单词
     */
    private void initWord(){
        Word word1=new Word("disgust","厌恶，令人作呕");
        wordList.add(word1);
        Word word2=new Word("motorist","开汽车者");
        wordList.add(word2);
        Word word3=new Word("squash","挤压，压扁，南瓜");
        wordList.add(word3);
        Word word4=new Word("compulsory","被强制的，义务的");
        wordList.add(word4);
        Word word5=new Word("corporate","合伙的，公司的");
        wordList.add(word5);
        Word word6=new Word("revenue","收入，税收，税务局");
        wordList.add(word6);
        Word word7=new Word("recreation","娱乐，再创造");
        wordList.add(word7);
        Word word8=new Word("denote","指示，表示");
        wordList.add(word8);
        Word word9=new Word("crumble","崩溃，面包屑");
        wordList.add(word9);
//        Word word10=new Word("doctrine","教条，学说");
//        wordList.add(word10);
//        Word word11=new Word("traverse","越过，反驳，来回移动");
//        wordList.add(word11);
//        Word word12=new Word("expedition","远征，探险队，迅速");
//        wordList.add(word12);
//        Word word13=new Word("vain","空虚的，自负的，愚蠢的");
//        wordList.add(word13);
//        Word word14=new Word("cultivation","教化，培养，耕作");
//        wordList.add(word14);
//        Word word15=new Word("statute","法规");
//        wordList.add(word15);
//        Word word16=new Word("elegant","优雅的");
//        wordList.add(word16);
//        Word word17=new Word("apprentice","学徒，生手");
//        wordList.add(word17);
//        Word word18=new Word("plateau","（上升后的）稳定水平，高原");
//        wordList.add(word18);
//        Word word19=new Word("competent","能干的，胜任的，有效的");
//        wordList.add(word19);
//        Word word20=new Word("petition","申请，恳求");
//        wordList.add(word20);
//        Word word21=new Word("induction","感应，入门，归纳法");
//        wordList.add(word21);
//        Word word22=new Word("allergy","反感，过敏");
//        wordList.add(word22);
    }
}


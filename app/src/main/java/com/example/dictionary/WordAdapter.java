package com.example.dictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/*
recyclerview适配器
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private List<Word> list;

    /*
    定义viewholder
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        View wordView;
        TextView EnWView;
        TextView ChWView;
        View include;
        TextView wordDelete;
        TextView wordEdit;
        View editLinear;
        EditText changeEn;
        EditText changeCh;
        Button changeButton;
        private ViewHolder(View v){
            super(v);
            wordView=v;
            EnWView=v.findViewById(R.id.EnW);
            ChWView=v.findViewById(R.id.ChW);
            include=v.findViewById(R.id.popLay);
            wordDelete=v.findViewById(R.id.word_delete);
            wordEdit=v.findViewById(R.id.word_edit);
            editLinear=v.findViewById(R.id.edit_linear);
            changeEn=v.findViewById(R.id.change_En);
            changeCh=v.findViewById(R.id.change_Ch);
            changeButton=v.findViewById(R.id.change_button);
        }
    }

    /*
    创建viewholder
     */
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //把子控件填充到当前布局
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.ChWView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
////                View contentView= LayoutInflater.from(v.getContext()).inflate(R.layout.popup_layout,null);
////                PopupWindow popupWindow=new PopupWindow(contentView,140,220);
////                popupWindow.setFocusable(true);
////                popupWindow.setTouchable(true);
////                popupWindow.setOutsideTouchable(true);
////                popupWindow.showAsDropDown(v,0,0);
//                Log.d("顺序","PopupWindow");

                //显示隐藏的控件
                if(viewHolder.include.getVisibility()==View.GONE){
                    viewHolder.include.setVisibility(View.VISIBLE);

                    //删除单词
                    viewHolder.wordDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alert=new AlertDialog.Builder(v.getContext());
                            alert.setTitle("将要删除这条单词");
                            alert.setMessage("是否删除");
                            alert.setCancelable(true);
                            alert.setPositiveButton("忍痛割爱", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int pos=viewHolder.getLayoutPosition();
                                    list.remove(pos);
                                    notifyItemRemoved(pos);
                                }
                            });
                            alert.setNegativeButton("还想再活五百年", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                            alert.show();
                        }
                    });
                    //编辑单词
                    viewHolder.wordEdit.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view){
                            viewHolder.editLinear.setVisibility(View.VISIBLE);
                            viewHolder.changeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Word newWord=new Word(viewHolder.changeEn.getText().toString(),viewHolder.changeCh.getText().toString());
                                    int pos=viewHolder.getLayoutPosition();
                                    list.remove(pos);
                                    list.add(pos, newWord);
                                    notifyItemRemoved(pos);
                                    notifyItemInserted(pos);
//                                   notifyItemChanged(pos,newWord);
                                    viewHolder.editLinear.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }else if(viewHolder.include.getVisibility()==View.VISIBLE){
                    viewHolder.include.setVisibility(View.GONE);//再次点击则隐藏控件
                }
            }
        });

        //用百度翻译查阅词典中某个词的定义
        viewHolder.EnWView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=viewHolder.EnWView.getText().toString();
                Intent intent=new Intent(view.getContext(),WebActivity.class);
                intent.putExtra("word",data);
                view.getContext().startActivity(intent);
            }
        });
//        Log.d("顺序","onCreateViewHolder");
        return viewHolder;
    }

    /*
    对子项的进行赋值
     */
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        Word word=list.get(position);
        viewHolder.EnWView.setText(word.getEnW());
        viewHolder.ChWView.setText(word.getChW());
    }

    /*
    构造函数
     */
    public WordAdapter(List<Word> list){
        this.list=list;
//        Log.d("顺序","构造函数");
    }

    /*
    返回当前项的列表长度
     */
    public int getItemCount(){
        return list.size();
    }
}

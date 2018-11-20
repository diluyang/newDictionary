package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
添加一个单词
 */
public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText EEi=findViewById(R.id.EEi);
        final EditText CEi=findViewById(R.id.CEi);
        Button add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                String[] returnData=new String[]{EEi.getText().toString(),CEi.getText().toString()};
                intent.putExtra("return_data",returnData);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

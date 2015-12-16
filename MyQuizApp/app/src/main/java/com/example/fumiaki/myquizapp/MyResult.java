package com.example.fumiaki.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MyResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_result);

        TextView resultText = (TextView) findViewById(R.id.resultText);

        // 前の画面から渡されたスコアデータを取得
        Intent intent = getIntent();
        String score = intent.getStringExtra(MainActivity.EXTRA_MYSCORE);

        resultText.setText(score);
    }

    public void goBack(View view) {
        // 元画面に戻る
        finish();
    }

}

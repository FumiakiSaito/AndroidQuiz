package com.example.fumiaki.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // 次の画面へ渡すスコアデータ名
    public final static String EXTRA_MYSCORE = "com.example.fumiaki.myquizapp.EXTRA_MYSCORE";

    // クイズ設定
    private ArrayList<String[]> quizSet = new ArrayList<String[]>();

    private TextView scoreText;
    private TextView qText;
    private Button a0Button;
    private Button a1Button;
    private Button a2Button;
    private Button nextButton;

    private int currentQuiz = 0;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadQuizSet();

        getViews();

        setQuiz();
    }

    /**
     * スコア表示
     */
    private void showScore() {
        scoreText.setText("Score! " + score + " / " + quizSet.size());
    }

    /**
     * クイズデータを設定
     */
    private void setQuiz() {
        qText.setText(quizSet.get(currentQuiz)[0]);

        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            answers.add(quizSet.get(currentQuiz)[i]);
        }
        Collections.shuffle(answers);

        a0Button.setText(answers.get(0));
        a1Button.setText(answers.get(1));
        a2Button.setText(answers.get(2));

        a0Button.setEnabled(true);
        a1Button.setEnabled(true);
        a2Button.setEnabled(true);
        nextButton.setEnabled(false);

        showScore();
    }

    /**
     * 回答チェック
     * @param view
     */
    public void checkAnswer(View view) {
        Button clickedButton = (Button) view;
        String clickedAnswer = clickedButton.getText().toString();

        if (clickedAnswer.equals(quizSet.get(currentQuiz)[1])) {
            clickedButton.setText("◯ " + clickedAnswer);
            score++;
        } else {
            clickedButton.setText("☓ " + clickedAnswer);
        }
        showScore();

        a0Button.setEnabled(false);
        a1Button.setEnabled(false);
        a2Button.setEnabled(false);
        nextButton.setEnabled(true);

        currentQuiz++;

        if (currentQuiz == quizSet.size()) {
            nextButton.setText("Check Result");
        }
    }

    /**
     * 次の画面へ
     * @param view
     */
    public void goNext(View view) {
        if (currentQuiz == quizSet.size()) {
            // 結果画面へ
            Intent intent = new Intent(this, MyResult.class);
            intent.putExtra(EXTRA_MYSCORE, score + " / " + quizSet.size()); // 次の画面へ渡すデータ
            startActivity(intent);
        } else {
            setQuiz();
        }
    }

    /**
     * アクティヴィティがアクティブになったときに呼ばれる処理
     */
    @Override
    public void onResume() {
        super.onResume();
        nextButton.setText("Next");
        currentQuiz = 0;
        score = 0;
        setQuiz();
    }

    /**
     * View取得
     */
    private void getViews() {
        scoreText = (TextView) findViewById(R.id.scoreText);
        qText = (TextView) findViewById(R.id.qText);
        a0Button = (Button) findViewById(R.id.a0Button);
        a1Button = (Button) findViewById(R.id.a1Button);
        a2Button = (Button) findViewById(R.id.a2Button);
        nextButton = (Button) findViewById(R.id.nextButton);
    }

    /**
     * クイズデータをテキストファイルから取得し設定
     */
    private void loadQuizSet() {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = getAssets().open("quiz.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                quizSet.add(s.split("\t"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
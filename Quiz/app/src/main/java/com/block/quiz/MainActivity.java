package com.block.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtQuestion;
    ProgressBar quizPB;
    TextView txtStats;
    Button btnTrue;
    Button btnFalse;

    QuizModel[] questionArray = new QuizModel[]{
            new QuizModel(R.string.q1, true), // [0]
            new QuizModel(R.string.q2, false), // [1]
            new QuizModel(R.string.q3, true),  // [2]
            new QuizModel(R.string.q4, false), // [3]
            new QuizModel(R.string.q5, true),  // [4]
            new QuizModel(R.string.q6, false),  // [5]
            new QuizModel(R.string.q7, true),  // [6]
            new QuizModel(R.string.q8, false),  // [7]
            new QuizModel(R.string.q9, true),  // [8]
            new QuizModel(R.string.q10, false)  // [9]
    };                                                    //  [12]

    int questionIndex = 0;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            score = savedInstanceState.getInt("SCORE");
            questionIndex = savedInstanceState.getInt("INDEX");
        }else{
            score = 0;
            questionIndex = 0;
        }

        txtQuestion = findViewById(R.id.txtQuestion);
        quizPB = findViewById(R.id.quizPB);
        txtStats = findViewById(R.id.txtStats);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // btnTrue 눌렀을때,
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(true);

                questionIndex = (questionIndex + 1 ) % 10 ;
                // 인덱스에는 10 이상은 올수가 없다.
                Log.i("MyQuiz", "questionIndex : " + questionIndex);
                if(questionIndex == 0){
                    // 알러트 다이얼로그를 이용하려면, AlertDialog.Builder 를 객체 생성.
                    AlertDialog.Builder quizAlert = new AlertDialog.Builder(MainActivity.this);
                    quizAlert.setTitle("퀴즈 앱 종료");
                    quizAlert.setMessage("당신의 점수는 : " + score);
                    quizAlert.setPositiveButton("앱 종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 앱을 종료시키는 코드
                            finish();
                        }
                    });
                    quizAlert.show();
                    return;
                }
                QuizModel q = questionArray[questionIndex];
                txtQuestion.setText(q.getmQuestion());
            }
        });
        // btnFalse 눌렀을때,
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(false);

                questionIndex = (questionIndex + 1 ) % 10;
                Log.i("MyQuiz", "questionIndex : " + questionIndex);
                if(questionIndex == 0){
                    AlertDialog.Builder quizAlert = new AlertDialog.Builder(MainActivity.this);
                    quizAlert.setTitle("퀴즈 앱 종료");
                    quizAlert.setMessage("당신의 점수는 : " + score);
                    quizAlert.setPositiveButton("앱 종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    quizAlert.show();
                    return;
                }
                QuizModel q = questionArray[questionIndex];
                txtQuestion.setText(q.getmQuestion());
            }
        });

        QuizModel q = questionArray[questionIndex];
        txtQuestion.setText(q.getmQuestion());
        txtStats.setText("점수는 : "+score);
        Log.i("MyQuiz", "onCreate 호출됨");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // "SCORE" 라는 키로, score 멤버변수에 저장된 현재 스코어를 저장.
        outState.putInt("SCORE", score);
        outState.putInt("INDEX", questionIndex);
        Log.i("MyQuiz", "onSaveInstanceState 호출됨.");
    }

    // 유저의 대답을 체크하는 함수 : 토스트로 "정답입니다" , "오답입니다" 를 보여준다.
    void evaluateUserAnswer(boolean userAnswer){
        // 현재 문제의 정답을 가져오는 코드
        QuizModel q = questionArray[questionIndex];
        boolean answer = q.getAnswer();

        // 유저의 대답과, 현재 정답을 비교하여, 토스트 하는 코드
        if(userAnswer == answer){
            score = score + 1;
            Toast.makeText(MainActivity.this, "정답입니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "오답입니다.", Toast.LENGTH_SHORT).show();
        }
        txtStats.setText("점수는 : "+score);
        quizPB.incrementProgressBy(1);

    }


}









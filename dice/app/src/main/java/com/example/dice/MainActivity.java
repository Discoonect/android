package com.example.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int num1 = 0;
        int num2 = 0;
        while(true){
            // 1. 주사위를 두번 던집니다.
            num1 = (int)(Math.random() * 6) + 1 ;
            num2 = (int)(Math.random() * 6) + 1 ;
            // 2. 주사위 두개 값을 화면에 (값1, 값2) 출력
            Log.i("aaa",("("+num1+", "+num2+")"));
            // 3. 주사위 두개의 합이 5이면, 이 반복문을 빠져 나가게 코드 작성


            if( (num1+num2) == 5  ) {
                break;
            }
        }
    }
}
package com.example.customdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView main_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 커스텀 다이얼로그에서 입력한 메시지를 출력할 TextView 를 준비한다.
        main_label = findViewById(R.id.main_label);

        // 커스텀 다이얼로그를 호출할 버튼을 정의한다.
        button = findViewById(R.id.button);

        // 커스텀 다이얼로그 호출할 클릭 이벤트 리스너 정의
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                CustomDialog customDialog = new CustomDialog(MainActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                customDialog.callFunction(main_label);
            }
        });
    }
}
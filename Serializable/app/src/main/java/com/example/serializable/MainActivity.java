package com.example.serializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.serializable.model.Person;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    EditText editEmail;
    Button bntSend;
    RadioGroup radioGroup;
    RadioButton radioF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        radioGroup = findViewById(R.id.radioGroup);
        bntSend = findViewById(R.id.btnSend);

        // 처음 실행시, 무조건 여자쪽에 체크 되어 있도록 하는 코드
        radioF = findViewById(R.id.radioF);
        radioF.setChecked(true);

        bntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이름 가져오기
                String name = editName.getText().toString().trim();

                // 이메일 가져오기
                String email = editEmail.getText().toString().trim();

                // 어느 라디오 버튼에 체크 되어있는지, 체크된 버튼의 아이디 가져오기.
                int checkedId = radioGroup.getCheckedRadioButtonId();
                boolean isMale = false;

                if(checkedId == R.id.radioM){
                    isMale = true;
                }else{
                    isMale = false;
                }
                // 객체 생성
                Person person = new Person(name, email, isMale);
                // 위의 객체를, 새로운 액티비티에 전달.
                Intent i = new Intent(MainActivity.this, Second.class);
                i.putExtra("PersonClass", person);
                startActivity(i);

            }
        });

    }
}

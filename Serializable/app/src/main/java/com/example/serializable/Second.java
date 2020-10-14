package com.example.serializable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.serializable.model.Person;

public class Second extends AppCompatActivity {

    TextView txtPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtPerson = findViewById(R.id.txtPerson);

        Person person = (Person) getIntent().getSerializableExtra("PersonClass");

        String info = person.getName() + ", " + person.getEmail() + ", " + person.isMale();

        txtPerson.setText(info);

    }
}
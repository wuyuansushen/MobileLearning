package com.wwl.mobilelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTransactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transactions);

        Intent intent= getIntent();

        EditText descriptionText=findViewById(R.id.name);
        //是编辑操作进入还是新建操作进入
        String description=intent.getStringExtra("name");
        if(description!=null){
        }

        Button cancelButton=this.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTransactions.this.finish();
            }
        });
    }
}
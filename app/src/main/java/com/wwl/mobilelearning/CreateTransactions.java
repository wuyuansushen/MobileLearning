package com.wwl.mobilelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateTransactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transactions);

        //当前操作账单的索引
        Intent intent= getIntent();
        int positionIndex=intent.getIntExtra("positionIndex",0);

        //账单类型
        Spinner typeSpinner=findViewById(R.id.transactionType);
        int typeIDFromMain=intent.getIntExtra("typeID",0);
        typeSpinner.setSelection(typeIDFromMain);

        //账单内容
        EditText name=findViewById(R.id.name);
        //是编辑操作进入还是新建操作进入
        String nameFromMain=intent.getStringExtra("name");
        if(nameFromMain!=null){
            name.setText(nameFromMain);
        }

        //消费金额
        EditText price=findViewById(R.id.price);
        Double priceFromMain=intent.getDoubleExtra("price",0.0);
        if(priceFromMain!=0.0)
        {
            price.setText(priceFromMain.toString());
        }
        else
        {
        }

        //确定按钮
        Button buttonOK=this.findViewById(R.id.ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMain=new Intent();
                returnToMain.putExtra("positionIndex",positionIndex);
                returnToMain.putExtra("typeID",(int)typeSpinner.getSelectedItemId());
                returnToMain.putExtra("name",name.getText().toString());
                returnToMain.putExtra("price",Double.valueOf(price.getText().toString()));
                setResult(RESULT_OK,returnToMain);
                CreateTransactions.this.finish();
            }
        });

        //取消按钮
        Button cancelButton=findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                CreateTransactions.this.finish();
            }
        });
    }
}
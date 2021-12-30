package com.wwl.mobilelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Account account;
    public List<Transaction> transactionList=new ArrayList<Transaction>();
    public Double asset,income,expence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView=getWindow().getDecorView().getRootView();
        InitialRecyclerView(rootView);

        FloatingActionButton createTransactionButton=this.findViewById(R.id.floatingActionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateTransactions.class);

                //未完成
                startActivity(intent);
            }
        });
    }

    public void InitialRecyclerView(View view)
    {
        account=new Account(this.getApplicationContext());
        transactionList=account.loadAccount();
    }

    public void UpdateAsset(View view){
        asset=0.0;
        income=0.0;
        expence=0.0;

        for(int i=0;i<transactionList.size();i++)
        {

        }
    }
}
package com.wwl.mobilelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        InitialData(rootView);
        UpdateAsset(rootView);

        RecyclerView transactionRecyclerView=this.findViewById(R.id.transactionRecycler);

        FloatingActionButton createTransactionButton=this.findViewById(R.id.floatingActionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateTransactions.class);
                intent.putExtra("positionIndex",transactionList.size());
                startActivity(intent);
            }
        });
    }

    public void InitialData(View view)
    {
        account=new Account(this.getApplicationContext());
        transactionList=account.loadAccount();
    }

    public void UpdateAsset(View view){
        asset=0.0;
        income=0.0;
        expence=0.0;

        TextView assetView=(TextView)view.findViewById(R.id.asset);
        TextView incomeView=(TextView) view.findViewById(R.id.income);
        TextView expenceView=(TextView) view.findViewById(R.id.expence);

        for(int i=0;i<transactionList.size();i++)
        {
            Double price=transactionList.get(i).getPrice();
            if(price>0)
                income=income+price;
            else
                expence=expence+price;
        }

        asset=income+expence;

        assetView.setText(Double.toString(asset));
        incomeView.setText(Double.toString(income));
        expenceView.setText(Double.toString(expence));
    }
}
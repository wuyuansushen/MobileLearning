package com.wwl.mobilelearning;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Account account;
    private TransRecycleViewAdapter transRecycleViewAdapter;

    public Double asset, income, expence;
    public List<Transaction> transactionList = new ArrayList<Transaction>();


    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_OK){
                if(data==null)
                    return;
                int positionIndex = data.getIntExtra("positionIndex",transactionList.size());
                int typeID = data.getIntExtra("typeID",0);
                String name = data.getStringExtra("name");
                Double price = data.getDoubleExtra("price",0);
                transactionList.add(positionIndex,new Transaction(typeID,name,price));
                account.saveAccount();  //保存数据
                UpdateAsset(getWindow().getDecorView().getRootView());
                transRecycleViewAdapter.notifyItemInserted(positionIndex);
            }
        }
    });

    ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_OK){
                if(data==null)
                    return;
                int positionIndex = data.getIntExtra("positionIndex",transactionList.size());
                int typeID = data.getIntExtra("typeID",0);
                String name = data.getStringExtra("name");
                Double price = data.getDoubleExtra("price",0);
                transactionList.get(positionIndex).setTypeID(typeID);
                transactionList.get(positionIndex).setName(name);
                transactionList.get(positionIndex).setPrice(price);
                account.saveAccount();  //保存数据
                UpdateAsset(getWindow().getDecorView().getRootView());
                transRecycleViewAdapter.notifyItemChanged(positionIndex);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView = getWindow().getDecorView().getRootView();
        InitialData(rootView);
        UpdateAsset(rootView);

        RecyclerView transactionRecyclerView = this.findViewById(R.id.transactionRecycler);
        LinearLayoutManager spinnerLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        transactionRecyclerView.setLayoutManager(spinnerLayoutManager);
        //数据填充
        transRecycleViewAdapter = new TransRecycleViewAdapter(transactionList);
        transactionRecyclerView.setAdapter(transRecycleViewAdapter);

        FloatingActionButton createTransactionButton = this.findViewById(R.id.floatingActionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTransactions.class);
                intent.putExtra("positionIndex", 0);
                launcherAdd.launch(intent);
            }
        });
    }

    public void InitialData(View view) {
        account = new Account(this.getApplicationContext());
        transactionList = account.loadAccount();
    }

    public void UpdateAsset(View view) {
        asset = 0.0;
        income = 0.0;
        expence = 0.0;

        TextView assetView = (TextView) view.findViewById(R.id.asset);
        TextView incomeView = (TextView) view.findViewById(R.id.income);
        TextView expenceView = (TextView) view.findViewById(R.id.expence);

        for (int i = 0; i < transactionList.size(); i++) {
            Double price = transactionList.get(i).getPrice();
            if (price > 0)
                income = income + price;
            else
                expence = expence + price;
        }

        asset = income + expence;

        assetView.setText(Double.toString(asset));
        incomeView.setText(Double.toString(income));
        expenceView.setText(Double.toString(expence));
    }


    public class TransRecycleViewAdapter extends RecyclerView.Adapter<TransRecycleViewAdapter.TransViewHolder> {
        private List<Transaction> transactionList;

        public TransRecycleViewAdapter(List<Transaction> transactionList) {
            this.transactionList = transactionList;
        }

        @Override
        public int getItemCount() {
            return transactionList.size();
        }

        @NonNull
        @Override
        public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_treansaction_holder,parent,false);
            TransViewHolder transHolder = new TransViewHolder(holderView);
            return transHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TransViewHolder holder, int position) {
            Transaction transaction=(Transaction) transactionList.get(position);
            String[] typeText = {"交通出行","外出就餐","生活杂物","休闲/购物","医疗健康","教育支出"};
            holder.typeView.setText(typeText[transaction.getTypeID()]);
            holder.nameView.setText(transaction.getName());
            holder.priceView.setText(Double.toString(transaction.getPrice()));
        }
        public class TransViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            private final TextView typeView;
            private final TextView nameView;
            private final TextView priceView;
            //public static final int MENU_ADD=1;
            public static final int MENU_EDIT = 2;
            public static final int MENU_DELETE = 3;

            public TransViewHolder(View view) {
                super(view);
                typeView = (TextView) view.findViewById(R.id.typeView);
                nameView = (TextView) view.findViewById(R.id.nameView);
                priceView = (TextView) view.findViewById(R.id.priceView);
                view.setOnCreateContextMenuListener(this::onCreateContextMenu);
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //MenuItem addTab=contextMenu.add(Menu.NONE,MENU_ADD,MENU_ADD,"新增")
                MenuItem editTab = contextMenu.add(Menu.NONE, MENU_EDIT, MENU_EDIT, "修改");
                MenuItem deleteTab = contextMenu.add(Menu.NONE, MENU_DELETE, MENU_DELETE, "删除");

                editTab.setOnMenuItemClickListener(this::onMenuItemClick);
                deleteTab.setOnMenuItemClickListener(this::onMenuItemClick);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int positionIndex = getAdapterPosition();
                Intent intent;
                switch (menuItem.getItemId()) {
                    case MENU_EDIT:
                        intent = new Intent(MainActivity.this, CreateTransactions.class);
                        intent.putExtra("positionIndex",positionIndex);
                        intent.putExtra("typeID",transactionList.get(positionIndex).getTypeID());
                        intent.putExtra("name",transactionList.get(positionIndex).getName());
                        intent.putExtra("price",transactionList.get(positionIndex).getPrice());
                        launcherEdit.launch(intent);
                        break;
                    case MENU_DELETE:
                        transactionList.remove(positionIndex);
                        account.saveAccount();
                        UpdateAsset(getWindow().getDecorView().getRootView());
                        TransRecycleViewAdapter.this.notifyItemRemoved(positionIndex);
                        break;
                }
                return false;
            }
        }

    }
}
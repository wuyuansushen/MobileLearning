package com.wwl.mobilelearning;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;

public class Account {
    public static final String StorageFileName="Account";
    private final Context context;
    public List<Transaction> TransactionList;

    public Account(Context context) {
        this.context=context;
    }

    public List<Transaction> loadAccount() {
        TransactionList = new ArrayList<Transaction>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(StorageFileName));
            TransactionList=(ArrayList<Transaction>)objectInputStream.readObject();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return TransactionList;
    }

    public void saveAccount() {
        ObjectOutputStream objectOutputStream = null;
        try {
            //完全覆盖写入
            objectOutputStream=new ObjectOutputStream(context.openFileOutput(StorageFileName,Context.MODE_PRIVATE));
            objectOutputStream.writeObject(TransactionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(objectOutputStream!=null)
                {
                    objectOutputStream.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

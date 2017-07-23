package d1marine.com.d1chat.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;

import d1marine.com.d1chat.Data.DatabaseHelper;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Fragment.AddProfilepicFragment;
import d1marine.com.d1chat.Fragment.AddStatusFirst;
import d1marine.com.d1chat.Fragment.AddStatusFragment;
import d1marine.com.d1chat.Fragment.ChatFragment;
import d1marine.com.d1chat.Fragment.LoginFragment;
import d1marine.com.d1chat.Interface.FragmentListenerCallBack;
import d1marine.com.d1chat.R;

public class MainActivity extends ActionBarActivity implements FragmentListenerCallBack, View.OnClickListener {

    //MD5: 15:EE:AD:E6:10:37:AA:DF:52:52:EA:93:1C:E0:1C:2B
    //SHA1: 27:46:EB:3A:97:03:A8:E1:AA:E8:91:3B:AF:BA:BD:CD:05:C4:0D:40

    private Toolbar mToolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getData();
        DatabaseQueryHelper.getInstance().deleteAllEntriesFromChatActivityOpenCheckTable();

        if(DatabaseQueryHelper.getInstance().isNewUser()==0){
            loadLogin();
        }else {
            startActivity(new Intent(MainActivity.this,ChatAndContactActivity.class));
        }

       // loadProfilePic();
        //loadStatus();
        //loadStatusFirst();
       // startActivity(new Intent(MainActivity.this,TestActivity.class));

       // startActivity(new Intent(MainActivity.this,ChatAndContactActivity.class));

        DatabaseQueryHelper.getInstance().deleteAllEntriesFromStatusTable();
        DatabaseQueryHelper.getInstance().insertEntryStatus("1","Hey there! I am using D1 Chat");
        DatabaseQueryHelper.getInstance().insertEntryStatus("2","Available");
        DatabaseQueryHelper.getInstance().insertEntryStatus("3","At work");
        DatabaseQueryHelper.getInstance().insertEntryStatus("4","At school");
        DatabaseQueryHelper.getInstance().insertEntryStatus("5","Busy");
        DatabaseQueryHelper.getInstance().insertEntryStatus("6","Battery about to die");
        DatabaseQueryHelper.getInstance().insertEntryStatus("7","Can't talk, chat only");

    }

    private void getData() {
        dbHelper=new DatabaseHelper(this);
        try{
            dbHelper.createDatabase();
        }catch (IOException IOE){
            throw new Error("Unable to create Database");

        }try {
            dbHelper.openDatabase();
            dbHelper.getQueryHelper();
        }catch (SQLiteException sqle){

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            }
        }

    @Override
    public void loadLogin() {
        Fragment mLoginFragment=new LoginFragment();
        FragmentManager mFragmentManager=getFragmentManager();
        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container,mLoginFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void loadProfilePic() {
        Fragment mAddProfilePicFragment=new AddProfilepicFragment();
        FragmentManager mFragmentManager=getFragmentManager();
        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container,mAddProfilePicFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void loadStatus() {
        Fragment mAddStatusFragment=new AddStatusFragment();
        FragmentManager mFragmentManager=getFragmentManager();
        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container,mAddStatusFragment);
        mFragmentTransaction.replace(R.id.container,mAddStatusFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void loadStatusFirst() {
        Fragment mAddStatusFragment=new AddStatusFirst();
        FragmentManager mFragmentManager=getFragmentManager();
        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container,mAddStatusFragment);
        mFragmentTransaction.replace(R.id.container,mAddStatusFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

}
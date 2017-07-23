package d1marine.com.d1chat.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import d1marine.com.d1chat.Chat.LocalBinder;
import d1marine.com.d1chat.Chat.MyService;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Fragment.ChatFragment;
import d1marine.com.d1chat.Fragment.ContactFragment;
import d1marine.com.d1chat.Fragment.UserList;
import d1marine.com.d1chat.R;

public class ChatAndContactActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    Toolbar mToolbar;
    FragmentManager mFragmentManager;
    private MyService mService;
    private boolean mBounded;
    public ViewPager mViewPager;

    private final ServiceConnection mConnection = new ServiceConnection() {

        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name,
                                       final IBinder service) {
            mService = ((LocalBinder<MyService>) service).getService();
            mBounded = true;
             //Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mService = null;
            mBounded = false;
            //Log.d(TAG, "onServiceDisconnected");
        }
    };


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new UserList();
                case 1:
                    return new ChatFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CHATS";
                case 1:
                    return "CONTACTS";


            }
            return null;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_and_contact);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);

        tabLayout = (TabLayout) this.findViewById(R.id.tabs_chat_contact_activity);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager_chat_contact_activity);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        mFragmentManager = getSupportFragmentManager();

        DatabaseQueryHelper.getInstance().deleteAllEntriesFromChatActivityOpenCheckTable();
        DatabaseQueryHelper.getInstance().insertEntryChatActivityOpenCheckTable("0");

        doBindService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    void doBindService() {
        bindService(new Intent(this, MyService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    void doUnbindService() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
    }

    public MyService getmService() {
        return mService;
    }

}

package d1marine.com.d1chat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import d1marine.com.d1chat.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setContentView(R.layout.frame_chat_activity_user_2);
    }
}

package d1marine.com.d1chat.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import d1marine.com.d1chat.Adapter.ChatAdapter;
import d1marine.com.d1chat.R;

public class AddStatusFirstActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView mTextSave;
    ImageView mImageEmoticon;
    Toolbar mToolbar;
    ChatAdapter chatAdapter;
    EmojiPopup emojiPopup;
    EmojiEditText mEditStatus;
    ViewGroup rootView;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiManager.install(new IosEmojiProvider());
        setContentView(R.layout.activity_add_status_first);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);

        mTextSave=(TextView)findViewById(R.id.text_status_first_save);
        mEditStatus=(EmojiEditText)findViewById(R.id.text_status_first);
        rootView = (ViewGroup)findViewById(R.id.status_first_root_view);
        mImageEmoticon=(ImageView)findViewById(R.id.img_emoticons_button_status_first);

        mImageEmoticon.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        //mTextSave.setColorFilter(ContextCompat.getColor(getActivity(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

        mTextSave.setOnClickListener(this);
        mImageEmoticon.setOnClickListener(this);

        setUpEmojiPopup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_status_first_save:{
                Toast.makeText(this,mEditStatus.getText().toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddStatusFirstActivity.this, ChatActivity.class));

                break;
            }
            case R.id.img_emoticons_button_status_first:{
                emojiPopup.toggle();
                break;
            }
        }
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
                .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
                    @Override public void onEmojiBackspaceClicked(final View v) {
                        // Log.d(TAG, "Clicked on Backspace");
                    }
                })
                .setOnEmojiClickedListener(new OnEmojiClickedListener() {
                    @Override public void onEmojiClicked(final Emoji emoji) {
                        //  Log.d(TAG, "Clicked on emoji");
                    }
                })
                .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
                    @Override public void onEmojiPopupShown() {
                        mImageEmoticon.setImageResource(R.drawable.ic_keyboard);
                    }
                })
                .setOnSoftKeyboardOpenListener(new OnSoftKeyboardOpenListener() {
                    @Override public void onKeyboardOpen(final int keyBoardHeight) {
                        //  Log.d(TAG, "Opened soft keyboard");
                    }
                })
                .setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {
                    @Override public void onEmojiPopupDismiss() {
                        mImageEmoticon.setImageResource(R.drawable.emoji_ios_category_people);
                    }
                })
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override public void onKeyboardClose() {
                        // Log.d(TAG, "Closed soft keyboard");
                    }
                })
                .build(mEditStatus);
    }

    @Override
    public void onStop() {
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }

        super.onStop();
    }

    @Override public void onBackPressed() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

}

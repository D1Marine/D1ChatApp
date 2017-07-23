package d1marine.com.d1chat.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import d1marine.com.d1chat.Activity.ChatActivity;

import d1marine.com.d1chat.Adapter.ChatAdapter;
import d1marine.com.d1chat.ParentFragment;
import d1marine.com.d1chat.R;

/**
 * Created by User on 23/5/2017.
 */

public class AddStatusFirst extends ParentFragment implements View.OnClickListener {//////////no need
    TextView mTextSave;
    ImageView mImageEmoticon;
    ChatAdapter chatAdapter;
    EmojiPopup emojiPopup;
    EmojiEditText mEditStatus;
    ViewGroup rootView;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiManager.install(new IosEmojiProvider());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_status_first_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mTextSave=(TextView)getActivity().findViewById(R.id.text_status_first_save);
        mEditStatus=(EmojiEditText) getActivity().findViewById(R.id.text_status_first);
        rootView = (ViewGroup) getActivity().findViewById(R.id.status_first_root_view);
        mImageEmoticon=(ImageView)getActivity().findViewById(R.id.img_emoticons_button_status_first);

        mImageEmoticon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        //mTextSave.setColorFilter(ContextCompat.getColor(getActivity(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

        mTextSave.setOnClickListener(this);
        mImageEmoticon.setOnClickListener(this);

        setUpEmojiPopup();

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_status_first_save:{
                getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
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



}

package d1marine.com.d1chat.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransfer;

import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import d1marine.com.d1chat.Adapter.ChatAdapter;
import d1marine.com.d1chat.Adapter.ChatMessageAdapter;
import d1marine.com.d1chat.Adapter.ChatMessageFromDbAdapter;
import d1marine.com.d1chat.Chat.CommonMethods;
import d1marine.com.d1chat.Chat.MyService;
import d1marine.com.d1chat.Chat.MyXMPP;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Model.ChatModel;
import d1marine.com.d1chat.R;




public class ChatActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Toolbar mToolbar;
    ImageView mImageEmoticon, mImageSendInactive, mImageSendActive;
    ChatAdapter chatAdapter;
    EmojiPopup emojiPopup;
    EmojiEditText mEditMessage;
    ViewGroup rootView;
    public static ListView msgListView;
    TextView mTextToolbarHeader;
    ImageView mImgAttachFile;

    private String user1 = "";
    public static String USER_TWO = "";
    private Random random;
    public static ArrayList<ChatModel> chatlist;
    public static ChatMessageAdapter mChatMessageAdapter;
    public static ChatMessageFromDbAdapter mChatMessageFromDbAdapter;
    public FileTransferManager manager;
public XMPPConnection mConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiManager.install(new IosEmojiProvider());
        setContentView(R.layout.activity_chat);
        // setContentView(R.layout.frame_chat_activity_user_1);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar_chat);

        mEditMessage = (EmojiEditText) findViewById(R.id.chat_activity_edit_text);
        rootView = (ViewGroup) findViewById(R.id.rootview_chat_activity);
        mImageEmoticon = (ImageView) findViewById(R.id.emojiButton);
        mImageSendActive = (ImageView) findViewById(R.id.chat_activity_send_active);
        mImageSendInactive = (ImageView) findViewById(R.id.chat_activity_send_inactive);
        mImageEmoticon.setColorFilter(ContextCompat.getColor(ChatActivity.this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        msgListView = (ListView) findViewById(R.id.chat_activity_list_view);
        mTextToolbarHeader = (TextView) findViewById(R.id.text_head_toolbar_chat);
        mImgAttachFile=(ImageView)findViewById(R.id.img_toolbar_attach_file);

        //mTextSave.setColorFilter(ContextCompat.getColor(getActivity(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        Intent intent = getIntent();
        USER_TWO = intent.getStringExtra("UserName");
        mTextToolbarHeader.setText(USER_TWO);

        // int Count= DatabaseQueryHelper.getInstance().getCountOfUnreadMessages(USER_TWO+"@user-pc");

        DatabaseQueryHelper.getInstance().updateReadStatusInChatMessageTable(USER_TWO + "@user-pc");


        mImageEmoticon.setOnClickListener(this);
        mEditMessage.addTextChangedListener(this);
        mImageSendActive.setOnClickListener(this);
        mImgAttachFile.setOnClickListener(this);

        mImageSendActive.setVisibility(View.GONE);
        mImageSendInactive.setVisibility(View.VISIBLE);

        random = new Random();
        //   user1 = getString(R.string.user1_login);
        user1 = DatabaseQueryHelper.getInstance().getUserNameFromLoginTable();
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<ChatModel>();
        mChatMessageAdapter = new ChatMessageAdapter(ChatActivity.this, chatlist, USER_TWO);
        msgListView.setAdapter(mChatMessageAdapter);

        mChatMessageFromDbAdapter = new ChatMessageFromDbAdapter(ChatActivity.this, DatabaseQueryHelper.getInstance().mMessageFromDbList, USER_TWO);


        setUpEmojiPopup();
        DatabaseQueryHelper.getInstance().updateChatActivityOpenCheckTable("1");
        //DatabaseQueryHelper.getInstance().setMessageFromDbList(USER_TWO+"@user-pc");
        displayList();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }


    public void sendTextMessage(View v) {
        String message = mEditMessage.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
         /*   final ChatModel chatMessage = new ChatModel(DatabaseQueryHelper.getInstance().getUserIdFromLoginTable(),user1, USER_TWO,
                    message, "" + random.nextInt(1000), true);*/
            final ChatModel chatMessage = new ChatModel(user1, USER_TWO,
                    message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            // chatMessage.setMsgid();
            chatMessage.body = message;
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            //chatMessage.setSender(R.string.user1_login+"@user-pc");
            // chatMessage.setSenderId(DatabaseQueryHelper.getInstance().getUserIdFromLoginTable());
            chatMessage.setSender(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable() + "@user-pc");
            chatMessage.setReceiver(USER_TWO);
            // Toast.makeText(ChatActivity.this,"sender : "+DatabaseQueryHelper.getInstance().getUserNameFromLoginTable()+" Receiver : "+USER_TWO,Toast.LENGTH_LONG).show();
            mEditMessage.setText("");
            //  Toast.makeText(ChatActivity.this,chatMessage.getMsgid()+" "+chatMessage.getSender()+" "+chatMessage.getReceiver()+" "+chatMessage.getBody()+" 0 "+chatMessage.getTime(),Toast.LENGTH_LONG).show();
            //  DatabaseQueryHelper.getInstance().insertEntryMessage(chatMessage.getMsgid(),chatMessage.getSender(),chatMessage.getReceiver(),chatMessage.getBody(),0,chatMessage.getTime());

           // mChatMessageAdapter.add(chatMessage);
          //  mChatMessageAdapter.notifyDataSetChanged();
            ChatAndContactActivity mactivity = new ChatAndContactActivity();
            mactivity.getmService().xmpp.sendMessage(chatMessage);

        }
    }


    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
                .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
                    @Override
                    public void onEmojiBackspaceClicked(final View v) {
                        // Log.d(TAG, "Clicked on Backspace");
                    }
                })
                .setOnEmojiClickedListener(new OnEmojiClickedListener() {
                    @Override
                    public void onEmojiClicked(final Emoji emoji) {
                        //  Log.d(TAG, "Clicked on emoji");
                    }
                })
                .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
                    @Override
                    public void onEmojiPopupShown() {
                        mImageEmoticon.setImageResource(R.drawable.ic_keyboard);
                    }
                })
                .setOnSoftKeyboardOpenListener(new OnSoftKeyboardOpenListener() {
                    @Override
                    public void onKeyboardOpen(final int keyBoardHeight) {
                        //  Log.d(TAG, "Opened soft keyboard");
                    }
                })
                .setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {
                    @Override
                    public void onEmojiPopupDismiss() {
                        mImageEmoticon.setImageResource(R.drawable.emoji_ios_category_people);
                    }
                })
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override
                    public void onKeyboardClose() {
                        // Log.d(TAG, "Closed soft keyboard");
                    }
                })
                .build(mEditMessage);
    }

    @Override
    public void onStop() {
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }

        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mImageSendActive.setVisibility(View.GONE);
        mImageSendInactive.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditMessage.getText().length() > 0) {
            mImageSendActive.setVisibility(View.VISIBLE);
            mImageSendInactive.setVisibility(View.GONE);
        } else if (mEditMessage.getText().length() == 0) {
            mImageSendActive.setVisibility(View.GONE);
            mImageSendInactive.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.emojiButton: {
                emojiPopup.toggle();
                break;
            }

            case R.id.chat_activity_send_active: {
                sendTextMessage(v);
                break;

            }
            case R.id.img_toolbar_attach_file:{
                attachFile();

                break;
            }
        }
    }

    private void attachFile() {
        
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(USER_TWO +"@user-pc");
        File file = new File(String.valueOf(R.drawable.add_camera_pic));
        try {
            transfer.sendFile(file, "add_camera_pic");

        } catch (SmackException e) {
            e.printStackTrace();
        }
        while(!transfer.isDone()) {
            if(transfer.getStatus().equals(FileTransfer.Status.error)) {
                System.out.println("ERROR!!! " + transfer.getError());
            } else if (transfer.getStatus().equals(FileTransfer.Status.cancelled)
                    || transfer.getStatus().equals(FileTransfer.Status.refused)) {
                System.out.println("Cancelled!!! " + transfer.getError());
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(transfer.getStatus().equals(FileTransfer.Status.refused) || transfer.getStatus().equals(FileTransfer.Status.error)
                || transfer.getStatus().equals(FileTransfer.Status.cancelled)){
            System.out.println("refused cancelled error " + transfer.getError());
        } else {
            System.out.println("Success");
        }
    }

    public void displayList() {
        try {
            DatabaseQueryHelper.getInstance().setMessageFromDbList(USER_TWO + "@user-pc");
            if (DatabaseQueryHelper.getInstance().mMessageFromDbList != null && DatabaseQueryHelper.getInstance().mMessageFromDbList.size() > 0) {
                Collections.sort(DatabaseQueryHelper.getInstance().mMessageFromDbList);
                mChatMessageFromDbAdapter = new ChatMessageFromDbAdapter(ChatActivity.this, DatabaseQueryHelper.getInstance().mMessageFromDbList, USER_TWO);
                if (msgListView == null) {

                    ChatActivity.msgListView.setAdapter(mChatMessageFromDbAdapter);
                } else {
                    msgListView.setAdapter(mChatMessageFromDbAdapter);
                }
                mChatMessageFromDbAdapter.notifyDataSetChanged();
                //recreate();

            } else {
                msgListView.setAdapter(null);
            }
        }catch (Exception e){
            Toast.makeText(ChatActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }


    public void displayListFromXmpp() {
        try {
            DatabaseQueryHelper.getInstance().setMessageFromDbList(USER_TWO + "@user-pc");
            if (DatabaseQueryHelper.getInstance().mMessageFromDbList != null && DatabaseQueryHelper.getInstance().mMessageFromDbList.size() > 0) {
                Collections.sort(DatabaseQueryHelper.getInstance().mMessageFromDbList);
               // recreate();

      //          mChatMessageFromDbAdapter = new ChatMessageFromDbAdapter(ChatActivity.this, DatabaseQueryHelper.getInstance().mMessageFromDbList, USER_TWO);
                if (msgListView == null) {
                    ChatActivity.msgListView.setAdapter(ChatActivity.mChatMessageFromDbAdapter);
                } else {
                    msgListView.setAdapter(ChatActivity.mChatMessageFromDbAdapter);
                }
                ChatActivity.mChatMessageFromDbAdapter.notifyDataSetChanged();
                //recreate();
            } else {
               // msgListView.setAdapter(null);
            }
        }catch (Exception e){
            Toast.makeText(ChatActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {
        DatabaseQueryHelper.getInstance().updateChatActivityOpenCheckTable("0");
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}

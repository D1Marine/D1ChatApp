package d1marine.com.d1chat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vanniktech.emoji.EmojiTextView;

import java.util.ArrayList;

import d1marine.com.d1chat.Activity.ChatActivity;
import d1marine.com.d1chat.Model.ChatModel;
import d1marine.com.d1chat.R;

/**
 * Created by User on 29/5/2017.
 */

public class ChatMessageAdapter extends BaseAdapter  {

    private static LayoutInflater inflater = null;
    ArrayList<ChatModel> chatMessageList;
    private Activity activity;
     String mSelectedUser="";

    public ChatMessageAdapter(Activity activity, ArrayList<ChatModel> list, String selected_user) {
        chatMessageList = list;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        mSelectedUser=selected_user;
    }
    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatModel message = (ChatModel) chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble, null);

        EmojiTextView msg = (EmojiTextView) vi.findViewById(R.id.message_text);
        TextView mTime=(TextView)vi.findViewById(R.id.time_text);
        msg.setText(message.body);
        mTime.setText(message.getTime());
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        String mSender=message.getSender();
        String regex = "\\s*\\b/Smack\\b\\s*";
        mSender = mSender.replaceAll(regex, "");


        // if message is mine then align to right
        if (message.isMine) {
            parent_layout.setVisibility(View.VISIBLE);
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else  if(mSender.equalsIgnoreCase(mSelectedUser+"@user-pc")){//if(mSender.equalsIgnoreCase(mSelectedUser+"@user-pc"))
           /* msg.setText(message.body);
            mTime.setText(message.getTime());*/
            parent_layout.setVisibility(View.VISIBLE);
            Toast.makeText(activity,mSender,Toast.LENGTH_SHORT).show();
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }else{
            parent_layout.setVisibility(View.GONE);
        }

        msg.setTextColor(Color.BLACK);
        return vi;
    }
    public void add(ChatModel object) {
        chatMessageList.add(object);
    }
}

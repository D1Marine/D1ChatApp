package d1marine.com.d1chat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.vanniktech.emoji.EmojiTextView;

import java.util.ArrayList;

import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Model.ChatModel;
import d1marine.com.d1chat.Model.MessageFromDbModel;
import d1marine.com.d1chat.R;

/**
 * Created by User on 18/6/2017.
 */

public class ChatMessageFromDbAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<MessageFromDbModel> chatMessageList;
    private Activity activity;
    String mSelectedUser = "";
    LayoutInflater mLayoutInflator;
    public Context mContext;

    public ChatMessageFromDbAdapter(Activity activity, ArrayList<MessageFromDbModel> list, String selected_user) {
        chatMessageList = list;
        mContext = (Activity) activity;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        mSelectedUser = selected_user;
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
        MessageFromDbModel message = (MessageFromDbModel) chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble, null);

        EmojiTextView msg = (EmojiTextView) vi.findViewById(R.id.message_text);
        TextView mTime=(TextView)vi.findViewById(R.id.time_text);
        msg.setText(chatMessageList.get(position).getmMessage());
        mTime.setText(chatMessageList.get(position).getmTime());
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);


        // if message is mine then align to right
        if (chatMessageList.get(position).getmSender().equalsIgnoreCase(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable() + "@user-pc")) {
            parent_layout.setVisibility(View.VISIBLE);
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else if (chatMessageList.get(position).getmSender().equalsIgnoreCase(mSelectedUser + "@user-pc")) {//if(mSender.equalsIgnoreCase(mSelectedUser+"@user-pc"))
           /* msg.setText(message.body);
            mTime.setText(message.getTime());*/
            parent_layout.setVisibility(View.VISIBLE);
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }else{
            parent_layout.setVisibility(View.GONE);
        }

        msg.setTextColor(Color.BLACK);
        return vi;
    }
    public void add(MessageFromDbModel object) {
        chatMessageList.add(object);
    }
}

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageFromDbModel message = (MessageFromDbModel) chatMessageList.get(position);
        View vi = convertView;
        final ViewHolder holder ;
        if (convertView == null) {
            mLayoutInflator = LayoutInflater.from(mContext);
            //mLayoutInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = mLayoutInflator.inflate(R.layout.chatbubble, parent,false);
            holder = new ViewHolder();
            holder.msg = (EmojiTextView) vi.findViewById(R.id.message_text);
            holder. mTime = (TextView) vi.findViewById(R.id.time_text);
            holder.msg.setText(chatMessageList.get(position).getmMessage());
            holder.mTime.setText(chatMessageList.get(position).getmTime());

            holder. layout = (LinearLayout) vi .findViewById(R.id.bubble_layout);
            holder. parent_layout = (LinearLayout) vi .findViewById(R.id.bubble_layout_parent);



            vi.setTag(holder);
        }

           else
        {
            holder = (ChatMessageFromDbAdapter.ViewHolder) vi.getTag();
        }


        if (chatMessageList.get(position).getmSender().equalsIgnoreCase(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable() + "@user-pc")) {
            holder.parent_layout.setVisibility(View.VISIBLE);
            holder.layout.setBackgroundResource(R.drawable.bubble2);
            holder.parent_layout.setGravity(Gravity.END);
            //LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.parent_layout.getLayoutParams(); params.gravity= Gravity.RIGHT;

        } else if (chatMessageList.get(position).getmSender().equalsIgnoreCase(mSelectedUser + "@user-pc")) {//if(mSender.equalsIgnoreCase(mSelectedUser+"@user-pc"))
            holder.parent_layout.setVisibility(View.VISIBLE);
            holder.layout.setBackgroundResource(R.drawable.bubble1);
            holder.parent_layout.setGravity(Gravity.START);
            //LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.parent_layout.getLayoutParams(); params.gravity= Gravity.LEFT;
        } else {
            holder.parent_layout.setVisibility(View.GONE);
        }

      *//*  msg.setText(message.body);
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
           *//**//* msg.setText(message.body);
            mTime.setText(message.getTime());*//**//*
            parent_layout.setVisibility(View.VISIBLE);
            Toast.makeText(activity,mSender,Toast.LENGTH_SHORT).show();
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }else{
            parent_layout.setVisibility(View.GONE);
        }*//*

        holder.msg.setTextColor(Color.BLACK);
        return vi;
    }

    public void add(MessageFromDbModel object) {
        chatMessageList.add(object);
    }

    class ViewHolder {
        EmojiTextView msg;
        TextView mTime;
        LinearLayout layout,parent_layout;
    }}*/

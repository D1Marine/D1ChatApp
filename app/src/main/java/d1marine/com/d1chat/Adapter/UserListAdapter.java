package d1marine.com.d1chat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.igniterealtime.restclient.entity.UserEntities;

import java.util.List;

import d1marine.com.d1chat.Activity.ChatActivity;
import d1marine.com.d1chat.Activity.ChatAndContactActivity;
import d1marine.com.d1chat.Chat.D1ChatApplication;
import d1marine.com.d1chat.Chat.MyXMPP;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Model.ChatModel;
import d1marine.com.d1chat.Model.UserModel;
import d1marine.com.d1chat.R;

/**
 * Created by User on 7/6/2017.
 */

public class UserListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    List<UserModel> userList;
    private ImageLoader imageLoader;
    private Activity activity;

    public UserListAdapter(Activity activity, List<UserModel> userList) {
        this.userList = userList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ((D1ChatApplication) activity.getApplication()).getImageLoader();
        this.activity = activity;

    }


    @Override
    public int getCount() {
        return userList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.user_list_adapter_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.status = (NetworkImageView) vi.findViewById(R.id.status);
            viewHolder.userName = (TextView) vi.findViewById(R.id.userName);
            viewHolder.userLayout = (LinearLayout) vi.findViewById(R.id.userLayout);
            viewHolder.unreadMsgCount = (TextView) vi.findViewById(R.id.count_of_unread_msg);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }

        viewHolder.userName.setText(userList.get(position).getUsername());
        viewHolder.status.setImageUrl("http://" + activity.getString(R.string.server) + ":9090/plugins/presence/status?jid=" + userList.get(position).getUsername(), imageLoader);
        if(DatabaseQueryHelper.getInstance().getCountOfUnreadMessages(userList.get(position).getUsername()+"@user-pc")>0) {
            viewHolder.unreadMsgCount.setText(String.valueOf(DatabaseQueryHelper.getInstance().getCountOfUnreadMessages(userList.get(position).getUsername()+"@user-pc")));
        }else{
            viewHolder.unreadMsgCount.setText("0");
        }

        viewHolder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ChatActivity.USER_TWO = userList.get(position).getUsername();
                MyXMPP.chat_created = false;

                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("UserName", userList.get(position).getUsername());
                activity.startActivity(intent);
                //((ChatAndContactActivity)activity).mViewPager.setCurrentItem(1);
            }
        });

        return vi;

    }

    class ViewHolder {
        NetworkImageView status;
        TextView userName;
        LinearLayout userLayout;
        TextView unreadMsgCount;
    }
}

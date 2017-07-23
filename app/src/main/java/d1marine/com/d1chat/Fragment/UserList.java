package d1marine.com.d1chat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;
import org.jivesoftware.smack.roster.Roster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d1marine.com.d1chat.Activity.ChatAndContactActivity;
import d1marine.com.d1chat.Adapter.UserListAdapter;
import d1marine.com.d1chat.Constants.Constants;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Enum.ServiceCallEnum;
import d1marine.com.d1chat.Interface.VolleyCallback;
import d1marine.com.d1chat.Model.UserModel;
import d1marine.com.d1chat.Model.UserResponse;
import d1marine.com.d1chat.ParentFragment;
import d1marine.com.d1chat.R;

import static d1marine.com.d1chat.R.id.container;


/**
 * Created by User on 7/6/2017.
 */

public class UserList extends Fragment implements View.OnClickListener {
    List<UserModel> users = new ArrayList<UserModel>();
    ListView userList;
    HashMap<String, Object> extras;
    Handler mHandler;
    ServiceCallEnum mServiceCallEnum;
    AuthenticationToken authenticationToken;
    RestApiClient restApiClient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_layout, container, false);
        userList = (ListView) view.findViewById(R.id.userList);
        getUsrList();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        extras = new HashMap<String, Object>();
        mHandler = new Handler();

    }

    RequestQueue requestQueue;
    UserListAdapter adapter;

    public void getUsrList() {

        requestQueue = Volley.newRequestQueue(getActivity());
        try {
            StringRequest strReq = new StringRequest(Request.Method.GET, "http://" + getString(R.string.server) + ":9090/plugins/restapi/v1/users", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject mJsonObj = new JSONObject(response);
                        String mJsonString = mJsonObj.getString("user");
                        JSONArray mJsonArray = new JSONArray(mJsonString);
                        users.clear();
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            JSONObject mJson = mJsonArray.getJSONObject(i);
                            String mUserName = mJson.getString("username");
                            String mName = mJson.getString("name");

                            if (mUserName.equalsIgnoreCase("admin") || mUserName.equalsIgnoreCase("focus") || mUserName.equalsIgnoreCase(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable())) {
                                //do nothing
                            } else {
                                UserModel mUserModel = new UserModel();
                                mUserModel.setUsername(mUserName);
                                mUserModel.setName(mName);

                                users.add(mUserModel);
                            }
                        }

                        if (users != null && users.size() > 0) {
                            Collections.sort(users);
                            adapter = new UserListAdapter(getActivity(), users);
                            userList.setAdapter(adapter);
                        } else {

                        }

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "mGJNewv0pyb0R8kT");//6rewDIzgr0cqKflL    //"mGJNewv0pyb0R8kT"
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };
            requestQueue.add(strReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUsrList();
    }
}

package d1marine.com.d1chat.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import d1marine.com.d1chat.Activity.ChatAndContactActivity;
import d1marine.com.d1chat.Activity.MainActivity;
import d1marine.com.d1chat.Constants.Constants;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Enum.FragmentTransactionEnum;
import d1marine.com.d1chat.Enum.ServiceCallEnum;
import d1marine.com.d1chat.Interface.FragmentListenerCallBack;
import d1marine.com.d1chat.Interface.VolleyCallback;
import d1marine.com.d1chat.ParentFragment;
import d1marine.com.d1chat.R;

/**
 * Created by User on 17/5/2017.
 */

public class LoginFragment extends ParentFragment implements View.OnClickListener,VolleyCallback {
    TextView mTextRegister;
    EditText mEditName, mEditMobile;
    HashMap<String,Object>extras;
    Handler mHandler;
    ServiceCallEnum mServiceCallEnum;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.initialize(getActivity(),LoginFragment.this);
        extras=new HashMap<String,Object>();
        mHandler=new Handler();
        mEditName = (EditText) getActivity().findViewById(R.id.edit_login_name);
        mEditMobile = (EditText) getActivity().findViewById(R.id.edit_login_mobile);
        mTextRegister = (TextView) getActivity().findViewById(R.id.text_login_register);
        mTextRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_login_register: {
                if (isConnectingToInternet()) {
                    if (nullChecker()) {
                        if (mobileValidation(mEditMobile.getText().toString())) {
                            connectToLoginServer();
                       /* toFragment(FragmentTransactionEnum.ADDPROFILEPIC);
                        String token = FirebaseInstanceId.getInstance().getToken();
                        String msg = getString(R.string.msg_token_fmt, token);

                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();*/
                        }else{
                            shakeEditText(R.id.edit_login_mobile);
                            Toast.makeText(getActivity(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showWarning("Sorry","Please connect to internet");
                }
            }
        }
    }

    private void connectToLoginServer() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String msg = getString(R.string.msg_token_fmt, token);
        JSONObject mLoginObject = new JSONObject();
        try {
            mLoginObject.put("userid", "");
            mLoginObject.put("username", mEditName.getText().toString());
            mLoginObject.put("mobile",mEditMobile.getText().toString());
            mLoginObject.put("instanceid",msg);

            JSONObject mPassingObj=new JSONObject();
            mPassingObj.put("userInfo",mLoginObject);

            showProgress("Please Wait");
            mServiceCallEnum = ServiceCallEnum.LOGIN;
            establishConnection(Request.Method.POST, Constants.mLoginUrl, mPassingObj);
        } catch (Exception e) {
        }
    }

    private boolean nullChecker() {
        if (mEditName.getText().toString().trim().length() == 0 || mEditName.getText().toString() == "") {
            shakeEditText(R.id.edit_login_name);
            return false;
        } else if (mEditMobile.getText().toString().trim().length() == 0 || mEditMobile.getText().toString() == "") {
            shakeEditText(R.id.edit_login_mobile);
            return false;
        }
        return true;
    }

    @Override
    public void volleyOnSuccess() {
        dismissAlert();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!extras.isEmpty()) {
                    try {
                        String responseType = (String) extras.get("response");
                        Toast.makeText(getActivity(),responseType,Toast.LENGTH_SHORT).show();
                        if (mServiceCallEnum == ServiceCallEnum.LOGIN) {
                            JSONObject mJsonObject = new JSONObject(responseType);
                            String mString = mJsonObject.getString("d");
                           // int mUserId=Integer.parseInt(mString);


                          //  if (mUserId>0) {
                            if(mString.equalsIgnoreCase("true")){
                               // DatabaseQueryHelper.getInstance().insertEntryLogin(mUserId+"",mEditName.getText().toString(),mEditMobile.getText().toString(),"");
                                DatabaseQueryHelper.getInstance().insertEntryLogin("",mEditName.getText().toString(),mEditMobile.getText().toString(),"");
                                //showMessage("Success","Registration Successfull",FragmentTransactionEnum.ADDPROFILEPIC);
                                startActivity(new Intent(getActivity(),ChatAndContactActivity.class));
                            } else {
                                showMessage("Sorry", "Something went wrong",FragmentTransactionEnum.LOGIN);
                            }

                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void volleyOnError() {
        dismissAlert();
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (!extras.isEmpty()) {
                    try {
                        String responseType = (String) extras.get(getResources().getString(R.string.string_server_response));
                        showError("Error", responseType);

                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public HashMap<String, Object> getExtras() {
        return extras;
    }
}

package d1marine.com.d1chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Calendar;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by User on 17/5/2017.
 */

public class ParentActivity extends AppCompatActivity {
    private Activity mActivity;
   // private VolleyCallback mVolleyCallback;
    SweetAlertDialog mAlertDialog ;
   // private RequestQueue mQueue;
    Calendar myCalendar = Calendar.getInstance();
    private EditText mEditText;
    //private FragmentListenerCallback mAlertCallback;
    //private CustomJSONObjectRequest CustomRequest;
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private static final String SHARED_DETAILS = "SHARED_DETAILS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public boolean emailValidation(String str) {
        if (str.equals("")
                || EMAIL_ADDRESS_PATTERN.matcher(str).matches() == false)
            return false;
        else
            return true;
    }
    public boolean mobileValidation(String strmob){
        // if((strmob.length()==10)&&(strmob.contains("[0-9]+")))
        if(strmob.matches("[0-9]{10}"))
            return true;
        else
            return false;
    }

    public void initialize(Activity mActivity) {//,VolleyCallback mVolleyCallback
        this.mActivity = mActivity;
       // this.mVolleyCallback = mVolleyCallback;
    }

    public void dismissAlert() {

        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    public void showProgress(String message) {

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(message);
        mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#398808"));
        mAlertDialog.show();
        mAlertDialog.setCancelable(false);


    }




    public void showError(String header, String message) {

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(header)
                .setContentText(message);

        mAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                //toFragment(mCalltype);
                dismissAlert();

            }
        });
        mAlertDialog.show();
    }

    public void showMessage(String header, String message) {

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(header)
                .setContentText(message);

        mAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                //toFragment(mCalltype);
                dismissAlert();

            }
        });
        mAlertDialog.show();
    }

    public void showWarning(String header, String message) {

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(header)
                .setContentText(message);

        mAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                //toFragment(mCalltype);
                dismissAlert();

            }
        });
        mAlertDialog.show();
    }

    public void showSuccess(String header, String message) {

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(header)
                .setContentText(message);
        mAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {


                dismissAlert();
            }
        });
        mAlertDialog.show();
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;

    }

    public void shakeEdittext(int widgetId) {
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .playOn(this.findViewById(widgetId));
        EditText mEdittext = (EditText) this.findViewById(widgetId);
        mEdittext.setHintTextColor(Color.RED);
        mEdittext.requestFocus();
    }

    public void shakeSpinner(int widgetId) {
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .playOn(this.findViewById(widgetId));

        Spinner mSpinnertext = (Spinner) this.findViewById(widgetId);
        mSpinnertext.setBackgroundColor(Color.parseColor("#66f9b6c1"));
        mSpinnertext.requestFocus();

    }

   /* public void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }*/

}

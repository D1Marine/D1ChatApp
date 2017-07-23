package d1marine.com.d1chat.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.Cache;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.MainActivity;
import com.mancj.slideup.SlideUp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import d1marine.com.d1chat.Activity.AddStatusFirstActivity;
import d1marine.com.d1chat.Enum.FragmentTransactionEnum;
import d1marine.com.d1chat.Interface.VolleyCallback;
import d1marine.com.d1chat.ParentFragment;
import d1marine.com.d1chat.R;

/**
 * Created by User on 17/5/2017.
 */

public class AddProfilepicFragment extends ParentFragment implements View.OnClickListener,VolleyCallback {
   // private static final int RESULT_OK =1 ;
    ImageView mImageProfilePic, mImageAddProfilePic;
    TextView mTextSave, mTextCancel;
    LinearLayout mLayoutSelectCam,mLayoutSelectGall;
    private ViewGroup mHiddenPanel, mMainscreen;
    SlideUp slideUp;
    View dim;
    GalleryPhoto mGalleryPhoto;
    CameraPhoto mCameraPhoto;
    final int GALLERY_REQUEST = 22131;
    final int CAMERA_REQUEST=1100;
    Bitmap bitmap;
//13323
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.initialize(getActivity(),AddProfilepicFragment.this);
        mImageAddProfilePic = (ImageView) getActivity().findViewById(R.id.image_add_profile_cam);
        mImageProfilePic = (ImageView) getActivity().findViewById(R.id.image_profile_pic);
        mTextSave = (TextView) getActivity().findViewById(R.id.text_add_profile_save);
        mTextCancel = (TextView) getActivity().findViewById(R.id.text_add_profile_cancel);
        mLayoutSelectCam=(LinearLayout)getActivity().findViewById(R.id.select_from_camera);
        mLayoutSelectGall=(LinearLayout)getActivity().findViewById(R.id.select_from_gallery);
        dim = getActivity().findViewById(R.id.dim);
        mImageAddProfilePic.setOnClickListener(this);
        mTextSave.setOnClickListener(this);
        mTextCancel.setOnClickListener(this);
        mLayoutSelectGall.setOnClickListener(this);
        mLayoutSelectCam.setOnClickListener(this);

        View slideView = getActivity().findViewById(R.id.slideView);
        slideUp = new SlideUp.Builder(slideView)
                .withListeners(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {
                        dim.setAlpha(1 - (percent / 100));
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {

                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .build();

        mGalleryPhoto = new GalleryPhoto(getActivity().getApplicationContext());
        mCameraPhoto=new CameraPhoto(getActivity().getApplicationContext());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                mGalleryPhoto.setPhotoUri(uri);
                String mPhotoPath = mGalleryPhoto.getPath();
                try {
                   bitmap = ImageLoader.init().from(mPhotoPath).requestSize(512, 512).getBitmap();
                    mImageProfilePic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something wrong while choosing photos", Toast.LENGTH_SHORT).show();

                }
            }
        }
        if(resultCode==getActivity().RESULT_OK){
            if(requestCode==CAMERA_REQUEST){
                String mPhotopath=mCameraPhoto.getPhotoPath();

                try {
                    bitmap=ImageLoader.init().from(mPhotopath).requestSize(512,512).getBitmap();
                    mImageProfilePic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_profile_pic_fragment_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        switch (v.getId()) {
            case R.id.image_add_profile_cam: {
                slideUp.show();
                break;
            }
            case R.id.text_add_profile_save: {
                startActivity(new Intent(getActivity(),AddStatusFirstActivity.class));
                 //toFragment(FragmentTransactionEnum.ADDSTATUS);
                break;
            }

            case R.id.text_add_profile_cancel: {
                bitmap=null;
                mImageProfilePic.setImageResource(R.drawable.profile_pic);
                break;
            }
            case R.id.select_from_camera:{
                try {
                    startActivityForResult(mCameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                    mCameraPhoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                slideUp.hide();

                break;
            }
            case R.id.select_from_gallery:{
                startActivityForResult(mGalleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
               // getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                slideUp.hide();
                break;
            }
        }

    }

    @Override
    public void volleyOnSuccess() {

    }

    @Override
    public void volleyOnError() {

    }

    @Override
    public HashMap<String, Object> getExtras() {
        return null;
    }
}

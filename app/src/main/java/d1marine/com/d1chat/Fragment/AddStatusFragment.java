package d1marine.com.d1chat.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import d1marine.com.d1chat.Adapter.StatusAdapter;
import d1marine.com.d1chat.Data.DatabaseQueryHelper;
import d1marine.com.d1chat.Model.StatusModel;
import d1marine.com.d1chat.ParentFragment;
import d1marine.com.d1chat.R;

/**
 * Created by User on 17/5/2017.
 */

public class AddStatusFragment extends ParentFragment implements View.OnClickListener{
    private List<StatusModel> mStatusModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StatusAdapter mAdapter;
    TextView mTextAddCurrentStatus;
    EditText mEditAddStatusCurrent;
    LinearLayout mLayoutEditStatusImg,mLayoutStatusText,mLayoutStatusEdit,mLayoutEditStatusImgSave;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_status_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_status);
        mStatusModelList=new ArrayList<>();

        mTextAddCurrentStatus=(TextView)getActivity().findViewById(R.id.text_add_status_current_content);
        mLayoutEditStatusImg=(LinearLayout)getActivity().findViewById(R.id.linear_layout_edit_status);
        mLayoutStatusText=(LinearLayout)getActivity().findViewById(R.id.linear_layout_status_text);
        mLayoutStatusEdit=(LinearLayout)getActivity().findViewById(R.id.linear_layout_status_edit);
        mLayoutEditStatusImgSave=(LinearLayout)getActivity().findViewById(R.id.linear_layout_edit_status_save);
        mEditAddStatusCurrent=(EditText)getActivity().findViewById(R.id.edit_add_status_current_content);

        mLayoutEditStatusImg.setOnClickListener(this);
        mLayoutEditStatusImgSave.setOnClickListener(this);

        mLayoutStatusText.setVisibility(View.VISIBLE);
        mLayoutStatusEdit.setVisibility(View.GONE);

        mAdapter = new StatusAdapter(this.getActivity(),mStatusModelList,mTextAddCurrentStatus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareStatusData();

    }
    private void prepareStatusData() {
        int mRowId= DatabaseQueryHelper.getInstance().getStatusId();
        int mCount=DatabaseQueryHelper.getInstance().getCountFromStatusTable();
        //Toast.makeText(getActivity(),mCount+"",Toast.LENGTH_SHORT).show();
        StatusModel mModel;
        for (mRowId=1;mRowId<=mCount;mRowId++){
          int mStatusId=mRowId;
            mModel = new StatusModel(mStatusId,DatabaseQueryHelper.getInstance().getStatus(mStatusId));
            mStatusModelList.add(mModel);
        }
       /* mModel = new StatusModel(mStatusId,"hi");
        mStatusModelList.add(mModel);

         mModel = new StatusModel(2,"hello");
        mStatusModelList.add(mModel);
        mModel = new StatusModel(3,"bye");
        mStatusModelList.add(mModel);*/
        mAdapter.notifyDataSetChanged();

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
            case R.id.linear_layout_edit_status:{
                mLayoutStatusText.setVisibility(View.GONE);
                mLayoutStatusEdit.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.linear_layout_edit_status_save:{
                String mStatusTextNew=mEditAddStatusCurrent.getText().toString();
                mTextAddCurrentStatus.setText(mStatusTextNew);

                int mCount=DatabaseQueryHelper.getInstance().getCountFromStatusTable();
                int mCountinc=mCount+1;


                DatabaseQueryHelper.getInstance().insertEntryStatus(String.valueOf(mCountinc),mStatusTextNew);
                mLayoutStatusText.setVisibility(View.VISIBLE);
                mLayoutStatusEdit.setVisibility(View.GONE);

            }
        }

    }
}

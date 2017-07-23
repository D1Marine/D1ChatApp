package d1marine.com.d1chat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import d1marine.com.d1chat.Model.StatusModel;
import d1marine.com.d1chat.R;

/**
 * Created by User on 21/5/2017.
 */

public class StatusAdapter extends RecyclerView.Adapter <StatusAdapter.MyViewHolder>{
private List<StatusModel>statusList;
    private Context mContext;
    TextView mTextView;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mStatus;

        public MyViewHolder(View view) {
            super(view);
            mStatus = (TextView) view.findViewById(R.id.list_statuses);
            mStatus.setOnClickListener(this);
        }
        public TextView getStatus() {
            return mStatus;
        }
        @Override
        public void onClick(View v) {
            mTextView.setText(String.valueOf(this.getStatus().getText()));
           // Toast.makeText(v.getContext(),String.valueOf(this.getStatus().getText()),Toast.LENGTH_SHORT).show();

        }
    }

    public StatusAdapter(Context mContext,List<StatusModel>statusList,TextView tv){
        this.statusList=statusList;
        this.mContext=mContext;
        this.mTextView=tv;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frame_list_status, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatusModel mStatusModel = statusList.get(position);

       holder.mStatus.setText(mStatusModel.getmStatus());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}

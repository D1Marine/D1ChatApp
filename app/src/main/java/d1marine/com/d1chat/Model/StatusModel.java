package d1marine.com.d1chat.Model;

/**
 * Created by User on 21/5/2017.
 */

public class StatusModel  {
    private String mStatus;
    private int mStatusId;


    public StatusModel(int mStatusId,String mStatus){
        this.mStatusId=mStatusId;
        this.mStatus=mStatus;

    }

    public int getmStatusId() {
        return mStatusId;
    }

    public void setmStatusId(int mStatusId) {
        this.mStatusId = mStatusId;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmStatus() {
        return mStatus;
    }


}

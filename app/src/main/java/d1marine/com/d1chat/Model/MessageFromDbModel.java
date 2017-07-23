package d1marine.com.d1chat.Model;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Random;

/**
 * Created by User on 13/6/2017.
 */

public class MessageFromDbModel implements Comparable<MessageFromDbModel> {
    public String mMessageId,mSender,mReceiver,mMessage,mTime;
    public int mReadStatus, mSlNo;
    public boolean isMine;

    public MessageFromDbModel(){

    }

    public int getmSlNo() {
        return mSlNo;
    }

    public void setmSlNo(int mSlNo) {
        this.mSlNo = mSlNo;
    }


    public String getmMessageId() {
        return mMessageId;
    }

    public void setmMessageId(String mMessageId) {
        this.mMessageId = mMessageId;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public String getmReceiver() {
        return mReceiver;
    }

    public void setmReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }

    public int getmReadStatus() {
        return mReadStatus;
    }

    public void setmReadStatus(int mReadStatus) {
        this.mReadStatus = mReadStatus;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public MessageFromDbModel(String Sender, String Receiver, String messageString,
                     String ID, boolean isMINE) {
        mMessage = messageString;
        isMine = isMINE;
        mSender = Sender;
        mMessageId = ID;
        mReceiver = Receiver;

    }

    public void setMsgID() {

        mMessageId += "-" + String.format("%02d", new Random().nextInt(100));
        ;
    }

    @Override
    public int compareTo(@NonNull MessageFromDbModel o) {
        return 0;
    }
}

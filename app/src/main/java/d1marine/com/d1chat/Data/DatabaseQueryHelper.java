package d1marine.com.d1chat.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import d1marine.com.d1chat.Model.MessageFromDbModel;

/**
 * Created by User on 21/5/2017.
 */

public class DatabaseQueryHelper {

    static DatabaseQueryHelper databaseQueryHelper;
    static SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public ArrayList<MessageFromDbModel> mMessageFromDbList;
    Context mContext;

    public DatabaseQueryHelper(Context myContext, SQLiteDatabase db) {
        databaseQueryHelper = this;
        dbHelper = new DatabaseHelper(myContext);
        mContext=myContext;
        mMessageFromDbList = new ArrayList<MessageFromDbModel>();
        this.db = db;
    }

    public static DatabaseQueryHelper getInstance() {
        return databaseQueryHelper;
    }

    //////////////////////////////////LOGIN//////////////////////////////////////

    public void insertEntryLogin(String mUserId, String mUserName, String mMobileNumber, String mInstanceId) {
        ContentValues values = new ContentValues();
        values.put("UserId", mUserId);
        values.put("Username", mUserName);
        values.put("MobileNumber", mMobileNumber);
        values.put("InstanceId", mInstanceId);

        db.insert("LoginTable", null, values);
    }

    public String getUserNameFromLoginTable() {
        Cursor mCur = null;
        String mUserName = null;
        try {

            mCur = db.rawQuery("select*from LoginTable", null);
            if (mCur != null && mCur.moveToFirst()) {
                mUserName = mCur.getString(mCur.getColumnIndex("Username"));
            }

        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }
        return mUserName;
    }

    public String getUserIdFromLoginTable() {
        Cursor mCur = null;
        String mUserId = null;
        try {

            mCur = db.rawQuery("select*from LoginTable", null);
            if (mCur != null && mCur.moveToFirst()) {
                mUserId = mCur.getString(mCur.getColumnIndex("UserId"));
            }

        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }
        return mUserId;
    }
    ////////////////////////////////////STATUS/////////////////////////////////////

    public void insertEntryStatus(String mStatusId, String Status) {
        ContentValues values = new ContentValues();
        values.put("StatusId", mStatusId);
        values.put("Status", Status);

        db.insert("StatusTable", null, values);
    }

    public int getStatusId() {
        Cursor mCur = null;
        int mStatusId = 0;
        try {

            mCur = db.rawQuery("select*from StatusTable", null);
            if (mCur != null && mCur.moveToFirst()) {
                mStatusId = mCur.getInt(mCur.getColumnIndex("StatusId"));
            }

        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }
        return mStatusId;
    }

    public String getStatus(int mRowId) {
        String mStatus = null;
        Cursor mCur = null;
        try {
            mCur = db.rawQuery("select*from StatusTable where StatusId=" + mRowId, null);
            if (mCur != null && mCur.moveToFirst()) {
                mStatus = mCur.getString(mCur.getColumnIndex("Status"));
            }
        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }

        return mStatus;
    }

    public int getCountFromStatusTable() {
        Cursor cursor = null;
        try {
            String countQuery = "SELECT  * FROM " + "StatusTable";
            cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } finally {
            {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    public void deleteAllEntriesFromStatusTable() {
        db.execSQL("delete from " + "StatusTable");
    }

////////////////////////MESSAGE//////////////////////////////////

  /*  public void insertEntryMessage(String mMessageId, String mSenderId,String mSender, String mReceiver, String mMessage, int mReadStatus, String mTime) {
        ContentValues values = new ContentValues();
        values.put("MessageId", mMessageId);
        values.put("SenderId", mSenderId);
        values.put("Sender", mSender);
        values.put("Reciever", mReceiver);
        values.put("Message", mMessage);
        values.put("ReadStatus", mReadStatus);
        values.put("Time", mTime);

        db.insert("ChatMessageTable", null, values);
    }*/
  public void insertEntryMessage(String mMessageId,String mSender, String mReceiver, String mMessage, int mReadStatus, String mTime) {
      ContentValues values = new ContentValues();
      values.put("MessageId", mMessageId);
      values.put("Sender", mSender);
      values.put("Reciever", mReceiver);
      values.put("Message", mMessage);
      values.put("ReadStatus", mReadStatus);
      values.put("Time", mTime);

      db.insert("ChatMessageTable", null, values);
  }

    public void setMessageFromDbList(String Sender) {
        mMessageFromDbList.clear();
        Cursor mCur = null;
        try {
            mCur = db.rawQuery("SELECT SlNo, MessageId, Sender, Reciever, Message, ReadStatus, Time FROM ChatMessageTable", null);
            if (mCur != null) {
                if (mCur.moveToFirst()) {
                    do {
                      //  Toast.makeText(Context,mCur.getColumnIndex("ReadStatus"));
                        /*if((mCur.getInt(mCur.getColumnIndex("ReadStatus"))==1) &&*/
                                if(mCur.getString(mCur.getColumnIndex("Sender")).equalsIgnoreCase(Sender)||mCur.getString(mCur.getColumnIndex("Sender")).equalsIgnoreCase(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable()+"@user-pc")&&
                                (mCur.getString(mCur.getColumnIndex("Reciever")).equalsIgnoreCase(Sender)||mCur.getString(mCur.getColumnIndex("Reciever")).equalsIgnoreCase(DatabaseQueryHelper.getInstance().getUserNameFromLoginTable()+"@user-pc"))) {


                            int mMsgSlNo = mCur.getInt(mCur.getColumnIndex("SlNo"));
                            String mMessageId = mCur.getString(mCur.getColumnIndex("MessageId"));
                            String mSender = mCur.getString(mCur.getColumnIndex("Sender"));
                            String mReceiver = mCur.getString(mCur.getColumnIndex("Reciever"));
                            String mMessage = mCur.getString(mCur.getColumnIndex("Message"));
                            int mReadStatus = mCur.getInt(mCur.getColumnIndex("ReadStatus"));
                            String mTime = mCur.getString(mCur.getColumnIndex("Time"));

                            MessageFromDbModel mMessageFromDbModel = new MessageFromDbModel();

                            mMessageFromDbModel.setmSlNo(mMsgSlNo);
                            mMessageFromDbModel.setmMessageId(mMessageId);
                            mMessageFromDbModel.setmSender(mSender);
                            mMessageFromDbModel.setmReceiver(mReceiver);
                            mMessageFromDbModel.setmMessage(mMessage);
                            mMessageFromDbModel.setmReadStatus(mReadStatus);
                            mMessageFromDbModel.setmTime(mTime);

                            mMessageFromDbList.add(mMessageFromDbModel);
                      }
                    } while (mCur.moveToNext());
                }
            }
        }

        catch (Exception e)
        {
            Toast.makeText(mContext,e.toString(),Toast.LENGTH_SHORT).show();
        }
        finally {
            if (mCur != null) {
                mCur.close();
            }
        }
    }

    public int getCountOfUnreadMessages(String mSender) {
        Cursor cursor = null;
        try {

            String countQuery = "SELECT  * FROM " + "ChatMessageTable " + "WHERE ReadStatus = " + 0 + " AND Sender = '"+mSender+"'";
            cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
           /* if(cnt<=0){
                cursor.close();
                return 0;
            }else {

                return cnt;
            }*/
        } finally {
            {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

    }

    public String getMsgIdFromChatMessageTable(){
        Cursor mCur = null;
        String mMsgId = null;
        try {

            mCur = db.rawQuery("select*from ChatMessageTable", null);
            if (mCur != null && mCur.moveToFirst()) {
                mMsgId = mCur.getString(mCur.getColumnIndex("MessageId"));
            }

        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }
        return mMsgId;
    }

    public int isMessagePresent(String mMessage)
    {
        int number = 0;
        Cursor c = null;
        try
        {
            c = db.rawQuery("select MessageId from ChatMessageTable where MessageId = ?", new String[] {String.valueOf(mMessage)});

            if(c.getCount() != 0)
                number = c.getCount();
        }
        catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(c!=null) c.close();
        }
        return number;
    }

    public void deleteAllEntriesFromChatMessageTable() {
        db.execSQL("delete from " + "ChatMessageTable");
    }

   /* public int updateReadStatusInChatMessageTable(int rowId){
        ContentValues args = new ContentValues();
        args.put("ReadStatus", 1);
        return db.update("ChatMessageTable", args, "SlNo=" + rowId, null);
    }*/
   public int updateReadStatusInChatMessageTable(String mSender){
       ContentValues args = new ContentValues();
       args.put("ReadStatus", 1);
       return db.update("ChatMessageTable", args, "Sender='" + mSender+"'", null);
   }

    public void transferDataFromChatMessageToAllChat() {
        String selectQuery = "INSERT INTO " + "PlaceOrderTable" + " SELECT * FROM " + "AddOrderDetails";
        db.execSQL(selectQuery);
    }
    //////////////////CHAT ACTIVITY OPEN CHECK/////////////////////////////////////
    public void deleteAllEntriesFromChatActivityOpenCheckTable() {
        db.execSQL("delete from " + "ChatActivityOpenCheckTable");
    }

    public void insertEntryChatActivityOpenCheckTable(String mStatus) {
        ContentValues values = new ContentValues();
        values.put("Status", mStatus);
        db.insert("ChatActivityOpenCheckTable", null, values);
    }

    public int updateChatActivityOpenCheckTable(String mStatusValue){
        ContentValues args = new ContentValues();
        args.put("Status", mStatusValue);
        return db.update("ChatActivityOpenCheckTable", args, null, null);
    }

    public String getStatusFromChatActivityOpenCheckTable(){
        Cursor mCur = null;
        String mMsgId = null;
        try {

            mCur = db.rawQuery("select*from ChatActivityOpenCheckTable", null);
            if (mCur != null && mCur.moveToFirst()) {
                mMsgId = mCur.getString(mCur.getColumnIndex("Status"));
            }

        } catch (Exception e) {

        } finally {
            if (mCur != null) {
                mCur.close();
            }
        }
        return mMsgId;
    }
//////////////////////NEW USER CHECK////////////////////////////////////////////
    public int isNewUser(){
        int isNew=0;
        Cursor mCur=db.rawQuery("Select * from LoginTable",null);
        if (mCur != null && mCur.moveToFirst()) {
            isNew = 1;
        }
        return isNew;
    }
}

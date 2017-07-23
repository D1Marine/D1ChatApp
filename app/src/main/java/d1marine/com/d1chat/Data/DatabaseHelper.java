package d1marine.com.d1chat.Data;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 21/5/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static String DBPATH;
    private static final String DB_NAME = "D1ChatDb.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase db;
    private final Context myContext;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        Resources resources = myContext.getResources();
        DBPATH = myContext.getExternalFilesDir(null) + "/";
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }


    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DBPATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void DeleteDatabase() {
        String myPath = DBPATH + DB_NAME;
        SQLiteDatabase.deleteDatabase(new File(myPath));

    }

    private void copyDatabase()throws IOException {
        InputStream myInput=myContext.getAssets().open("D1ChatDb.sqlite");
        String OutFilename=DBPATH+DB_NAME;
        OutputStream myOutput=new FileOutputStream(OutFilename);
        byte[] buffer=new byte[1024];
        int length;
        while ((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDatabase()throws android.database.SQLException{
        String myPath=DBPATH+DB_NAME;
        db=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    @Override
    public synchronized void close() {
        if (db!=null){
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void getQueryHelper(){
        new DatabaseQueryHelper(myContext,db);
    }
}

package d1marine.com.d1chat.Chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import d1marine.com.d1chat.Data.DatabaseQueryHelper;

public class CommonMethods {
    private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

    public static String getCurrentTime() {

        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


   /* public void displayList() {
        DatabaseQueryHelper.getInstance().setMessageFromDbList(USER_TWO+"@user-pc");


    }*/
}

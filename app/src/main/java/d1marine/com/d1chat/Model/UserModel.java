package d1marine.com.d1chat.Model;

import android.support.annotation.NonNull;

/**
 * Created by User on 7/6/2017.
 */

public class UserModel implements Comparable {
    private String username;
    private String name;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}

package ck.isyhelper;


import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MyApplication extends Application {

    private boolean isFirstRun = false;

    @Override
    public void onCreate() {
        super.onCreate();


    }
}

package ck.isyhelper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    private static final String IP_REGEX = "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.main_rooms:
                    fragment = new RoomsFragment();
                    break;

                case R.id.main_nodes:
                    fragment = new NodesFragment();
                    break;

                case R.id.main_programs:
                    fragment = new ProgramsFragment();
                    break;

                case R.id.main_variables:
                    fragment = new VariablesFragment();
                    break;

                case R.id.main_settings:
                    startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                    return false;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        ISYClient client = ISYClient.getInstance();
        client.initialize(getApplicationContext());

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(prefs.getBoolean("first_run", true)) {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View prompt = layoutInflater.inflate(R.layout.first_run_input_dialog, null);

            final EditText ip = prompt.findViewById(R.id.fr_ip_address);
            final EditText user = prompt.findViewById(R.id.fr_user);
            final EditText pass = prompt.findViewById(R.id.fr_password);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(prompt);
            builder.setCancelable(false);
            builder.setPositiveButton("Save", null);


            final AlertDialog promptDialog = builder.create();
            promptDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    Button b = promptDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean inputValidation = true;

                            Log.w("tset", ip.getText().toString());

                            if(!ip.getText().toString().matches(IP_REGEX)) {
                                Log.w("tset", "matches");
                                ip.setError("Please enter a properly formatted IP address");
                                inputValidation = false;
                            }

                            if(user.getText().toString().length() == 0) {
                                user.setError("Username is too short");
                                inputValidation = false;
                            }


                            if(pass.getText().toString().length() == 0) {
                                pass.setError("Username is too short");
                                inputValidation = false;
                            }

                            if(inputValidation) {
                                Toast.makeText(DashboardActivity.this, "Saving settings", Toast.LENGTH_LONG).show();

                                prefs.edit().putString("isy_ip", ip.getText().toString())
                                .putString("isy_user", user.getText().toString())
                                .putString("isy_password", pass.getText().toString())
                                .putBoolean("first_run", false).apply();

                                promptDialog.dismiss();
                            }
                        }
                    });
                }
            });


            promptDialog.show();
        }
    }

}

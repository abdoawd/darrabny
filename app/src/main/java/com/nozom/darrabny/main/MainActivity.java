package com.nozom.darrabny.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.company.Company_home_page;
import com.nozom.darrabny.company.Company_signup;
import com.nozom.darrabny.student.Student_home_page;
import com.nozom.darrabny.student.Student_signup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText login_email, login_password;
    private Button login, signup;
    private String email, pass;
    private RadioGroup group;
    private RadioButton RB;
    private ProgressDialog progress;
    private BackendlessUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = Backendless.UserService.CurrentUser();
        if (user != null) {
            Log.e("user type", user.getProperty(Constants.TYPE) + "");
            if (user.getProperty("type").equals("Company")) {
                startActivity(new Intent(this, Company_home_page.class));
                MainActivity.this.finish();
            }
        else {
            startActivity(new Intent(this,Student_home_page.class));
            MainActivity.this.finish();
        }}
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.btn_login);
        group = (RadioGroup) findViewById(R.id.RB_group);
        login.setOnClickListener(this);
        signup = (Button) findViewById(R.id.btn_signup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        email = login_email.getText().toString();
        pass = login_password.getText().toString();
        if (v.getId() == R.id.btn_login) {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                login_email.setError(Constants.EMPTY_ERROR);
            } else {
                new AsyncTask<String, Integer, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        Activity activity = MainActivity.this;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress = new ProgressDialog(MainActivity.this);
                                progress.setIcon(R.drawable.logo);
                                progress.setTitle("loading  " + email);
                                progress.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
                                progress.setIndeterminate(true);
                                progress.setMessage("loadind...");
                                progress.setProgress(0);
                                progress.show();
                                final int totalProgressTime = 100;
                                final Thread t = new Thread() {
                                    @Override
                                    public void run() {
                                        int jumpTime = 0;

                                        while (jumpTime < totalProgressTime) {
                                            try {
                                                sleep(200);
                                                jumpTime += 5;
                                                progress.setProgress(jumpTime);
                                            } catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                };
                                t.start();
                            }
                        });

                        return null;
                    }


                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        Backendless.UserService.login(email, pass, new BackendlessCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser backendlessUser) {
                                if (backendlessUser.getProperty(Constants.TYPE).equals(Constants.COMPANY)) {
                                    startActivity(new Intent(getApplicationContext(), Company_home_page.class));
                                    MainActivity.this.finish();
                                    Log.e("login", "success");
                                } else {
                                    Log.e("login", "success");
                                    startActivity(new Intent(getApplicationContext(), Student_home_page.class));
                                    MainActivity.this.finish();
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(getApplicationContext(), fault.toString(), Toast.LENGTH_LONG).show();
                                progress.dismiss();

                            }
                        });

                    }


                }.execute();

            }
        } else if (v.getId() == R.id.btn_signup) {
            int radioSelected = group.getCheckedRadioButtonId();
            RB = (RadioButton) findViewById(radioSelected);
            String B_text = RB.getText().toString();
            if (B_text.equals(Constants.STUDENT)) {
                startActivity(new Intent(this, Student_signup.class));

            } else if (B_text.equals(Constants.COMPANY)) {
                startActivity(new Intent(this, Company_signup.class));

            }
        }
    }
}

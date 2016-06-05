package com.nozom.darrabny.student;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.company.Company_home_page;
import com.nozom.darrabny.main.Constants;

public class Student_signup extends AppCompatActivity implements View.OnClickListener {
    private EditText student_name, student_email, student_phone,
            student_password, student_confirmPass;

    private String name, email, password, confirm, phone;
    private Button register;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_signup);
        student_name = (EditText) findViewById(R.id.student_name);
        student_email = (EditText) findViewById(R.id.student_email);
        student_phone = (EditText) findViewById(R.id.student_phone);
        student_password = (EditText) findViewById(R.id.student_password);
        student_confirmPass = (EditText) findViewById(R.id.student_confirmPassword);
        register = (Button) findViewById(R.id.student_register);
        register.setOnClickListener(this);
    }

    private void getInformation() {
        name = student_name.getText().toString();
        email = student_email.getText().toString();
        password = student_password.getText().toString();
        confirm = student_confirmPass.getText().toString();
        phone = student_phone.getText().toString();

    }

    @Override
    public void onClick(View v) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getInformation();
                Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                                                                    @Override
                                                                    public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                                                                        if (TextUtils.isEmpty(email)) {
                                                                            student_email.setError(Constants.EMPTY_ERROR);
                                                                        } else {
                                                                            for (BackendlessUser user : users.getCurrentPage()) {
                                                                                if (user.getEmail().equals(email)) {
                                                                                    student_email.setError(Constants.EMAIL_EXISTS);
                                                                                    break;
                                                                                }
                                                                            }
                                                                            if (TextUtils.isEmpty(name)) {
                                                                                student_name.setError(Constants.EMPTY_ERROR);

                                                                            } else if (TextUtils.isEmpty(phone)) {
                                                                                student_phone.setError(Constants.EMPTY_ERROR);
                                                                            } else if (TextUtils.isEmpty(password)) {
                                                                                student_password.setError(Constants.EMPTY_ERROR);
                                                                            } else if (TextUtils.isEmpty(confirm)) {
                                                                                student_confirmPass.setError(Constants.EMPTY_ERROR);
                                                                            } else if (!password.equals(confirm)) {
                                                                                student_confirmPass.setError(Constants.MATCH_PASS);
                                                                            } else {
                                                                                if (TextUtils.isEmpty(email)) {
                                                                                    student_email.setError(Constants.EMPTY_ERROR);
                                                                                } else {
                                                                                    BackendlessUser user1 = new BackendlessUser();
                                                                                    user1.setEmail(email);
                                                                                    user1.setPassword(password);
                                                                                    user1.setProperty(Constants.COMPANY_PHONE, phone);
                                                                                    user1.setProperty(Constants.TRAINING_NAME, name);
                                                                                    user1.setProperty(Constants.TYPE, Constants.STUDENT);
                                                                                    Backendless.UserService.register(user1, new AsyncCallback<BackendlessUser>() {
                                                                                        @Override
                                                                                        public void handleResponse(BackendlessUser backendlessUser) {
                                                                                            Backendless.UserService.login(backendlessUser.getEmail(),
                                                                                                    backendlessUser.getPassword(),
                                                                                                    new BackendlessCallback<BackendlessUser>() {
                                                                                                        @Override
                                                                                                        public void handleResponse(BackendlessUser backendlessUser) {

                                                                                                            startActivity(new Intent(Student_signup.this, Student_home_page.class));
                                                                                                        }
                                                                                                    });
                                                                                        }

                                                                                        @Override
                                                                                        public void handleFault(BackendlessFault backendlessFault) {
                                                                                            student_email.setError(backendlessFault.toString());
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void handleFault(BackendlessFault backendlessFault) {
                                                                        Toast.makeText(getApplicationContext(), backendlessFault.toString(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                }


                );

            }

            @Override
            protected String doInBackground(String... params) {
                Activity activity = Student_signup.this;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress = new ProgressDialog(Student_signup.this);

                        progress.setIcon(android.R.drawable.gallery_thumb);
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
        }.execute();


    }
}







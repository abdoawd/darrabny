package com.nozom.darrabny.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.logout;
import com.nozom.darrabny.main.request;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

public class TrainingForEntry extends AppCompatActivity implements View.OnClickListener {
    private EditText E_academicYear, E_grade, E_experience, E_name;
    private String academicYear, grade, experience, name;
    private Button confirm;
    private BackendlessUser user;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();
        user = Backendless.UserService.CurrentUser();
        setContentView(R.layout.activity_training_for_entry);
        E_academicYear = (EditText) findViewById(R.id.academic_year);
        E_grade = (EditText) findViewById(R.id.grade);
        E_experience = (EditText) findViewById(R.id.experience);
        E_name = (EditText) findViewById(R.id.name);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        setInformation();
        if (TextUtils.isEmpty(academicYear) || TextUtils.isEmpty(grade) || TextUtils.isEmpty(experience)) {
            Toast.makeText(getApplicationContext(), Constants.EMPTY_ERROR, Toast.LENGTH_LONG).show();
        } else {
            findViewById(R.id.loadingPanel_join).setVisibility(View.VISIBLE);
            final List<request> request1 = new ArrayList<>();
            Backendless.Data.save(new request(name, academicYear, grade, experience,user), new AsyncCallback<request>() {
                @Override
                public void handleResponse(final request Request) {
                    request1.add(Request);
                    Backendless.Persistence.of(training.class).findById(i.getStringExtra(Constants.TRAINING_OBJECTID), new AsyncCallback<training>() {
                        @Override
                        public void handleResponse(training training1) {


                            if (training1.getRequestList() != null) {
                                training1.getRequestList().add(request1.get(request1.size() - 1));
                                training1.setRequests(training1.getRequests() + 1);
                            } else {
                                training1.setRequestList(request1);
                                training1.setRequests(request1.size());
                            }
                            training1.setRequestList(training1.getRequestList());

                            Log.e("Training", training1.getRequestList().size() + "");
                            Backendless.Persistence.save(training1, new AsyncCallback<training>() {
                                @Override
                                public void handleResponse(training response) {
                                    Toast.makeText(getApplicationContext(), "your Request is send", Toast.LENGTH_LONG).show();
                                    Log.d("saved", "saved");
                                    E_academicYear.setText("");
                                    E_grade.setText("");
                                    E_experience.setText("");
                                    E_name.setText("");
                                    findViewById(R.id.loadingPanel_join).setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(getApplicationContext(), fault.toString(), Toast.LENGTH_LONG).show();
                                    Log.d("saved", fault.toString());

                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.e("Training", backendlessFault.toString());
                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Log.e("Request", backendlessFault.toString());
                }
            });


        }


    }

    private void setInformation() {
        academicYear = E_academicYear.getText().toString();
        name = E_name.getText().toString();
        grade = E_grade.getText().toString();
        experience = E_experience.getText().toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_training_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout logout=new logout(this);
            logout.logOut(Backendless.UserService.CurrentUser());
        }
        return true;
    }
}

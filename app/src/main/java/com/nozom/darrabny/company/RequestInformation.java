package com.nozom.darrabny.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PushBroadcastMask;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.nozom.darrabny.R;
import com.nozom.darrabny.main.Comment;
import com.nozom.darrabny.main.Constants;


import com.nozom.darrabny.main.logout;
import com.nozom.darrabny.main.request;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

public class RequestInformation extends AppCompatActivity implements View.OnClickListener {
    private TextView academicYear, name, grade, experience;
    private Button accept, refuse;
    private String trainingObjectId;
    private BackendlessUser user;
    private BackendlessUser owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_information);
        Intent i = getIntent();
        user = Backendless.UserService.CurrentUser();
        experience = (TextView) findViewById(R.id.request_experience);
        name = (TextView) findViewById(R.id.request_user_name);
        academicYear = (TextView) findViewById(R.id.request_academic_year);
        grade = (TextView) findViewById(R.id.request_grade);
        trainingObjectId = i.getStringExtra("training");
        retrieve(i.getStringExtra("request"));
        accept = (Button) findViewById(R.id.accept_btn);
        accept.setOnClickListener(this);
        refuse = (Button) findViewById(R.id.refuse_btn);
        refuse.setOnClickListener(this);
    }

    private void retrieve(String objectId) {

        Backendless.Persistence.of(request.class).findById(objectId, new AsyncCallback<request>() {
            @Override
            public void handleResponse(request request) {
                academicYear.setText(request.getAcademicYear());
                name.setText(request.getName());
                experience.setText(request.getExperience());
                grade.setText(request.getGrade());
                owner = request.getOwner();
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.accept_btn) {


            Backendless.Persistence.of(training.class).findById(trainingObjectId, new AsyncCallback<training>() {
                @Override
                public void handleResponse(training response) {
                    Backendless.Data.save(new Comment(owner, response, "accepted"), new AsyncCallback<Comment>() {
                        @Override
                        public void handleResponse(Comment response) {
                            Log.e("waiting", response.getStatus());
                           /* response.setId(response.getId() );
                            Backendless.Persistence.save(response, new AsyncCallback<Comment>() {
                                @Override
                                public void handleResponse(Comment response) {
                                    Log.e("comment", response.getId() + "");
                                    Toast.makeText(getApplicationContext(), "success aceepted", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Log.e("comment", fault.toString());
                                }
                            });*/
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("waiting", fault.toString());
                        }
                    });
                   /* if (response.getAcceptedUser() != null) {
                        response.getAcceptedUser().add(users.get(users.size() - 1));
                        Log.e("tr", response.getAuthorEmail());
                    } else {
                        response.setAcceptedUser(users);
                    }
                    response.setAcceptedUser(response.getAcceptedUser());
                    Backendless.Persistence.save(response, new AsyncCallback<training>() {
                        @Override
                        public void handleResponse(training response) {
                            Log.e("info", response.getAcceptedUser().toString());
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("info", fault.toString());

                        }
                    });
               */
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("tr", fault.toString());
                }
            });
            PublishOptions publishOptions = new PublishOptions();
            publishOptions.putHeader("android-ticker-text", "You just got a push Notification!");
            publishOptions.putHeader("android-content-title", "This is a Notification title");
            publishOptions.putHeader("android-content-text", "Push Notifications are cool");
            Backendless.Messaging.publish((Object) "hi device", publishOptions, new AsyncCallback<MessageStatus>() {
                @Override
                public void handleResponse(MessageStatus response) {
                    Log.e("message", response.toString());

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("message", fault.toString());

                }
            });
        } else if (id == R.id.refuse_btn) {
            Backendless.Persistence.of(training.class).findById(trainingObjectId, new AsyncCallback<training>() {
                @Override
                public void handleResponse(training response) {
                    Backendless.Data.save(new Comment(user, response, "refused"), new AsyncCallback<Comment>() {
                        @Override
                        public void handleResponse(Comment response) {
                            Log.e("waiting", response.getObjectId());
                            Toast.makeText(getApplicationContext(), "success aceepted", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("waiting", fault.toString());
                        }
                    });
                    DeliveryOptions deliveryOptions = new DeliveryOptions();

                    deliveryOptions.setPushBroadcast(PushBroadcastMask.ANDROID | PushBroadcastMask.IOS);

                    PublishOptions publishOptions = new PublishOptions();
                    publishOptions.putHeader("android-ticker-text", "You just got a push Notification!");
                    publishOptions.putHeader("android-content-title", "This is a Notification title");
                    publishOptions.putHeader("android-content-text", "Push Notifications are cool");

                    Backendless.Messaging.publish("Hi Devices!", publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
                        @Override
                        public void handleResponse(MessageStatus response) {
                            Log.e("message", response.toString());
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("message", fault.toString());
                        }
                    });

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }

            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout logout = new logout(this);
            logout.logOut(Backendless.UserService.CurrentUser());
        }
        return true;
    }
}

package com.nozom.darrabny.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;

/**
 * Created by abdelgawad on 23/04/16.
 */
public class logout {
    Activity activity;
private ProgressDialog progress;
    public logout(Activity activity) {

        this.activity = activity;
    }

    public void logOut(final BackendlessUser user){
        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    public void handleResponse(Void response) {
                        activity.startActivity(new Intent(activity,MainActivity.class));
                        activity.finish();
                        Log.e("log out ","success");

                    }

                    public void handleFault(BackendlessFault fault) {
                        // something went wrong and logout failed, to get the error code call fault.getCode()
                        Toast.makeText(activity.getApplicationContext(), fault.toString(), Toast.LENGTH_LONG).show();
                        Log.e("log out fault",fault.toString());

                    }
                });
            }


            @Override
            protected String doInBackground(String... params) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress = new ProgressDialog(activity);
                        progress.setIcon(R.drawable.logo);
                        progress.setTitle("log out... "+user.getEmail());
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

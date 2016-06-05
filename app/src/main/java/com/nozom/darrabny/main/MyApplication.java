package com.nozom.darrabny.main;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by abdelgawad on 18/03/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Backendless.initApp(this, Constants.APPLICATION_iD, Constants.SECRET_KEY, Constants.APP_VERSION);
        Backendless.Messaging.registerDevice(Constants.GCMSENDERID, "default", new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(),fault.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}

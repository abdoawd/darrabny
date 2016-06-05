package com.nozom.darrabny.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.AdapteRequest;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.logout;
import com.nozom.darrabny.main.request;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

public class Acception_home extends AppCompatActivity {
    ListView accepting_lv;
    String training_object_id;
    List<request> requestList;
    AdapteRequest adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        training_object_id = i.getStringExtra(Constants.TRAINING_OBJECTID);
        setContentView(R.layout.activity_acception_home);
        accepting_lv = (ListView) findViewById(R.id.accepting_lv);
        requestList = new ArrayList<>();
        retrieve();
    }

    private void retrieve() {

        Backendless.Persistence.of(training.class).findById(training_object_id, new AsyncCallback<training>() {
            @Override
            public void handleResponse(training training) {
                requestList = training.getRequestList();
                Log.e("requests",requestList.size()+"");
                adapter = new AdapteRequest(Acception_home.this, requestList,training_object_id);
                accepting_lv.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
Log.e("training",backendlessFault.toString());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acception, menu);
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

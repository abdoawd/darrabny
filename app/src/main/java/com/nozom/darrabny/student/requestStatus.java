package com.nozom.darrabny.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.backendless.Backendless;
import com.nozom.darrabny.R;
import com.nozom.darrabny.main.logout;

public class requestStatus extends AppCompatActivity {
TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        view= (TextView) findViewById(R.id.status);
        Intent i=getIntent();
        String status=i.getStringExtra("status");
        int id=i.getIntExtra("id",0);
        if (status.equals("accepted")){
            view.setText("congratolation!!!!1 you are accepted in this train ");
        }else {
            view.setText("OOH , we are sorry , you not accepted in this train ,we hope best wishes in another requests");

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request_status, menu);
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

package com.nozom.darrabny.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.nozom.darrabny.R;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.logout;

/**
 * Created by abdelgawad on 28/03/16.
 */
public class View_training extends AppCompatActivity implements View.OnClickListener {
    private TextView mobile,description,type,name,from,to;
    private Button enterToTaining;;
    String fromD,toD;
     private Intent i;
    BackendlessUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_training);
        user= Backendless.UserService.CurrentUser();
        mobile= (TextView) findViewById(R.id.Vtrain_mobile);
        description= (TextView) findViewById(R.id.Vabout_train);
        type= (TextView) findViewById(R.id.Vtrain_industry);
        name= (TextView) findViewById(R.id.Vtrain_name);
        from= (TextView) findViewById(R.id.Vtrain_from);
        to= (TextView) findViewById(R.id.Vtrain_to);
        i=getIntent();
        fromD=i.getStringExtra(Constants.TRAINING_FROM);
        toD=i.getStringExtra(Constants.TRAINING_TO);
        Log.e("from and to ",fromD + toD);
        mobile.setText("mobile : "+i.getStringExtra(Constants.TRAINING_MOBILE));
        description.setText("description : "+i.getStringExtra(Constants.TRAINING_DESCRIPTION));
        type.setText("type  : "+i.getStringExtra(Constants.TYPE));
        name.setText("name : "+i.getStringExtra(Constants.TRAINING_NAME));
        from.setText(" start date is : "+i.getStringExtra(fromD));
        to.setText(" end date is : "+i.getStringExtra(toD));
        enterToTaining= (Button) findViewById(R.id.enter_to_training);
        enterToTaining.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,TrainingForEntry.class);
        intent.putExtra(Constants.TRAINING_OBJECTID,i.getStringExtra(Constants.TRAINING_OBJECTID));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout logout=new logout(this);
            logout.logOut(user);
        }
        return true;
    }
}

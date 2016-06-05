package com.nozom.darrabny.company;

import android.content.Intent;
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
import com.nozom.darrabny.main.Constants;

public class Company_signup extends AppCompatActivity implements View.OnClickListener {
    private EditText company_name, company_email, company_phone, company_password, company_confirmPass;
     String name, email, password, confirm, phone, fax, industry;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_signup);
        company_name = (EditText) findViewById(R.id.company_name);
        company_email = (EditText) findViewById(R.id.company_email);
        company_phone = (EditText) findViewById(R.id.company_phone);
        company_password = (EditText) findViewById(R.id.company_password);
        company_confirmPass = (EditText) findViewById(R.id.company_confirmPassword);
        register = (Button) findViewById(R.id.company_register);
        register.setOnClickListener(this);
    }

    private void getInformation() {
        name = company_name.getText().toString();
        email = company_email.getText().toString();
        password = company_password.getText().toString();
        confirm = company_confirmPass.getText().toString();
        phone = company_phone.getText().toString();

    }

    @Override
    public void onClick(View v) {


        getInformation();
        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {



                    if (TextUtils.isEmpty(email)) {
                        company_email.setError(Constants.EMPTY_ERROR);
                    } else {
                        for (BackendlessUser user : users.getCurrentPage()) {
                            if (user.getEmail().equals(email)) {
                                company_email.setError(Constants.EMAIL_EXISTS);
                                break;
                            }
                        }

                 if (TextUtils.isEmpty(name)) {
                    company_name.setError(Constants.EMPTY_ERROR);

                } else if (TextUtils.isEmpty(phone)) {
                    company_phone.setError(Constants.EMPTY_ERROR);

                }else if (TextUtils.isEmpty(password)) {
                    company_password.setError(Constants.EMPTY_ERROR);

                } else if (TextUtils.isEmpty(confirm)) {
                    company_confirmPass.setError(Constants.EMPTY_ERROR);
                } else if (!password.equals(confirm)) {
                    company_confirmPass.setError(Constants.MATCH_PASS);
                } else {
                     if (TextUtils.isEmpty(email)){
              company_email.setError(Constants.EMPTY_ERROR);
                     }else {
                    BackendlessUser user1 = new BackendlessUser();
                    user1.setEmail(email);
                    user1.setPassword(password);
                    user1.setProperty(Constants.COMPANY_PHONE, phone);
                    user1.setProperty(Constants.TRAINING_NAME, name);
                    user1.setProperty(Constants.FAX, fax);
                    user1.setProperty(Constants.INDUSTRY, industry);
                      user1.setProperty(Constants.TYPE, Constants.COMPANY);
                    Backendless.UserService.register(user1, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser backendlessUser) {
                            Backendless.UserService.login(backendlessUser.getEmail(), backendlessUser.getPassword(), new BackendlessCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {

                                    startActivity(new Intent(Company_signup.this, Company_home_page.class));

                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {

                            company_email.setError(backendlessFault.toString());


                        }
                    });

                }
            }}

        }
        @Override
        public void handleFault (BackendlessFault backendlessFault){
            Toast.makeText(getApplicationContext(), backendlessFault.toString(), Toast.LENGTH_LONG).show();
        }
    }

    );

}



}






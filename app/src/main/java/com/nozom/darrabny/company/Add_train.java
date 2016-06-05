package com.nozom.darrabny.company;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.main.training;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by abdelgawad on 14/03/16.
 */
public class Add_train extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
   private EditText train_name,train_description ,train_mobile,startDate,endDate;
    private String name,description ,mobile,from,to,training_type;
    private Spinner train_type;
    private Date dateFrom,dateTo;
    private Button confirm;
    private Calendar calendar;
    private String[] types;
    private DatePickerDialog fromDatePickerDialog,toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int year, month, day;
    private  View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.add_train,container,false);
        train_name= (EditText) view.findViewById(R.id.train_name);
        train_type= (Spinner) view.findViewById(R.id.train_industry);
        types = getResources().getStringArray(R.array.training_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, types);
        train_type.setAdapter(adapter);
        train_type.setOnItemSelectedListener(this);
        train_description = (EditText) view.findViewById(R.id.about_train);
        train_mobile= (EditText) view.findViewById(R.id.train_mobile);
        startDate= (EditText) view.findViewById(R.id.training_from);
        endDate= (EditText) view.findViewById(R.id.training_to);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.requestFocus();
        endDate.setInputType(InputType.TYPE_NULL);
        endDate.requestFocus();
        confirm= (Button) view.findViewById(R.id.train_confirm);
        confirm.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();
        return view;
    }
    private void getInformation(){

        name=train_name.getText().toString();
        description =train_description .getText().toString();
        mobile=train_mobile.getText().toString();
        from=startDate.getText().toString().trim();
        to=endDate.getText().toString().trim();
        try {

            dateFrom = dateFormatter.parse(from);
           dateTo=dateFormatter.parse(to);
            Log.e("confirm","dateTo");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        // showDate(year, month+1, day);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (v==confirm) {
            view.findViewById(R.id.loadingPanel_add).setVisibility(View.VISIBLE);
            getInformation();
            Log.e("confirm","confirmed");
            final BackendlessUser user = Backendless.UserService.CurrentUser();
            Backendless.Persistence.save(new training(dateFrom,dateTo,name, description,
                    mobile, user.getEmail(), training_type, 0), new BackendlessCallback<training>() {
                @Override
                public void handleResponse(training training) {

                    Log.e("save", user.getEmail());
                    Toast.makeText(getActivity().getApplicationContext(),"your training is added ",Toast.LENGTH_LONG).show();
                    view.findViewById(R.id.loadingPanel_add).setVisibility(View.GONE);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                   Log.e("save",fault.toString());
                }
            });
        }else if (v==startDate){
            fromDatePickerDialog.show();
        }else if (v==endDate){
            toDatePickerDialog.show();
        }
    }
    private void setDateTimeField() {
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        training_type = types[position];

        Toast.makeText(this.getContext(), types[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
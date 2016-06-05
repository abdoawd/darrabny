package com.nozom.darrabny.student;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.CustomStudentAdapter;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelgawad on 31/03/16.
 */
public class Search extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private RadioGroup group;
    private EditText E_search;
    private Button B_search;
    private ListView lv;
    private List<training> trainings;
    private BackendlessUser user;
    private CustomStudentAdapter studentAdapter;
    private Spinner spinner;
    private String[] types;
    private ArrayAdapter<String> adapter;
    private String training_type;
private  View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.search, container, false);
        user = Backendless.UserService.CurrentUser();
        group = (RadioGroup) view.findViewById(R.id.RB_group_search);
        E_search = (EditText) view.findViewById(R.id.edit_search);
        lv = (ListView) view.findViewById(R.id.list_search);
        trainings = new ArrayList<training>();
        types = getResources().getStringArray(R.array.training_types);
        spinner = (Spinner) view.findViewById(R.id.spinner_search);
        RadioButton radioButton= (RadioButton) view.findViewById(R.id.radio_search_type);
        radioButton.setOnCheckedChangeListener(this);

        B_search = (Button) view.findViewById(R.id.B_search);
        B_search.setOnClickListener(this);
        return view;

    }

    public void search(String str) {
        StringBuilder where = new StringBuilder();
        if (str.equals(Constants.TYPE)) {
            where=where.append(Constants.TYPE).append("='").append(training_type).append("'");
        }else {
            if (TextUtils.isEmpty(E_search.getText())){
                view.findViewById(R.id.loadingPanel_search).setVisibility(View.GONE);
            }else {

                where = where.append(Constants.TRAINING_NAME).append("='").append(E_search.getText().toString()).append("'");
            }
        }
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(where.toString());
        Backendless.Persistence.of(training.class).find(dataQuery, new AsyncCallback<BackendlessCollection<training>>() {
            @Override
            public void handleResponse(BackendlessCollection<training> trainengsCollection) {
                trainings = trainengsCollection.getData();
                studentAdapter = new CustomStudentAdapter(getActivity(), trainings);
                lv.setAdapter(studentAdapter);
view.findViewById(R.id.loadingPanel_search).setVisibility(View.GONE);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.e("search", backendlessFault.toString());

            }
        });
    }

    @Override
    public void onClick(View v) {
        view.findViewById(R.id.loadingPanel_search).setVisibility(View.VISIBLE);
        int id=group.getCheckedRadioButtonId();
       RadioButton RB= (RadioButton) getActivity().findViewById(id);
        String s=RB.getText().toString();
        search(s);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        training_type = types[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId()==R.id.radio_search_type){
            if (buttonView.isChecked()){

                    E_search.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, types);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(this);

            }else {
                E_search.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.INVISIBLE);
            }
        }
    }
}
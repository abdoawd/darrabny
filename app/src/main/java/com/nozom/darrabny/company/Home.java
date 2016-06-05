package com.nozom.darrabny.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.CustomCompanyAdapter;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.MainActivity;
import com.nozom.darrabny.main.training;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelgawad on 14/03/16.
 */
public class Home extends Fragment {
    private ListView lv;
    private CustomCompanyAdapter adapter;
    private BackendlessUser user;
    private List<training> trainings;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);
        user = Backendless.UserService.CurrentUser();
        lv = (ListView) view.findViewById(R.id.company_trains);
        trainings = new ArrayList<training>();
        retrieve(user);
            Log.e("user",user.getEmail());
            return view;
    }

    private void retrieve(BackendlessUser user) {
       StringBuilder where=new StringBuilder();
        if (user==null)
        {

        }else {
        where= where.append(Constants.TRAINING_AUTHOREMAIL).append("='").append(user.getEmail()).append("'");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( where.toString() );
        Backendless.Data.of(training.class).find(dataQuery,new AsyncCallback<BackendlessCollection<training>>() {
            @Override
            public void handleResponse(BackendlessCollection<training> fileMappings) {
                //do operations
                trainings = fileMappings.getData();
                adapter = new CustomCompanyAdapter(getActivity(), trainings);
                lv.setAdapter(adapter);
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Log.e("trainings", trainings.get(0).getAuthorEmail());
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.e("trainings",backendlessFault.toString());
            }

        });
    }}
}

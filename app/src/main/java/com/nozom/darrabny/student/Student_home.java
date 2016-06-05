package com.nozom.darrabny.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.CustomStudentAdapter;
import com.nozom.darrabny.main.training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelgawad on 26/03/16.
 */
public class Student_home extends Fragment  {
    private List<training> trainings;
    private ListView lv;
    private View view;
    private CustomStudentAdapter adapter;
    RelativeLayout bar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.home, container, false);
        lv = (ListView) view.findViewById(R.id.company_trains);
        trainings = new ArrayList<training>();
        retrieve();
       bar= (RelativeLayout) view.findViewById(R.id.loadingPanel);
        return view;
    }

    public void retrieve() {
        Backendless.Data.of(training.class).find(new AsyncCallback<BackendlessCollection<training>>() {
            @Override
            public void handleResponse(BackendlessCollection<training> fileMappings) {
                //do operations
                trainings = fileMappings.getData();
                adapter = new CustomStudentAdapter(getActivity(), trainings);
                lv.setAdapter(adapter);
               bar.setVisibility(View.GONE);
              /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(getActivity(),View_training.class);
                        Log.e("item click","clicked");
                        i.putExtra("trainings", trainings.get(position));
                        Log.e("mobile",trainings.get(position).getMobile()+"");
                        startActivity(i);
                    }
                });*/
            }
            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.e("trainings", backendlessFault.toString());
            }
        });
    }
}

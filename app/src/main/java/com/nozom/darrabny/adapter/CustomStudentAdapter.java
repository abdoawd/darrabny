package com.nozom.darrabny.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nozom.darrabny.R;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.student.View_training;
import com.nozom.darrabny.main.training;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by abdelgawad on 26/03/16.
 */
public class CustomStudentAdapter extends BaseAdapter {
    private List<training> trainings;
    private static LayoutInflater inflater = null;
    private Activity context;

    public CustomStudentAdapter(Activity activity, List<training> t) {
        context = activity;
        /********** Take passed values **********/
        trainings = t;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return trainings.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        final CustomCompanyAdapter.ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.train_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new CustomCompanyAdapter.ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.view_train_name);
            holder.requests = (TextView) vi.findViewById(R.id.view_train_requests);
            holder.type = (TextView) vi.findViewById(R.id.view_train_type);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (CustomCompanyAdapter.ViewHolder) vi.getTag();

        if (trainings.size() <= 0) {
            holder.name.setText(Constants.NODATA);

        } else {
            /***** Get each Model object from Arraylist ********/


            /************  Set Model values in Holder elements ***********/
            //Calendar calendar=Calendar.getInstance();
            holder.name.setText(trainings.get(position).getName());

            if (Calendar.getInstance().getTime().after(trainings.get(position).getStartDate())) {
                holder.requests.setText("closed");
            } else {
                holder.requests.setText(Constants.TRAINING_STATUS);
            }
            holder.type.setText(trainings.get(position).getType());

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, trainings.get(position).getName(), Toast.LENGTH_LONG).show();
                    String from = trainings.get(position).getStartDate().toString();
                    String to = trainings.get(position).toString();
                    Intent i = new Intent(context.getApplicationContext(), View_training.class);
                    i.putExtra(Constants.TRAINING_FROM, from);
                    Log.e("date", from + "");
                    i.putExtra(Constants.TRAINING_TO, to);
                    Log.e("date", to + "");
                    i.putExtra(Constants.TRAINING_OBJECTID, trainings.get(position).getObjectId());
                    i.putExtra(Constants.TRAINING_MOBILE, trainings.get(position).getMobile());
                    i.putExtra(Constants.TRAINING_NAME, trainings.get(position).getName());
                    i.putExtra(Constants.TRAINING_DESCRIPTION, trainings.get(position).getDescription());
                    i.putExtra(Constants.TYPE, trainings.get(position).getType());
                    context.startActivity(i);

                }
            });
        }
        return vi;
    }


}

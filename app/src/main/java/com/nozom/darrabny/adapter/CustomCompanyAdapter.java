package com.nozom.darrabny.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nozom.darrabny.R;
import com.nozom.darrabny.company.Acception_home;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.training;

import java.util.List;

/**
 * Created by abdelgawad on 25/03/16.
 */
public class CustomCompanyAdapter extends BaseAdapter  {
    private List<training> trainings;
    private static LayoutInflater inflater=null;
    private Context context;
    public CustomCompanyAdapter(Activity a, List<training>t) {
context=a;
        /********** Take passed values **********/
        trainings=t;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater ) a.
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.train_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.view_train_name);
            holder.requests=(TextView)vi.findViewById(R.id.view_train_requests);
            holder.type=(TextView)vi.findViewById(R.id.view_train_type);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(trainings.size()<=0)
        {
            holder.name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/


            /************  Set Model values in Holder elements ***********/

            holder.name.setText(trainings.get(position).getName() );
            holder.requests.setText("requests : "+trainings.get(position).getRequests());
            holder.type.setText(trainings.get(position).getType());

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,Acception_home.class);
                    i.putExtra(Constants.TRAINING_OBJECTID,trainings.get(position).getObjectId());
                    context.startActivity(i);
                }
            });
        }
        return vi;
    }
    public static class ViewHolder{

        public TextView name;
        public TextView requests;
        public TextView type;
        public TextView discription;
        public TextView mobile;

    }
}

package com.nozom.darrabny.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nozom.darrabny.R;
import com.nozom.darrabny.company.RequestInformation;
import com.nozom.darrabny.main.request;

import java.util.List;

/**
 * Created by abdelgawad on 02/04/16.
 */
public class AdapteRequest extends BaseAdapter {
   private List<request> Requests;
   private Context context;
   private LayoutInflater inflater;
    private String trainingObjectId;
    public AdapteRequest(Context a, List<request> t,String trainingObject) {
        context=a;
        trainingObjectId=trainingObject;
        /********** Take passed values **********/
        Requests =t;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) a.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return Requests.size();
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
        CustomCompanyAdapter.ViewHolder holder;

        if(convertView==null){

            /****** Inflate tab item.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.train_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new CustomCompanyAdapter.ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.view_train_name);
            holder.requests=(TextView)vi.findViewById(R.id.view_train_requests);
            holder.type=(TextView)vi.findViewById(R.id.view_train_type);
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else {
            holder = (CustomCompanyAdapter.ViewHolder) vi.getTag();
        }
        if(Requests.size()<=0)
        {
            holder.name.setText("No Requests yet");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/


            /************  Set Model values in Holder elements ***********/

            holder.name.setText(Requests.get(position).getName() );
            holder.requests.setText(Requests.get(position).getGrade());
            holder.type.setText(Requests.get(position).getAcademicYear());
            Log.e("adapter", Requests.get(position).getGrade());
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,RequestInformation.class);
                    i.putExtra("request", Requests.get(position).getObjectId());
                    i.putExtra("training", trainingObjectId);
                    context.startActivity(i);
                }
            });
        }
        return vi;
    }
}

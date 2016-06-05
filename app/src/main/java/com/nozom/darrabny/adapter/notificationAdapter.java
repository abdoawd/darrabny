package com.nozom.darrabny.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nozom.darrabny.R;
import com.nozom.darrabny.main.Comment;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.student.View_training;
import com.nozom.darrabny.student.requestStatus;

import java.util.List;

/**
 * Created by abdelgawad on 23/04/16.
 */
public class notificationAdapter extends BaseAdapter {
    private List<Comment> comments;
    private Context context;
    private static LayoutInflater inflater = null;
    public notificationAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.size();
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

        if (comments.size() <= 0) {
            holder.name.setText(Constants.NODATA);

        } else {
            /***** Get each Model object from Arraylist ********/
            /************  Set Model values in Holder elements ***********/

            holder.name.setText(comments.get(position).getTraining().getName());
            holder.requests.setText(comments.get(position).getStatus());
            holder.type.setText(comments.get(position).getUser().getEmail());

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), requestStatus.class);
                    i.putExtra("status", comments.get(position).getStatus());
                    i.putExtra("id", comments.get(position).getId());
                    context.startActivity(i);

                }
            });
        }
        return vi;
    }
}

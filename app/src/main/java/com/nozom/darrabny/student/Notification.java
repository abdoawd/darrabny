package com.nozom.darrabny.student;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.BackendlessDataQuery;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.CustomStudentAdapter;
import com.nozom.darrabny.adapter.notificationAdapter;
import com.nozom.darrabny.main.Comment;
import com.nozom.darrabny.main.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelgawad on 14/03/16.
 */
public class Notification extends Fragment {
   private View view;
   private ListView notifications;
   private notificationAdapter adapter;
   private List<Comment> comments;
   private BackendlessUser currentUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification, container, false);
        Log.e("list","notification");

        notifications = (ListView) view.findViewById(R.id.notification_lv);
        currentUser = Backendless.UserService.CurrentUser();
        comments = new ArrayList<>();
        retrive();
        return view;
    }

    private void retrive() {
        StringBuilder where = new StringBuilder();
        if (currentUser.getEmail() == null) {

        } else {

            where = where.append("user.email").append("='").append(currentUser.getEmail()).append("'");

        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(where.toString());
        Backendless.Persistence.of(Comment.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Comment>>() {
            @Override
            public void handleResponse(BackendlessCollection<Comment> response) {
                comments = response.getData();
                Log.e("list", comments.size() + "");
                adapter = new notificationAdapter(comments, getContext());
                notifications.setAdapter(adapter);
                view.findViewById(R.id.loadingPanel_notification).setVisibility(View.GONE);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("list", fault.toString());
            }
        });
    }
    }
}


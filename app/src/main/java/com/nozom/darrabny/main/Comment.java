package com.nozom.darrabny.main;

import com.backendless.BackendlessUser;

import java.util.List;

/**
 * Created by abdelgawad on 23/04/16.
 */
public class Comment {
    private String objectId;
    private BackendlessUser user;
    private training training;
    private  String status;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BackendlessUser getUser() {
        return user;
    }

    public void setUser(BackendlessUser user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Comment() {
    }

    public Comment(BackendlessUser users, com.nozom.darrabny.main.training training,String status) {
        this.user = users;
        this.training = training;
        this.status=status;

    }

    public com.nozom.darrabny.main.training getTraining() {
        return training;
    }

    public void setTraining(com.nozom.darrabny.main.training training) {
        this.training = training;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


}

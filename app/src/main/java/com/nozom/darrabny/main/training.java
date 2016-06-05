package com.nozom.darrabny.main;

import android.os.Parcel;
import android.os.Parcelable;

import com.backendless.BackendlessUser;

import java.util.Date;
import java.util.List;

/**
 * Created by abdelgawad on 24/03/16.
 */
public class training implements Parcelable {
    private String name, description, mobile, type, authorEmail, objectId;
    private Date startDate,endDate;
    private int requests;
    private List<request>requestList;

    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }




    public List<request> getRequestList() {
        return requestList;
    }
    public void setRequestList(List<request> requestList) {
        this.requestList = requestList;
    }
    public training() {
    }
    public training(Date startDate,Date endDate,String name, String description, String mobile, String autherEmail, String type, int requests) {
        this.name = name;
        this.description = description;
        this.mobile = mobile;
        this.type = type;
        this.authorEmail = autherEmail;
        this.requests = requests;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    protected training(Parcel in) {
        name = in.readString();
        description = in.readString();
        mobile = in.readString();
        type = in.readString();
        authorEmail = in.readString();
        requests = in.readInt();
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getObjectId() {
        return objectId;
    }
    public static final Creator<training> CREATOR = new Creator<training>() {
        @Override
        public training createFromParcel(Parcel in) {
            return new training(in);
        }

        @Override
        public training[] newArray(int size) {
            return new training[size];
        }
    };
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getRequests() {
        return requests;
    }
    public void setRequests(int requests) {
        this.requests = requests;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAuthorEmail() {
        return authorEmail;
    }
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(authorEmail);
        dest.writeString(type);
        dest.writeString(mobile);
        dest.writeInt(requests);
    }
}
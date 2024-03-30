package edu.harrisburgu.cisc349.customerlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    protected String _id;

    protected String name;
    protected String address;
    protected String phone;
    protected List<String> comments;

    public Customer(JSONObject jsonData) throws JSONException {
        if (jsonData.has("_id")) {
            this.setId(jsonData.getString("_id"));
        }
        this.setName(jsonData.getString("name"));
        this.setAddress(jsonData.getString("address"));
        this.setPhone(jsonData.getString("phone"));
        if (jsonData.has("comments")) {
            JSONArray cmts = jsonData.getJSONArray("comments");
            if (null != cmts) {
                comments = new ArrayList<String>();
                for (int i = 0; i < cmts.length(); i++) {
                    comments.add(cmts.get(i).toString());
                }
            }
        }
        else {
            comments = new ArrayList<String>();
        }
    }


    public void setId(String id) {
        this._id = id;
    }
    public String getId() {
        return _id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getComments() {return comments;}
    public void setComments(List<String> comments){this.comments = comments;}
}

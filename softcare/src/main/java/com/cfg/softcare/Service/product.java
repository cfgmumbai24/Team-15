package com.cfg.softcare.Service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class product {

    private String name;
    private Fields fields;
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @JsonProperty("createTime")
    public Date getCreateTime() {
        return createTime;
    }

    @JsonProperty("createTime")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("updateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    @JsonProperty("updateTime")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

class Fields {
    private assignedTo assignedTo;
    private qty qty;
    private stability stability;
    private category category;
    private date date;
    private image image;
    private inferredClass inferredClass;

    // Getters and Setters
//    @JsonProperty("assignedTo")
//    public String getAssignedTo() {
//        return assignedTo;
//    }
//
//    @JsonProperty("assignedTo")
//    public void setAssignedTo(String assignedTo) {
//        this.assignedTo = assignedTo;
//    }
//
//    @JsonProperty("qty")
//    public Integer getQty() {
//        return qty;
//    }
//
//    @JsonProperty("qty")
//    public void setQty(Integer qty) {
//        this.qty = qty;
//    }
//
//    @JsonProperty("stability")
//    public Integer getStability() {
//        return stability;
//    }
//
//    @JsonProperty("stability")
//    public void setStability(Integer stability) {
//        this.stability = stability;
//    }
//
//    @JsonProperty("category")
//    public String getCategory() {
//        return category;
//    }
//
//    @JsonProperty("category")
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    @JsonProperty("date")
//    public String getDate() {
//        return date;
//    }
//
//    @JsonProperty("date")
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    @JsonProperty("image")
//    public String getImage() {
//        return image;
//    }
//
//    @JsonProperty("image")
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    @JsonProperty("inferredClass")
//    public String getInferredClass() {
//        return inferredClass;
//    }
//
//    @JsonProperty("inferredClass")
//    public void setInferredClass(String inferredClass) {
//        this.inferredClass = inferredClass;
//    }

    @Override
    public String toString() {
        return "Fields{" +
                "assignedTo='" + assignedTo + '\'' +
                ", qty=" + qty +
                ", stability=" + stability +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", inferredClass='" + inferredClass + '\'' +
                '}';
    }
}

class assignedTo{
    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "assignedTo{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}


class image{
    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "assignedTo{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}

class category{
    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "assignedTo{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}

class inferredClass{
    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "assignedTo{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}

class qty{
    private int integerValue;

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    @Override
    public String toString() {
        return "qty{" +
                "integerValue=" + integerValue +
                '}';
    }
}

class date{
    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "assignedTo{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}
class stability{
    private int integerValue;

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    @Override
    public String toString() {
        return "qty{" +
                "integerValue=" + integerValue +
                '}';
    }
}


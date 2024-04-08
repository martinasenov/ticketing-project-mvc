package com.cydeo.enums;

public enum Gender {

    MALE("Male"), FEMEALE("Female");

    private String value;

    Gender(String value){

        this.value=value;
    }

    public String getValue(){

        return value;
    }
}

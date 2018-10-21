package com.example.amhso.nlpapp_doctor.otherclass;



public class StringAndIntegers {

    private String name;
    private int integer;
    private String Price;
    private String Address;
    private String phone;



    public StringAndIntegers(int integer, String name, String Price, String Address, String Phone){
        this.integer=integer;
        this.name=name;
        this.Price=Price;
        this.Address=Address;
        this.phone=Phone;

    }


    public int getInteger(){
        return this.integer;
    }

    public String getPrice(){return this.Price;}

    public String getAddress(){return this.Address;}

    public String getPhone(){return this.phone;}

    public String getName(){return this.name;  }




}
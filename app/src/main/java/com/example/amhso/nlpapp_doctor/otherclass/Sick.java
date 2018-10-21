package com.example.amhso.nlpapp_doctor.otherclass;

/**
 * Created by amhso on 18/05/2018.
 */

public class Sick {

    private String name;
    private String id;

    public Sick(String name,String phone){
        this.name=name;
        this.id=phone;

    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }
}

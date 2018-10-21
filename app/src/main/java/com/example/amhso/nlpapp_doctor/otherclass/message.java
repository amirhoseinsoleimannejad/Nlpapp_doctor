package com.example.amhso.nlpapp_doctor.otherclass;

/**
 * Created by amhso on 14/05/2018.
 */

public class message {

    public String text;
    public String date;
    public String time;
    public Boolean self;
    public String img;
    public Boolean imageOrtext;


    public message(String text, String date, String time, Boolean self, String img, Boolean imageOrtext){
        this.text=text;
        this.date=date;
        this.time=time;
        this.self=self;
        this.img=img;
        this.imageOrtext=imageOrtext;
    }




    public String getText(){
        return this.text;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){
        return this.time;
    }

    public String getImg(){
        return this.img;
    }

    public Boolean getSelf(){
        return this.self;
    }



    public Boolean getImageOrtext(){
        return this.imageOrtext;
    }
}

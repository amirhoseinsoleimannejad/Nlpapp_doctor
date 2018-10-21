package com.example.amhso.nlpapp_doctor.otherclass;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amhso.nlpapp_doctor.R;



public class CustomAdapter extends ArrayAdapter<StringAndIntegers> {

    private final Activity context;
    private final StringAndIntegers itemname[];

    public CustomAdapter(Activity context, StringAndIntegers itemname []) {
        super(context, R.layout.activity_listview, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(int position, View view, ViewGroup parent) {





        View listItem = view;
        MyWrapper wrapper = null;


        try {

            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            } else {
                wrapper = (MyWrapper) listItem.getTag();
            }

            String namedoctor = itemname[position].getName();

            wrapper.getName().setText(namedoctor);



            String price = itemname[position].getPrice();

            wrapper.getPrice().setText(price);



            String address = itemname[position].getAddress();

            wrapper.getAddress().setText(address);




            String mobile = itemname[position].getPhone();
            wrapper.getPhone().setText(mobile);



//            String text = itemname[position].getText();
//            wrapper.getText().setText(text);



        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }



        return listItem;


    };







    class MyWrapper
    {
        private View base;
        private TextView name;
        private TextView phone;
        private TextView Address;
        private TextView Price;
        private TextView integer;
        private TextView text;


        public MyWrapper(View base)
        {
            this.base = base;
        }



        public TextView getName(){
            if(name == null){
                name = (TextView) base.findViewById(R.id.label);
            }
            return name;
        }
        public TextView getPhone(){
            if(phone == null){
                phone = (TextView) base.findViewById(R.id.label1);
            }
            return phone;
        }
        public TextView getAddress(){
            if(Address == null){
                Address = (TextView) base.findViewById(R.id.label2);
            }
            return Address;
        }
        public TextView getPrice(){
            if(Price == null){
                Price = (TextView) base.findViewById(R.id.label3);
            }
            return Price;
        }
//        public TextView getInteger(){
//            if(integer == null){
//                integer = (TextView) base.findViewById(R.id.label4);
//            }
//            return integer;
//        }

//        public TextView getText(){
//            if(integer == null){
//                text = (TextView) base.findViewById(R.id.text);
//            }
//            return text;
//        }
    }
}

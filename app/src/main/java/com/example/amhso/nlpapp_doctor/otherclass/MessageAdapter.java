package com.example.amhso.nlpapp_doctor.otherclass;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.amhso.nlpapp_doctor.R;
import com.example.amhso.nlpapp_doctor.ShowImageChatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MessageAdapter extends ArrayAdapter<message> {

    private final Activity context;
    private final List<message> itemname;

    public MessageAdapter(Activity context, List<message> itemname) {
        super(context, R.layout.listmessage, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(int position, View view, ViewGroup parent) {




        View listItem = view;
        MyWrapper wrapper = null;


        try {


            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.listmessage, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            } else {
                wrapper = (MyWrapper) listItem.getTag();
            }


            Log.i("ttttttttttttttttttttt", "adapter: "+itemname.get(position).getText());




            if (itemname.get(position).getSelf()){

//                wrapper.getText_person().setVisibility(View.GONE);
//                wrapper.getText().setVisibility(View.VISIBLE);
//
//
//                wrapper.getProfile().setVisibility(View.GONE);
//                wrapper.getSelf_profile().setVisibility(View.VISIBLE);


                wrapper.getOther().setVisibility(View.GONE);
                wrapper.getSelf().setVisibility(View.VISIBLE);



                String body_message = itemname.get(position).getText();
                String date = itemname.get(position).getDate();
                String time = itemname.get(position).getTime();

                body_message += "\n";
                body_message += date;
                body_message += "\n";
                body_message += time;


                if(itemname.get(position).getImageOrtext()){
                    wrapper.getText().setVisibility(View.GONE);
                    wrapper.getImage_self().setVisibility(View.VISIBLE);
                    ImageView image_self=wrapper.getImage_self();
                    Picasso.with(G.activity)
                            .load(G.ServerImg+itemname.get(position).getText())
                            .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                            .into(image_self);

                    Log.i("iiiiiiiiiiiiiiii", "iiiiiiiii: "+G.ServerImg+itemname.get(position).getText());

                }
                else{
                    wrapper.getImage_self().setVisibility(View.GONE);
                    wrapper.getText().setVisibility(View.VISIBLE);
                    wrapper.getText().setText(body_message);
                }



                ImageView img_profile=wrapper.getSelf_profile();
                Picasso.with(G.activity)
                        .load(G.ServerImg+itemname.get(position).getImg())
                        .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                        .into(img_profile);
//                Log.i("imageimageimage", "11111111: "+G.ServerImg+itemname.get(position).getImg());

            }


            else{

                wrapper.getOther().setVisibility(View.VISIBLE);
                wrapper.getSelf().setVisibility(View.GONE);

                String body_message = itemname.get(position).getText();
                String date = itemname.get(position).getDate();
                String time = itemname.get(position).getTime();

                body_message += "\n";
                body_message += date;
                body_message += "\n";
                body_message += time;

                if(itemname.get(position).getImageOrtext()){
                    wrapper.getText_person().setVisibility(View.GONE);
                    wrapper.getImage_person().setVisibility(View.VISIBLE);
                    ImageView image_person=wrapper.getImage_person();
                    Picasso.with(G.activity)
                            .load(G.ServerImg+itemname.get(position).getText())
                            .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                            .into(image_person);

                }
                else{
                    wrapper.getImage_person().setVisibility(View.GONE);
                    wrapper.getText_person().setVisibility(View.VISIBLE);
                    wrapper.getText_person().setText(body_message);
                }


                ImageView img=wrapper.getProfile();
                Picasso.with(G.activity)
                        .load(G.ServerImg+itemname.get(position).getImg())
                        .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                        .into(img);

                Log.i("imageimageimage", "2222222: "+G.ServerImg+itemname.get(position).getImg());
            }



            wrapper.getImage_person().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    View parentRow = (View) v.getParent();
                    parentRow= (View) parentRow.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);


                    Intent i = new Intent(G.activity, ShowImageChatActivity.class);
                    i.putExtra("image",G.ServerImg+itemname.get(position).getText());
                    G.activity.startActivity(i);


                }
            });



            wrapper.getImage_self().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    View parentRow = (View) v.getParent();
                    parentRow= (View) parentRow.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);


                    Intent i = new Intent(G.activity, ShowImageChatActivity.class);
                    i.putExtra("image",G.ServerImg+itemname.get(position).getText());
                    G.activity.startActivity(i);

                }
            });


        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }

        return listItem;

    };







    class MyWrapper
    {
        private View base;
        private TextView message;
        private TextView message_person;
        private ImageView self_profile;
        private ImageView profile;
        private ImageView image_self;
        private ImageView image_person;
        private RelativeLayout self;
        private RelativeLayout other;


        public MyWrapper(View base)
        {
            this.base = base;
        }



        public TextView getText(){
            if(message == null){
                message = (TextView) base.findViewById(R.id.text);
//                Typeface yekan_font = Typeface.createFromAsset(G.activity.getAssets(), "B Nazanin Bold_p30download.com.ttf");
//                message .setTypeface(yekan_font);

            }
            return message;
        }




        public TextView getText_person(){
            if(message_person == null){
                message_person = (TextView) base.findViewById(R.id.text_person);
//                Typeface yekan_font = Typeface.createFromAsset(G.activity.getAssets(), "B Nazanin Bold_p30download.com.ttf");
//                message_person .setTypeface(yekan_font);

            }
            return message_person;
        }




        public ImageView getSelf_profile(){
            if(self_profile == null){
                self_profile = (ImageView) base.findViewById(R.id.image_profile_self);

            }
            return self_profile;
        }





        public ImageView getProfile(){
            if(profile == null){
                profile = (ImageView) base.findViewById(R.id.image_profile);
            }
            return profile;
        }




        public ImageView getImage_self(){
            if(image_self == null){
                image_self = (ImageView) base.findViewById(R.id.image_self);
            }
            return image_self;
        }




        public ImageView getImage_person(){
            if(image_person == null){
                image_person = (ImageView) base.findViewById(R.id.image_person);
            }
            return image_person;
        }




        public RelativeLayout getSelf(){
            if(self == null){
                self = (RelativeLayout) base.findViewById(R.id.self);
            }
            return self;
        }



        public RelativeLayout getOther(){
            if(other == null){
                other = (RelativeLayout) base.findViewById(R.id.other);
            }
            return other;
        }

    }
}

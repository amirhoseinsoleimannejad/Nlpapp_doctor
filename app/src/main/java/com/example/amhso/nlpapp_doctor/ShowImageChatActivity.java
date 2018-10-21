package com.example.amhso.nlpapp_doctor;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.amhso.nlpapp_doctor.otherclass.G;
import com.squareup.picasso.Picasso;

public class ShowImageChatActivity extends AppCompatActivity {

    public String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_chat);


        G.activity=this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            image = bundle.getString("image");

        } else {
            finish();
        }



        ImageView img=(ImageView) findViewById(R.id.image);
                Picasso.with(G.activity)
                        .load(image)
                        .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                        .into(img);
    }
}

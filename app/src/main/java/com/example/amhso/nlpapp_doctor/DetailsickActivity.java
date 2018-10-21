package com.example.amhso.nlpapp_doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amhso.nlpapp_doctor.otherclass.G;
import com.example.amhso.nlpapp_doctor.otherclass.Sick;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsickActivity extends AppCompatActivity {

    String id_sick;
    TextView detail_sick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsick);


        G.activity=this;

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_sick=bundle.getString("id_sick");

        }
        else{
            finish();
        }

        detail_sick=(TextView) findViewById(R.id.detail_sick);


        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "detail_sick");



        Button mEmailSignInButton = (Button) findViewById(R.id.chat_message);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(G.activity, ChatActivity.class);
                myIntent.putExtra("id_sick",id_sick);
                startActivity(myIntent);

            }
        });
    }







    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        public ProgressDialog progressDialog;



        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);


            try{
                progressDialog.dismiss();

            }
            catch (Exception e){

            }



            try {


                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("sick");





                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String phone = c.getString("phone");
                    String mobile = c.getString("mobile");

                    String national = c.getString("national_number");
                    String address = c.getString("address");
                    String birth = c.getString("birth");
                    String sick_des = c.getString("sick_des");
                    String img = c.getString("img");




                    String text =name+"\n"+phone+"\n"+mobile+"\n"+national+"\n"+address+"\n"+birth+"\n"+sick_des;

                    detail_sick.setText(text);

                    ImageView image=(ImageView) G.activity.findViewById(R.id.profile_images);
                    Picasso.with(G.activity)
                            .load(G.ServerImg+img)
                            .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                            .into(image);

                }







            } catch (Exception e) {


                Log.i("eeeeeee", "eeeeeee: " + e.toString());
//
                Toast.makeText(G.activity, "شما هیچ بیماری ندارید", Toast.LENGTH_LONG).show();


            }


        }






        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(G.activity,
                    "لطفاً منتظر بمانید",
                    "با تشکر");
        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                Log.i("urluuuuuuuuuuuuuuu", "doInBackground: "+params[0]);

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data

                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);


                nameValuePairs = new ArrayList<NameValuePair>(2);



                nameValuePairs.add(new BasicNameValuePair("id_sick",id_sick.trim()));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return "0";
        }
    }
}

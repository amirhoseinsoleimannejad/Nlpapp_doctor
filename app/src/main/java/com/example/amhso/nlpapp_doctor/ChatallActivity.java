
package com.example.amhso.nlpapp_doctor;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.amhso.nlpapp_doctor.otherclass.G;
import com.example.amhso.nlpapp_doctor.otherclass.MessageAdapter;
import com.example.amhso.nlpapp_doctor.otherclass.message;
import com.example.amhso.nlpapp_doctor.service.MessageService;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class ChatallActivity extends AppCompatActivity {




    private  ListView listView;
    private static Boolean MainactivityIsStart=false;
    private static List<message> listmessage;
    private static MessageAdapter messageAdapter;
    private static final int CAMERA_PIC_REQUEST = 2500;
    private String text="";
    private Bitmap bitmap;
    private Boolean start=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatall);



        G.activity=this;
        MainactivityIsStart=true;


        listView=(ListView) findViewById(R.id.list_message);
        listmessage = new ArrayList<message>();


        messageAdapter = new MessageAdapter(G.activity,listmessage);
        listView.setAdapter(messageAdapter);





        ImageView send= (ImageView) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                EditText ed=(EditText) findViewById(R.id.text_message);
                text=ed.getText().toString();


                message m=new message(text,"","",true,"",false);
                listmessage.add(m);

                messageAdapter.notifyDataSetChanged();


                HttpPostAsyncTask task = new HttpPostAsyncTask();
                task.execute(G.urlserver + "insert_message");

                ed.setText(null);

            }
        });






        ImageView imageUpload= (ImageView) findViewById(R.id.aaaa);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });

        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "fetch_message_all");

        new Thread( new Runnable() {
            @Override
            public void run() {


                while (start){

                    try {


                        HttpPostAsyncTask task = new HttpPostAsyncTask();
                        task.execute(G.urlserver + "fetch_message_all");

                        Thread.sleep(10000);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        }).start();



    }






    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            if(data!=null){
                bitmap = (Bitmap) data.getExtras().get("data");
                uploadImage bt=new uploadImage();
                bt.execute();
            }
        }
    }





    public static Boolean MainIsStart(){
        return MainactivityIsStart;
    }





    public static void getMessage(String id,String message){
        message m=new message(message,"","",false,"",false);
        listmessage.add(m);
        messageAdapter.notifyDataSetChanged();
    }










    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;



        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);


            listmessage.clear();


            message m_array[];

            try {


                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("message_all");


                m_array=new message[contacts.length()];

                int k=contacts.length();
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    String text = c.getString("text");
                    String date = c.getString("date");
                    String time = c.getString("time");
                    String image = c.getString("img");
                    String id_user = c.getString("id_user");
                    String imageOrtext = c.getString("imageOrtext");

                    Boolean imageOtextB=false;
                    if(imageOrtext.equals("2")){
                        imageOtextB=true;
                    }
                    else if(imageOrtext.equals("1")){
                        imageOtextB=false;
                    }
                    k--;

                    SharedPreferences sharedpreferences = getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);

                    if(id_user.equals(sharedpreferences.getString(G.id_user ,"0"))){
                        message m=new message(text,date,time,true,image,imageOtextB);
                        m_array[k]=m;
                    }
                    else{
                        message m=new message(text,date,time,false,image,imageOtextB);
                        m_array[k]=m;
                    }
                }


                for (int i = 0; i < m_array.length; i++) {
                    listmessage.add(m_array[i]);
                }



                messageAdapter.notifyDataSetChanged();


            }
            catch (Exception e){


                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }

        }






        @Override
        protected void onPreExecute() {


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
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("message",text.trim()));

                SharedPreferences sharedpreferences = getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);

                nameValuePairs.add(new BasicNameValuePair("id_user", sharedpreferences.getString(G.id_user ,"0").trim()));

                nameValuePairs.add(new BasicNameValuePair("type","2".trim()));


//                Log.i("dddddddddd", "doInBackground: "+shpref.getString("id_user","-1").trim());
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














    private class uploadImage extends AsyncTask<Void,Void,String>{



        protected void onPreExecute(){

        }
        protected String doInBackground(Void...params){
            String text="";

            try{
                SharedPreferences sharedpreferences = getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);

                // Create HttpClitent instance
                HttpClient httpClient = new DefaultHttpClient();
                // Create HttpPost instance with specifying php file on server to handle file uploading
                HttpPost httpPostRequest = new HttpPost(G.urlserver+"chat_upload_doctor?id_user="+sharedpreferences.getString(G.id_user ,"0").trim());


                // Read image file and convert it to byte array
//                Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/Rose.jpg");

                ByteArrayOutputStream bo=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 30, bo);
                byte[] data=bo.toByteArray();
                // Specify HttpClient parameters
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                // Create InputStreamBody instance with specifying image data destination file name
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "/index_files/sick.jpg");
                // Create MultipartEntityBuilder instance
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                // Set multi-part mode
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                // Attach image data in InputStreamBody instance to MultipartEntityBuilder instance
                multipartEntity.addPart("img_file", inputStreamBody);
                // Set MultipartEntityBuilder instance to HttpPost
                httpPostRequest.setEntity(multipartEntity.build());


                // Start uploading the image and get the result from remote server
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                text=httpClient.execute(httpPostRequest, responseHandler);
                return text;
            }catch(Exception e){e.printStackTrace();}
            return null;
        }


        protected void onPostExecute(String result){

            message m=new message(result,"","",true,"",true);
            listmessage.add(m);
            messageAdapter.notifyDataSetChanged();

        }
    }
}

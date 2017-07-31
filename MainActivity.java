package com.example.arief.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    ListView list;
     static InputStream newsdata;
    String[] head=new String[40];
    String[] img1=new String[40];
    String[] content=new String[20];
    String[] description=new String[20];
    String result=null;
    String data=null;
    ProgressDialog progressDialog;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sec=new Intent(getApplicationContext(),secondactivity.class);
                sec.putExtra("url",content[position]);
                startActivity(sec);
            }
        });

        new mytask().execute("");

    }
    private class mytask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] params) {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpPost = new HttpGet( "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=923645fc1af4487d9ac7e99fc503e1f6");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                newsdata=httpEntity.getContent();
            }

            catch (final Exception e)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(newsdata, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((data = buffer.readLine()) != null) {
                    sb.append(data+"\n");
                }
                newsdata.close();
                result = sb.toString();
            }catch (final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Sorry Unnable to connect to the Network", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            try {
                JSONObject fon=new JSONObject(result);
                JSONArray fin=fon.getJSONArray("articles");
                for (i=0;i<fin.length();i++)
                {
                    JSONObject c=fin.getJSONObject(i);
                    head[i]=c.getString("title");
                    img1[i]=c.getString("urlToImage");
                    content[i]=c.getString("url");
                    description[i]=c.getString("description");

                }

            }catch (final Exception e)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Connect to the network try again",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Fetching news....");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            CustomAdapter custom=new CustomAdapter();
            list.setAdapter(custom);
        }
    }


    private class CustomAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return i;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.newslay,null);
            ImageView img=(ImageView)convertView.findViewById(R.id.imageView1);
            TextView txt=(TextView)convertView.findViewById(R.id.text1);
            TextView txt1=(TextView)convertView.findViewById(R.id.text2);
            txt.setText(head[position]);
            txt1.setText(description[position]);
            Picasso.with(getApplicationContext()).load(img1[position]).placeholder(R.drawable.load).error(R.drawable.error).resize(1000,300).into(img);
            return convertView;

        }

    }








}





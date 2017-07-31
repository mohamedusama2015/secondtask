package com.example.arief.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class secondactivity extends AppCompatActivity {
    WebView wv;
    String url;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        progressDialog=new ProgressDialog(secondactivity.this);
        wv=(WebView)findViewById(R.id.wb);
        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }
        });
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        wv.loadUrl(url);
    }

}

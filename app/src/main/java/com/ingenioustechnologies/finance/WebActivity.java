package com.ingenioustechnologies.finance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class WebActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    int once = 0;
    String m2,username,password,url;
    LottieAnimationView load,nosignal;
    WebView wv_webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");
        url=intent.getStringExtra("url");

        load=findViewById(R.id.f_mainload);

        nosignal=findViewById(R.id.f_mainnosignal);
        load.setVisibility(View.VISIBLE);

        m2="http://projecting.ingenious-technologies.com/finance_app/"+url;
        wv_webview = (WebView) findViewById(R.id.appview);
        wv_webview.getSettings().setJavaScriptEnabled(true);
        wv_webview.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wv_webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('main-sidebar')[0].style.display='none'; " +
                        "document.getElementsByClassName('sidebar-toggle')[0].style.display='none';" +
                        "document.getElementsByClassName('logo')[0].style.display='none'; " +
                        "document.getElementsByClassName('logout')[0].style.display='none';})()");
                if (once == 0) {

                    try {
                        wv_webview.loadUrl(m2);
                    } catch (Exception ru) {
                        ru.printStackTrace();
                        Toast.makeText(getApplicationContext(), "try again later", Toast.LENGTH_LONG).show();
                    }
                    load.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Please Wait....!", Toast.LENGTH_LONG).show();
                    wv_webview.setVisibility(View.VISIBLE);

                    once = 1;
                }

            }
        });
        //wv_webview.setWebViewClient(new WebViewClient());
//        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        wv_webview.getSettings().setUserAgentString(newUA);
        String linkss = "http://projecting.ingenious-technologies.com/finance_app/login?username="+username+"&password="+password+"";
//        Log.d("webviewtest", m2);

        wv_webview.setVisibility(View.INVISIBLE);
        load.setVisibility(View.VISIBLE);
        try {
            wv_webview.loadUrl(linkss);
        } catch (Exception r) {
            r.printStackTrace();
            Toast.makeText(getApplicationContext(), "try again later", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        if (wv_webview.canGoBack()) {
            wv_webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            nosignal.setVisibility(View.INVISIBLE);
        } else {
            nosignal.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}

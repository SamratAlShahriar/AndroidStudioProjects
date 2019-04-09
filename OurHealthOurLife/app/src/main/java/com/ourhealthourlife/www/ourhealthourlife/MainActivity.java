package com.ourhealthourlife.www.ourhealthourlife;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Loading", "Please wait", true);

        try {
            webView = (WebView) findViewById(R.id.webViewOHOL);
            webView.loadUrl("http://www.ourhealthourlife.com");

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


            //for open link(url) in same WebView
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());
                    return false;
                }
            });

            //for progress loading popup
            webView.setWebViewClient( new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                   // Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    pd.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    pd.dismiss();
                    String webUrl = webView.getUrl();
                }
            });


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


package com.liulei1947.photos.PhotosView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import cn.bmob.im.inteface.DownloadListener;

import com.bmob.im.demo.config.BmobConstants;
import com.liulei1947.photos.ResideMenu.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * This sample shows how to use ActionBar-PullToRefresh with a
 * {@link android.webkit.WebView WebView}, and manually creating (and attaching) a
 * {@link PullToRefreshLayout} to the view.
 */
public class WebViewActivity extends SwipeBackActivity implements OnRefreshListener {

    private PullToRefreshLayout mPullToRefreshLayout;

    private WebView mWebView;
    String htmlUrl;
    String title;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle bundle = getIntent().getExtras();
      	title=bundle.getString(BmobConstants.PhotosTitle);
      	setTitle(title);
      	htmlUrl = bundle.getString(BmobConstants.htmlUrl);
        // Find WebView and get it ready to display pages
      
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(new SampleWebViewClient());
        // Now find the PullToRefreshLayout and set it up
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(this)
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

        // Finally make the WebView load something...
        mPullToRefreshLayout.setRefreshing(true);
        mWebView.loadUrl(htmlUrl);
    }

    @Override
    public void onRefreshStarted(View view) {
        // Here we just reload the webview
    	mWebView.clearCache(true);
        mWebView.reload();
    }

    private class SampleWebViewClient extends WebViewClient {
 
    	public void onReceivedError(WebView view,int erroeCode,String description,String failingUrl){
    	
    			if(erroeCode==-2){
    				Toast.makeText(getApplication(), description+"Ê©Ö÷Çë»Ø°É ", Toast.LENGTH_LONG).show();
    				view.loadUrl("file:///android_asset/404error.html");
    			}
    		    
			}
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Return false so the WebView loads the url
        	 if (!mPullToRefreshLayout.isRefreshing()) {
                 mPullToRefreshLayout.setRefreshing(true);
             }else{
            	 mPullToRefreshLayout.setRefreshComplete();
            	 mPullToRefreshLayout.setRefreshing(true);
             }
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            
           
            // If the PullToRefreshAttacher is refreshing, make it as complete
            if (mPullToRefreshLayout.isRefreshing()) {
                mPullToRefreshLayout.setRefreshComplete();
            }
        }
        
        //************************
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
    //*********************************
    @Override
	public boolean onKeyDown(int keyCode,KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode== KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		} 
		return super.onKeyDown(keyCode, event);
	}
    
    
}

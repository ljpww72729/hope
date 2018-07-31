package cc.lkme.hope.utils;

import android.app.Application;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class WebViewUtils {

    Application application;
    @Inject
    public NetWorkUtils netWorkUtils;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @Inject
    public WebViewUtils(Application application) {
        this.application = application;
    }

    public void configWebView(WebView webView, WebSettings webSettings) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        webSettings.setJavaScriptEnabled(true);
        // 支持多窗口，处理弹出窗口问题，比如prompt弹出式窗口
        // 如果设置为true，必须同时实现onCreateWindow方法
        // 参考：https://stackoverflow.com/questions/8578332/webview-webchromeclient-method-oncreatewindow-not-called-for-target-blank/11280814#11280814
        webSettings.setSupportMultipleWindows(false);

        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setHorizontalScrollBarEnabled(false);//水平滑动条不显示
        webSettings.setBuiltInZoomControls(false); // 显示放大缩小
        webSettings.setSupportZoom(false); // 可以缩放
        webSettings.setUseWideViewPort(true);// 支持viewport元标签
        webSettings.setLoadWithOverviewMode(true); // 缩小页面以适应WebView的大小
        webSettings.setDomStorageEnabled(true);//设置支持html5本地存储，有些h5页面服务器做了缓存，webview控件也要设置，否则显示不出来页面
        String cacheDirPath = application.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        Timber.d("cacheDirPath=" + cacheDirPath);

        //设置数据库缓存路径
        webSettings.setDatabaseEnabled(true);

        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);
        if (netWorkUtils.isNetworkConnected()) {
            // 如果有网络则使用默认加载模式，有缓存并且没有失效则走缓存，否则走网络
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 如果有缓存，无论缓存是否失效都使用缓存资源，否则从网络加载
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAllowFileAccess(true);// 设置可以访问缓存文件

        // Enable third-party cookies if on Lolipop.
        // 不允许第三方设置cookies
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptThirdPartyCookies(webView, true);
//        }

        // 参考：https://stackoverflow.com/questions/30493567/webview-setdefaultzoom-deprecated/30496247#30496247
        webView.setInitialScale(0);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //设置listview、webview中滚动拖动到顶部或者底部时的阴影效果
//        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        Timber.d("WebView 配置完成！");
    }
}

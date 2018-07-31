package cc.lkme.hope.components.webview;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import timber.log.Timber;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.N && overrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && overrideUrlLoading(view, request.getUrl().toString());
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    public void catchUriScheme(WebView view, String url){

    }

    private boolean overrideUrlLoading(WebView view, String url) {
        Timber.d("shouldOverrideUrlLoading: " + url);
        //重写该方法是为了处理uri scheme,对于uri scheme则直接唤起APP
        //去掉回车、换行、tab
        String stray_spacing = "[\n\r\t\\p{Zl}\\p{Zp}\u0085]+";
        url = url.trim();
        url = url.replaceAll(stray_spacing, "");
        String rfc2396regex = "^(([a-zA-Z][a-zA-Z0-9\\+\\-\\.]*)://)(([^/?#]*)?([^?#]*)(\\?([^#]*))?)?(#(.*))?";
        String http_scheme_slashes = "^(https?://)/+(.*)";
        //(?i)后面的匹配不区分大小写
        String all_schemes_pattern = "(?i)^(http|https|ftp|mms|rtsp|wais)://.*";
        if (url.matches(all_schemes_pattern)) {
            view.loadUrl(url);
            return true;
        }
        if (url.matches(rfc2396regex)) {
            catchUriScheme(view, url);
            return true;
        }
        return true;
    }
}

package cc.lkme.hope.main.news.detail;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import javax.inject.Inject;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.components.webview.CustomWebChromeClient;
import cc.lkme.hope.components.webview.CustomWebViewClient;
import cc.lkme.hope.data.snssdk.Constants;
import cc.lkme.hope.databinding.DetailFragBinding;
import cc.lkme.hope.utils.AppExecutors;
import cc.lkme.hope.utils.AutoClearedValue;
import cc.lkme.hope.utils.SPUtils;
import cc.lkme.hope.utils.WebViewUtils;
import timber.log.Timber;

public class DetailFragment extends BaseFragment {

    private AutoClearedValue<DetailFragBinding> binding;
    private String articleUrl;
    @Inject
    public WebViewUtils webViewUtils;
    @Inject
    public SPUtils spUtils;
    @Inject
    public AppExecutors appExecutors;

    public static DetailFragment newInstance(Bundle bundle) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.detail_frag, false);
        DetailFragBinding detailFragBinding = DetailFragBinding.bind(root);
        binding = new AutoClearedValue<>(this, detailFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleUrl = getArguments().getString(Constants.ARTICLE_URL);
        initWebView();
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_back);
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.share:
                    Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        Timber.d("configWebView: loadurl === %s", articleUrl);
        WebSettings webSettings = binding.get().webView.getSettings();
        webViewUtils.configWebView(binding.get().webView, webSettings);
        binding.get().webView.addJavascriptInterface(new WebAppInterface(getActivity(), binding.get().webView, spUtils), "Android");
        binding.get().webView.setWebViewClient(new CustomWebViewClient() {

            @Override
            public void catchUriScheme(WebView view, String url) {
                showErrorDefault(v -> view.loadUrl(articleUrl));
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
                binding.get().progressBar.setVisibility(View.VISIBLE);
                webSettings.setBlockNetworkImage(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showContent();
//                if (!loadFailed) {
//                    articleUrl = url;
//                }
                webSettings.setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }


            @Override
            public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
                // 处理错误的情况
                showErrorDefault(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.loadUrl(articleUrl);
                    }
                });
                super.onReceivedError(view, request, error);
            }

        });

        binding.get().webView.setWebChromeClient(new CustomWebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                binding.get().toolbar.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.get().progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    // 延迟隐藏为了进度能达到100%
                    binding.get().progressBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (binding.get() != null) {
                                binding.get().progressBar.setVisibility(View.GONE);
                            }
                        }
                    }, 100);
                }
            }

        });
        binding.get().webView.setOnKeyListener((v, keyCode, event) -> {
            //This is the filter
            if (event.getAction() != KeyEvent.ACTION_DOWN)
                return true;

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (binding.get().webView.canGoBack()) {
                    binding.get().webView.goBack();
                } else {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return false;
        });
        binding.get().webView.loadUrl(articleUrl);
    }

    @Override
    public void onDestroyView() {
        // 因为binding已经被销魂了，因此在此处需要销毁WebView，防止WebView监听方法产生空指针异常
        binding.get().webView.destroy();
        super.onDestroyView();
    }

}

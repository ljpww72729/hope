package cc.lkme.hope;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

@SuppressLint("Registered")
public class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    public T baseViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    /**
     * 获取ViewModel
     *
     * @param activity   Activity
     * @param modelClass 需要实例化的ViewModel class
     * @return 被实例化的ViewModel对象
     */
    public T obtainViewModel(FragmentActivity activity, Class<T> modelClass) {
        // Use a Factory to inject dependencies into the ViewModel
        baseViewModel = ViewModelProviders.of(activity, viewModelFactory).get(modelClass);
        return baseViewModel;
    }

}

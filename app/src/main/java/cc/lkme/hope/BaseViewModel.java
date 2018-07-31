package cc.lkme.hope;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public abstract class BaseViewModel extends AndroidViewModel {

    // 通用
    // 消息提示
    protected final SnackbarMessage snackbarMessage = new SnackbarMessage();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

}

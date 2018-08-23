package cc.lkme.hope.main.news.video.detail;

import android.databinding.BindingAdapter;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;

import cc.lkme.hope.R;

public class IjkVideoViewBindings {
    @BindingAdapter({"actualVideoUri"})
    public static void setActualVideoUri(final IjkVideoView view, final String videoUrl) {

//        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
//        ijkVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels * 9 / 16 + 1));
        StandardVideoController controller = new StandardVideoController(view.getContext());
        PlayerConfig mPlayerConfig = new PlayerConfig.Builder()
                .enableCache()
                .addToPlayerManager()//required
                .savingProgress()
//                .playMute()
                .build();
        view.setPlayerConfig(mPlayerConfig);
        view.setUrl(videoUrl);
        view.setVideoController(controller);
        controller.getThumb().setImageResource(R.drawable.va_error);
    }
}

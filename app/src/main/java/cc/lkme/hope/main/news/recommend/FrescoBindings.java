package cc.lkme.hope.main.news.recommend;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cc.lkme.hope.R;
import cc.lkme.hope.data.snssdk.ImageData;

/**
 * Created by LinkedME06 on 16/11/26.
 */

public class FrescoBindings {

    /**
     * 该方式类似于自定义视图时自定义相关属性,该方法的强大之处在于<b>可以进行逻辑的处理</b>
     *
     * 此处bind可自定义,无固定要求
     *
     * @param view   需要绑定的组件类型(必选参数)
     * @param imgUrl 图片地址
     */
    @BindingAdapter({"actualImageUri"})
    public static void setActualImageUri(final SimpleDraweeView view, final String imgUrl) {
        view.setImageURI(imgUrl);

// TODO: 16/11/26 需要将该类提出来
    }

    @BindingAdapter({"actualImageUri"})
    public static void setActualImageUri(final SimpleDraweeView view, final ArrayList<ImageData> imageDataArrayList) {
        if (imageDataArrayList != null && imageDataArrayList.size() > 0) {
            if (view.getTag() != null) {
                if (TextUtils.equals(view.getTag().toString(), view.getContext().getString(R.string.image_list_one)) &&
                        imageDataArrayList.size() > 1) {
                    view.setImageURI(imageDataArrayList.get(1).getUrl());
                } else if (TextUtils.equals(view.getTag().toString(), view.getContext().getString(R.string.image_list_two)) &&
                        imageDataArrayList.size() > 2) {
                    view.setImageURI(imageDataArrayList.get(2).getUrl());
                } else {
                    view.setImageURI(imageDataArrayList.get(0).getUrl());
                }
            } else {
                view.setImageURI(imageDataArrayList.get(0).getUrl());
            }
        }

// TODO: 16/11/26 需要将该类提出来
    }

    /**
     * 该方式类似于自定义视图时自定义相关属性,该方法的强大之处在于<b>可以进行逻辑的处理</b>
     *
     * 此处bind可自定义,无固定要求
     *
     * @param view   需要绑定的组件类型(必选参数)
     * @param imgUri 图片Uri地址
     */
    @BindingAdapter({"actualImageUri"})
    public static void setActualImageUri(final SimpleDraweeView view, final Uri imgUri) {
        view.setImageURI(imgUri);
// TODO: 16/11/26 需要将该类提出来
    }

}

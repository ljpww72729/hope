package cc.lkme.hope.main.mine;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cc.lkme.hope.R;

public class ClickArrowItemBindings {

    @BindingAdapter("title")
    public static void setTitle(final FrameLayout view, final String text) {
        if (view.getChildCount() == 0) {
            return;
        }
        TextView textPrimary = view.getChildAt(0).findViewById(R.id.title);
        if (textPrimary != null) {
            textPrimary.setText(text);
        }
    }

    @BindingAdapter("title_color")
    public static void setTitleColor(final FrameLayout view, final int text_color) {
        if (view.getChildCount() == 0) {
            return;
        }
        TextView textPrimary = view.getChildAt(0).findViewById(R.id.title);
        if (textPrimary != null) {
            textPrimary.setTextColor(text_color);
        }
    }

    @BindingAdapter("subtitle")
    public static void setSubtitle(final FrameLayout view, final String text) {
        if (view.getChildCount() == 0) {
            return;
        }
        TextView textPrimary = view.getChildAt(0).findViewById(R.id.subtitle);
        if (textPrimary != null) {
            textPrimary.setText(text);
        }
    }

    @BindingAdapter("subtitle_color")
    public static void setSubtitleColor(final FrameLayout view, final int text_color) {
        if (view.getChildCount() == 0) {
            return;
        }
        TextView textPrimary = view.getChildAt(0).findViewById(R.id.subtitle);
        if (textPrimary != null) {
            textPrimary.setTextColor(text_color);
        }
    }

    @BindingAdapter("split_line_visibility")
    public static void setSplitLineVisibility(final FrameLayout view, final int visibility) {
        if (view.getChildCount() == 0) {
            return;
        }
        View lineView = view.getChildAt(0).findViewById(R.id.split_line);
        if (lineView != null) {
            lineView.setVisibility(visibility);
        }
    }
}

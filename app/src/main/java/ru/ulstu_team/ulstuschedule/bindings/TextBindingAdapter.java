package ru.ulstu_team.ulstuschedule.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import ru.ulstu_team.ulstuschedule.util.FontUtil;

public class TextBindingAdapter {

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontUtil.getFont(textView.getContext(), fontName));
    }
}

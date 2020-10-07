package com.learn.myviewmodel.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.learn.myviewmodel.R;

public class MyButton extends AppCompatButton {

    private Drawable enabledBackground, disabledBackground;
    private int textColor;

    public MyButton(@NonNull Context context) {
        super(context);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Mengubah background dari Button
        setBackground(isEnabled() ? enabledBackground : disabledBackground);

        // Mengubah warna text pada Button
        setTextColor(textColor);

        // Mengubah ukuran text pada Button
        setTextSize(12.f);

        // Menjadikan object pada Button menjadi center
        setGravity(Gravity.CENTER);

        // Mengubah text pada Button pada kondisi enable dan disable
        setText(isEnabled() ? "Submit" : "Isi Dulu");
    }

    private void init() {
        textColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        enabledBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_button, null);
        disabledBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_button_disable, null);
    }
}

package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class EditText_ extends EditText {

    private final int MIN = 0;
    private final int MAX = 100;
    private final int WARNING_COLOR = Color.YELLOW;

    private int currentValue;

    public EditText_(Context context) {
        super(context);
        init();
    }

    public EditText_(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String txt = getText().toString();
                    int result = Integer.valueOf(txt);
                    if (result < MIN) {
                        result = MIN;
                    }
                    if (result > MAX) {
                        result = MAX;
                    }
                    setText(result + "");

                    if (getTextChanged() != null && currentValue != result) {
                        textChanged.onNumberChanged(result);
                    }
                    currentValue = result;
                }

                return false;
            }
        });

//        setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    String txt = getText().toString();
//                    int result = Integer.valueOf(txt);
//                    if (result < MIN) {
//                        result = MIN;
//                    }
//
//                    if (result > MAX) {
//                        result = MAX;
//                    }
//
//                    setText(result + "");
//                    if (getTextChanged() != null && currentValue != result) {
//                        textChanged.onNumberChanged(result);
//                    }
//                    currentValue = result;
//                    setBackgroundColor(Color.TRANSPARENT);
//                }else{
//                    setSelection(getText().length());
//                }
//            }
//        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(getText().toString())){
                    setText("0");
                }
                String txt = getText().toString();
                int result = Integer.valueOf(txt);
                if (result < MIN || result > MAX) {
                    setBackgroundColor(WARNING_COLOR);
                } else {
                    setBackgroundColor(Color.TRANSPARENT);
                }

            }
        });
    }

    public interface EditTextChangedListener {
        void onNumberChanged(int num);
    }

    private EditTextChangedListener textChanged;

    public EditTextChangedListener getTextChanged() {
        return textChanged;
    }

    public void setTextChanged(EditTextChangedListener textChanged) {
        this.textChanged = textChanged;
    }
}

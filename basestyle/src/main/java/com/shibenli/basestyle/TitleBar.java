package com.shibenli.basestyle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by shibenli on 2015/7/17.
 * 简单的 TitleBar,只包含左侧的文字和中间标题
 */
public class TitleBar extends FrameLayout {

    /**
     * title栏根布局
     */
    protected View titleView;
    protected ImageView ivLeftClose;
    protected View layoutLeft;
    protected ImageView ivLeft;
    protected ImageView ivRight;
    protected TextView tvRight;
    protected TextView tvMiddle;
    protected Activity _activity;
    protected View statusBar;
    protected RelativeLayout rlLayout;

    final int withBackIcon = 0;
    final int withRightIcon = 1;
    final int withAllIcon = 2;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    protected
    @LayoutRes
    int getLayout() {
        return R.layout.common_titlebar;
    }

    protected TitleBar initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(getLayout(), this);
        if (context instanceof Activity)
            _activity = (Activity) context;

        instanceObjects(this);

        ImmersionBar.with(_activity)
                .statusBarView(statusBar)
                .init();

        if (attrs != null) {
            TypedArray taCustom = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            int leftIcon = taCustom.getResourceId(R.styleable.TitleBar_leftIcon, 0);
            int rightIcon = taCustom.getResourceId(R.styleable.TitleBar_rightIcon, R.mipmap.more);
            String rightText = taCustom.getString(R.styleable.TitleBar_rightText);
            int style = taCustom.getInt(R.styleable.TitleBar_titleType, -1);
            boolean isTransparent = taCustom.getBoolean(R.styleable.TitleBar_isTransparent, false);
            String titleMiddle = taCustom.getString(R.styleable.TitleBar_titleMiddle);

            setMiddleTitleText(titleMiddle);
            setRightText(rightText);
            isBackgroundTransparent(isTransparent);
            switch (style) {
                case withBackIcon: {
                    withBackIcon(leftIcon);
                }
                break;
                case withRightIcon: {
                    setRightIcon(rightIcon);
                    setRightText(null);
                }
                break;
                case withAllIcon: {
                    leftIcon = taCustom.getResourceId(R.styleable.TitleBar_leftIcon, R.mipmap.back_gray);
                    withBackIcon(leftIcon).setRightIcon(rightIcon);
                }
                break;
                default: {
                    withBackIcon(R.mipmap.back_gray);
                }
            }

            taCustom.recycle();
        }
        return this;
    }

    /**
     * 实例化对象
     */
    protected void instanceObjects(View titleView) {
        statusBar = titleView.findViewById(R.id.title_statusBar);
        rlLayout = (RelativeLayout) titleView.findViewById(R.id.title_rlContainer);
        //暂时按照此方法修复部分机型上Titlebar背景在xml中使用默认时，设置无效的问题；
        setBackgroundColor(titleView.getResources().getColor(R.color.bg_titlbar));
        ivLeftClose = (ImageView) titleView.findViewById(R.id.title_left_imageview);
        layoutLeft = titleView.findViewById(R.id.layout_title_left);
        tvMiddle = (TextView) titleView.findViewById(R.id.title_middle_textview);
        ivLeft = (ImageView) titleView.findViewById(R.id.title_left);
        ivRight = (ImageView) titleView.findViewById(R.id.title_right);
        tvRight = (TextView) titleView.findViewById(R.id.title_right_textview);
    }

    public View getTitleView() {
        return titleView;
    }

    public TitleBar withBackGrayIcon() {
        return withBackIcon(R.mipmap.back_gray);
    }

    public TitleBar withBackIcon(@DrawableRes int resId) {
        setLeftIcon(resId);
        if (_activity != null) {
            setLeftIconListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity.onBackPressed();
                }
            });
            ivLeftClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity.finish();
                }
            });
        }

        return this;
    }

    /**
     * 设置文本标题
     *
     * @param text
     * @return
     */
    public TitleBar setMiddleTitleText(String text) {
        tvMiddle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvMiddle.setText(text);
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param resId
     * @return
     */
    public TitleBar setLeftIcon(int resId) {
        if (resId > 0) {
            ivLeft.setImageResource(resId);
        }
        ivLeft.setVisibility(resId <= 0 ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置右侧图标
     *
     * @param resId
     * @return
     */
    public TitleBar setRightIcon(int resId) {
        if (resId > 0) {
            ivRight.setImageResource(resId);
        }
        ivRight.setVisibility(resId <= 0 ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置左侧图标点击处理函数
     */
    public TitleBar setLeftIconListener(View.OnClickListener listener) {
        if (ivLeft.getVisibility() == View.VISIBLE) {
            ivLeft.setOnClickListener(listener);
        }

        return this;
    }

    /**
     * 设置右侧图标点击处理函数
     */
    public TitleBar setRightIconListener(View.OnClickListener listener) {
        if (listener != null && ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右侧图标是否显示
     */
    public TitleBar setRightIconVisibility(int visibility) {
        ivRight.setVisibility(visibility);
        return this;
    }

    /**
     * 设置右侧文字点击处理函数
     */
    public TitleBar setRightTextListener(View.OnClickListener listener) {
        if (listener != null && tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setOnClickListener(listener);
        }

        return this;
    }

    /**
     * 设置右侧文字
     */
    public TitleBar setRightText(String text) {
        tvRight.setText(text);
        tvRight.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        return this;
    }

    /**
     * 设置标题栏（可定制标题栏事件处理类）
     */
    public TitleBar setTitlebar(int leftIcon, View.OnClickListener leftCilckListener, String titleName,
                                int rightIcon, View.OnClickListener rightClickListener) {
        if (leftCilckListener == null) {
            leftCilckListener = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    _activity.finish();
                }
            };
        }

        return this.setLeftIcon(leftIcon)
                .setLeftIconListener(leftCilckListener)
                .setMiddleTitleText(titleName)
                .setRightIcon(rightIcon)
                .setRightIconListener(rightClickListener);
    }

    /**
     * 设置标题栏
     */
    public TitleBar setTitlebar(String titleName) {
        return setTitlebar(R.mipmap.back_gray, null, titleName, 0, null);
    }

    /**
     * 设置标题栏透明
     *
     * @return
     */
    public TitleBar isBackgroundTransparent() {
        return isBackgroundTransparent(true);
    }

    /**
     * 设置标题栏透明
     *
     * @return
     */
    public TitleBar isBackgroundTransparent(boolean flag) {
        return flag ? setTitleBackgroundColor(getResources().getColor(android.R.color.transparent)) : this;
    }

    /**
     * 设置标题栏的背景
     *
     * @param color
     * @return
     */
    public TitleBar setTitleBackgroundColor(int color) {
        statusBar.setBackgroundColor(color);
        rlLayout.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题栏背景
     *
     * @param resid
     * @return
     */
    public TitleBar setTitleBackgroundResource(int resid) {
        statusBar.setBackgroundResource(resid);
        rlLayout.setBackgroundResource(resid);
        return this;
    }

    /**
     * 设置左侧关闭是否可用
     *
     * @return
     */
    public TitleBar setLeftClose(boolean enable) {
        if (enable)
            ivLeftClose.setVisibility(View.VISIBLE);
        else {
            ivLeftClose.setVisibility(View.GONE);
        }
        return this;
    }
}

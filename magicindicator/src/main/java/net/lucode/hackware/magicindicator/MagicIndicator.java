package net.lucode.hackware.magicindicator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * 整个框架的入口，核心
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class MagicIndicator extends FrameLayout {
    private IPagerNavigator mNavigator;
    private boolean showBottomLine = false;
    private int mLineHeight;
    private int mLineColor = Color.WHITE;

    private View mLine;
    private Context mContext;

    public MagicIndicator(Context context) {
        this(context, null);
    }

    public MagicIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLineHeight = UIUtil.dip2px(context, 3);
        mContext = context;
        init();
    }

    private void init() {
        if (mLine == null && !showBottomLine)
            return;
        if (mLine != null && !showBottomLine) {
            removeView(mLine);
            mLine = null;
            return;
        }
        if (mLine == null && showBottomLine) {
            mLine = new View(mContext);
            addView(mLine);
        }
        FrameLayout.LayoutParams layoutParams = (LayoutParams) mLine.getLayoutParams();
        layoutParams.height = mLineHeight;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;
        mLine.setBackgroundColor(mLineColor);
        mLine.setLayoutParams(layoutParams);
    }

    public void setShowBottomLine(boolean showBottomLine) {
        this.showBottomLine = showBottomLine;
    }

    public void setLineHeight(int lineHeight) {
        mLineHeight = lineHeight;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNavigator != null) {
            mNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (mNavigator != null) {
            mNavigator.onPageSelected(position);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (mNavigator != null) {
            mNavigator.onPageScrollStateChanged(state);
        }
    }

    public IPagerNavigator getNavigator() {
        return mNavigator;
    }

    public void setNavigator(IPagerNavigator navigator) {
        if (mNavigator == navigator) {
            return;
        }
        if (mNavigator != null) {
            mNavigator.onDetachFromMagicIndicator();
        }
        mNavigator = navigator;
        removeAllViews();
        mLine = null;
        init();
        if (mNavigator instanceof View) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView((View) mNavigator, lp);
            mNavigator.onAttachToMagicIndicator();
        }
    }
}

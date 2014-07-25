package com.pixplicity.choicegridview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class GridLinearLayout extends LinearLayout implements Checkable {

	private static final int[] CHECKED_STATE_SET = {
			android.R.attr.state_checked
	};

	private static final String TAG = GridLinearLayout.class.getSimpleName();

	private boolean mChecked;

	public GridLinearLayout(Context context) {
		super(context);
	}

	public GridLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public GridLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		if (mChecked != checked) {
			Log.d(TAG, "checked=" + checked + " for " + this);
		}
		mChecked = checked;
		refreshDrawableState();
	}

	@Override
	public void toggle() {
		setChecked(!mChecked);
	}

}

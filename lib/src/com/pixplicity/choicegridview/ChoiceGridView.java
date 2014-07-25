package com.pixplicity.choicegridview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.ListAdapter;

public class ChoiceGridView extends GridView {

	public static interface OnChoiceModeChangedListener {

		public void onChoiceModeChanged(int choiceMode);

	}

	private static final String TAG = ChoiceGridView.class.getSimpleName();

	private final SparseBooleanArray mChecked = new SparseBooleanArray();
	private int mChoiceMode = AbsListView.CHOICE_MODE_NONE;
	private OnChoiceModeChangedListener mOnChoiceModeChangedListener;

	public ChoiceGridView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public ChoiceGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public ChoiceGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public void init(Context context, AttributeSet attrs, int defStyle) {
		setOnItemLongClickListener(new OnItemLongClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public boolean onItemLongClick(AdapterView<?> list, View v, int position, long id) {
				setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
				setItemChecked(position, true);
				((Checkable) v).setChecked(true);
				return true;
			}
		});
	}

	public void setOnChoiceModeChangedListener(OnChoiceModeChangedListener listener) {
		mOnChoiceModeChangedListener = listener;
	}

	public OnChoiceModeChangedListener getOnChoiceModeChangedListener() {
		return mOnChoiceModeChangedListener;
	}

	@Override
	public int getChoiceMode() {
		return mChoiceMode;
	}

	@Override
	public void setChoiceMode(int choiceMode) {
		if (mChoiceMode != choiceMode) {
			switch (choiceMode) {
			case AbsListView.CHOICE_MODE_MULTIPLE:
			case AbsListView.CHOICE_MODE_MULTIPLE_MODAL:
				// Leave any existing selection
				break;
			default:
				clearChoices();
				break;
			};
			mChoiceMode = choiceMode;
			if (mOnChoiceModeChangedListener != null) {
				mOnChoiceModeChangedListener.onChoiceModeChanged(mChoiceMode);
			}
		}
	}

	@Override
	public boolean isItemChecked(int position) {
		return mChecked.get(position);
	}

	@Override
	public void setItemChecked(int position, boolean checked) {
		mChecked.put(position, checked);
	}

	@Override
	public long[] getCheckedItemIds() {
		int count = mChecked.size();
		long[] ids = new long[count];
		ListAdapter adapter = getAdapter();
		for (int i = 0; i < count; i++) {
			int position = mChecked.keyAt(i);
			ids[i] = adapter.getItemId(position);
		}
		return ids;
	}

	@Override
	public SparseBooleanArray getCheckedItemPositions() {
		return mChecked;
	}

	@Override
	public int getCheckedItemCount() {
		return mChecked.size();
	}

	public void selectAll() {
		for (int i = 0; i < getCount(); i++) {
			mChecked.put(i, true);
		}
		invalidateViews();
	}

	@Override
	public void clearChoices() {
		mChecked.clear();
		invalidateViews();
	}

	@Override
	public boolean performItemClick(View view, int position, long id) {
		switch (mChoiceMode) {
		case AbsListView.CHOICE_MODE_SINGLE:
			// First clear existing selection
			clearChoices();
		case AbsListView.CHOICE_MODE_MULTIPLE:
		case AbsListView.CHOICE_MODE_MULTIPLE_MODAL:
			if (view instanceof Checkable) {
				((Checkable) view).toggle();
				setItemChecked(position, ((Checkable) view).isChecked());
				return true;
			}
			break;
		}
		return super.performItemClick(view, position, id);
	}

}

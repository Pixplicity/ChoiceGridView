package com.pixplicity.choicegridview;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;

public abstract class GridCursorAdapter extends CursorAdapter {

	private final AdapterView<?> mList;

	@Deprecated
	public GridCursorAdapter(Context context, Cursor c, AdapterView<?> list) {
		super(context, c);
		mList = list;
	}

	public GridCursorAdapter(Context context, Cursor c, int flags, AdapterView<?> list) {
		super(context, c, flags);
		mList = list;
	}

	public GridCursorAdapter(Context context, Cursor c, boolean autoRequery, AdapterView<?> list) {
		super(context, c, autoRequery);
		mList = list;
	}

	@Override
	public final void bindView(View view, Context context, Cursor cursor) {
		int position = cursor.getPosition();
		if (view instanceof Checkable && mList instanceof ChoiceGridView) {
			boolean checked = ((ChoiceGridView) mList).isItemChecked(position);
			setChecked(view, checked);
		}
		bindCheckableView(view, context, cursor);
	}

	public abstract void bindCheckableView(View view, Context context, Cursor cursor);

	@Override
	public final View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = newCheckableView(cursor.getPosition(), null, parent);
		if (!(view instanceof Checkable)) {
			throw new IllegalArgumentException(
					GridAdapter.class.getSimpleName()
							+ " expects a root view that implements Checkable");
		}
		return view;
	}

	public abstract View newCheckableView(int position, View convertView, ViewGroup parent);

	public void setChecked(View view, boolean checked) {
		((Checkable) view).setChecked(checked);
	}

}

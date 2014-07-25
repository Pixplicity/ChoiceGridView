package com.pixplicity.choicegridview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ListAdapter;

public abstract class GridAdapter implements ListAdapter {

	private final AdapterView<?> mList;

	public GridAdapter(AdapterView<?> list) {
		mList = list;
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {}

	@Override
	public final View getView(final int position, View convertView, ViewGroup parent) {
		convertView = getCheckableView(position, convertView, parent);
		if (!(convertView instanceof Checkable)) {
			throw new IllegalArgumentException(
					GridAdapter.class.getSimpleName()
							+ " expects a root view that implements Checkable");
		}
		if (convertView instanceof Checkable && mList instanceof ChoiceGridView) {
			boolean checked = ((ChoiceGridView) mList).isItemChecked(position);
			setChecked(convertView, checked);
		}
		return convertView;
	}

	public abstract View getCheckableView(int position, View convertView, ViewGroup parent);

	public void setChecked(View view, boolean checked) {
		((Checkable) view).setChecked(checked);
	}


}
package com.pixplicity.choicegridview;

import com.pixplicity.gridtest.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public class DummyGridAdapter extends GridAdapter {

	public DummyGridAdapter(AdapterView<?> list) {
		super(list);
	}

	@Override
	public View getCheckableView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.grid, null);
		}
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
		tvTitle.setText("Item " + position);
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public boolean isEmpty() {
		return getCount() > 0;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

}

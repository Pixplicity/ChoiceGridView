package com.pixplicity.choicegridview;

import java.util.Arrays;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.pixplicity.choicegridview.ChoiceGridView.OnChoiceModeChangedListener;
import com.pixplicity.gridtest.R;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private ChoiceGridView mGridView;

	private Menu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGridView = (ChoiceGridView) findViewById(R.id.gridview);
		mGridView.setAdapter(new DummyGridAdapter(mGridView));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View v, int position, long id) {
				Toast.makeText(MainActivity.this, "item " + position, Toast.LENGTH_SHORT).show();
			}
		});
		mGridView.setOnChoiceModeChangedListener(new OnChoiceModeChangedListener() {

			@Override
			public void onChoiceModeChanged(int choiceMode) {
				mMenu.findItem(R.id.action_delete).setEnabled(
						choiceMode != AbsListView.CHOICE_MODE_NONE);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
		return true;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			boolean multiSelect = mGridView.getChoiceMode() == AbsListView.CHOICE_MODE_NONE;
			mGridView.setChoiceMode(multiSelect ? AbsListView.CHOICE_MODE_MULTIPLE
					: AbsListView.CHOICE_MODE_NONE);
			return true;
		case R.id.action_delete:
			long[] ids = mGridView.getCheckedItemIds();
			Log.d(TAG, Arrays.toString(ids));
			Toast.makeText(MainActivity.this, "delete " + Arrays.toString(ids), Toast.LENGTH_SHORT)
					.show();
			mGridView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

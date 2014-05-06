package tony.app.englishwords;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	

	private static final String TAG = "EnglishWords::MainActivity";
	private FragmentTabHost mTabHost;

	// private TabManager mTabManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tabs);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// mTabHost.setCurrentTab(0);//設定一開始就跳到第一個分頁
		mTabHost.addTab(mTabHost.newTabSpec("讀取/更新").setIndicator("讀取/更新"),
				DropboxChooser.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("單字列表").setIndicator("單字列表"),
				WordsListMain.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("單字考試").setIndicator("單字考試"),
				ExamActivity.class, null);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		displayUserSettings();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.action_settings:
			Toast.makeText(MainActivity.this, "尚未準備完成。", Toast.LENGTH_SHORT).show();
			/*
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, UserSettingActivity.class);
			startActivity(intent);
			*/
			break;
		// action with ID action_settings was selected
		default:
			break;
		}

		return true;
	}

	private void displayUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		String settings = "";

		settings = settings + "Password: "
				+ sharedPrefs.getString("name", (String)getResources().getText(R.string.usernamedefault));

		settings = settings + "\nRemind For Update:"
				+ sharedPrefs.getBoolean("checkbox", false);

		settings = settings + "\nUpdate Frequency: "
				+ sharedPrefs.getString("list", "NoLIST!");

		//Toast.makeText(MainActivity.this, settings, Toast.LENGTH_LONG).show();
	}

}

package tony.app.englishwords;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class UserSettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        //
        EditTextPreference editTextPref = (EditTextPreference) findPreference("name");
        editTextPref
        .setSummary(sp.getString("name", (String)getResources().getText(R.string.usernamedefault)));
        //
        ListPreference listpref = (ListPreference) findPreference("list");
        listpref.setSummary(listpref.getEntry());
	}
	protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        } else if(pref instanceof ListPreference) {
        	ListPreference lp = (ListPreference) pref;
        	lp.setSummary(lp.getEntry());
        }
    }
}

package com.example.android.mymovieapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class MoviesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            //Getting preferences identified by 2 Key preferences
            Preference orderByRating =
                    findPreference(getString(R.string.preference_sort_by_rating_key));
            Preference orderByPopularity =
                    findPreference(getString(R.string.preference_sort_by_popularity_key));

            //Binding the preference found to a onPreferenceChangeListener

            bindPreferenceSummaryToValueBoolean(orderByRating);
            bindPreferenceSummaryToValueBoolean(orderByPopularity);
           }

        private void bindPreferenceSummaryToValueBoolean(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            Boolean preferencesBoolean;
            preferencesBoolean = preferences.getBoolean(preference.getKey(), true);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if(preference.getKey()!=getString(R.string.preference_sort_by_rating_key)&preference.getKey()!=getString(R.string.preference_sort_by_popularity_key)){
            preference.setSummary(stringValue);}
            if(preference.getKey()==getString(R.string.preference_sort_by_rating_key) & newValue.equals(true)){
                Preference organizeByRelevance=findPreference(getString(R.string.preference_sort_by_popularity_key));
                ((CheckBoxPreference)organizeByRelevance).setChecked(false);
                organizeByRelevance.setDefaultValue(false);
            }
            if(preference.getKey()==getString(R.string.preference_sort_by_popularity_key) & newValue.equals(true)){
                Preference organizeByDate=findPreference(getString(R.string.preference_sort_by_rating_key));
                ((CheckBoxPreference)organizeByDate).setChecked(false);
                organizeByDate.setDefaultValue(false);
            }
            return true;}

    }
}
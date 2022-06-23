package com.dicoding.courseschedule.ui.setting

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //TODO 10 : Update theme based on value in ListPreference
        val shared = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        shared.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            val darkModeString = activity?.getString(R.string.pref_key_dark)

            val value = sharedPreferences.getString(darkModeString, "")
            when (value) {
                activity?.getString(R.string.pref_dark_auto) -> updateTheme(NightMode.AUTO.value)
                activity?.getString(R.string.pref_dark_on) -> updateTheme(NightMode.ON.value)
                activity?.getString(R.string.pref_dark_off) -> updateTheme(NightMode.OFF.value)
                else -> {}
            }
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val receiver = DailyReminder()
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

        prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue as Boolean) {
                receiver.setDailyReminder(requireContext())
            } else {
                receiver.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}
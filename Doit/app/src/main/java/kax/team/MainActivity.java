package kax.team;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import kax.team.fragment.HistoryFragment;
import kax.team.fragment.ProfileFragment;
import kax.team.fragment.RunningFragment;
import kax.team.fragment.SettingsFragment;

public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                 .replace(R.id.fragment_container, RunningFragment.newInstance())
                 .commit();
    }



    public void onClickMenu(View v) {
        Fragment f;
        switch (v.getId()) {
            case R.id.running_menu:
                f = RunningFragment.newInstance();
                break;
            case R.id.history_menu:
                f = HistoryFragment.newInstance();
                break;
            case R.id.settings_menu:
                f = SettingsFragment.newInstance();
                break;
            case R.id.profile_menu:
                f = ProfileFragment.newInstance();
                break;
            default:
                return;
        }
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }
}

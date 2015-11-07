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
}

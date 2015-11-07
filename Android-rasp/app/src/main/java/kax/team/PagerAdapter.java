package kax.team;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kax.team.fragment.RunningFragment;
import kax.team.fragment.SettingsFragment;

/**
 * Created by zenbook on 6/11/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {


    Fragment[] fragments = new Fragment[2];

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        fragments[0] = RunningFragment.newInstance();
        fragments[1] = SettingsFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments[position];
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}

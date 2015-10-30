package com.car_market_android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.car_market_android.util.MessageUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends Activity implements ActionBar.TabListener {

    private final static int TAB_PROFILE = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private String profileResult = "No profile info";

    public String getProfileResult() {
        return profileResult;
    }

    public void setProfileResult(String profileResult) {
        this.profileResult = profileResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "settings button on action bar is clicked...", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onBackPressed() {
        switch (mViewPager.getCurrentItem()) {
            case TAB_PROFILE:
                MessageUtils.showToastShort(this, ":" + mViewPager.getCurrentItem() + " -> Press back one more time to exit");
                AccountFragment fragment = (AccountFragment) ((SectionsPagerAdapter) mViewPager.getAdapter()).getFragment(mViewPager.getCurrentItem());

//                if (fragment != null && fragment.goBackToRegistrationView()) {
//                    fragment.setRegistrationView();
//                }
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        String[] sections = {"Profile", "Marketplace", "Garage", "Wishlist"};
        Map<Integer, Fragment> map = new HashMap<Integer, Fragment>();

        public SectionsPagerAdapter(Activity activity) {
            super(activity.getFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment f = null;

            switch (position) {
                case 0:
                    f = AccountFragment.newInstance(position + 1);
                    break;
                case 1:
                    f = Marketplace_Fragment.newInstance(position + 1);
                    break;
                case 3:
                    f = Wishlist_Fragment.newInstance(position + 1);
                    break;
                default:
                    f = MainFragment.newInstance(position + 1);
                    break;
            }

            map.put(position, f);
            return f;
        }

        public Fragment getFragment(int position) {
            return map.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            if (position < sections.length) {
                return sections[position].toUpperCase(l);
            }
            //            switch (position) {
            //                case 0:
            //                    return getString(R.string.title_section1).toUpperCase(l);
            //                case 1:
            //                    return getString(R.string.title_section2).toUpperCase(l);
            //                case 2:
            //                    return getString(R.string.title_section3).toUpperCase(l);
            //            }
            return null;
        }
    }
}

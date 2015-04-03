package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.norriors.java.mtbfreeride.R;
import com.norriors.java.mtbfreeride.astuetz.PagerSlidingTabStrip;

/**
 * Classe MainActivity
 */
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LlibreVisitesFragment.OnFragmentInteractionListener, OnClickListener {

    // User Session Manager Class
    private UsuariSessionManager sessioUsuari;


    //private ModalitatsConversor modalitatsConversor;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment currentFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciar la sessió
        sessioUsuari = new UsuariSessionManager(getApplicationContext());

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if (!sessioUsuari.checkLogin()) {
            finish();
        }

        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(MainActivity.this)) {
            setupGui();
        } else {
            InternetUtil.showAlertDialog(MainActivity.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    public void setupGui() {


        /*modalitatsConversor = new ModalitatsConversor(factsHelper);
        String prova = "";
        ArrayList<Modalitat> m = modalitatsConversor.getAllAsList();
        for(Modalitat i : m){
            prova = " " + i.getCodi();
            prova += " " + i.getDescripcio();
            prova += " " + i.getImatge1();
            prova += " " + i.getImatge2();
            prova += " " + i.getTitol();
            prova += " " + i.getUrl_video();

        }
        System.out.println(prova);*/


        // Creació del drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        // Crecio del pager horitzontal
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(4);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        // update the main content by replacing fragments
        mPagerSlidingTabStrip.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);

        switch (position) {

            case 1:
                currentFragment = PerfilUsuariFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag").commit();
                break;

            case 2:
                if (mPagerSlidingTabStrip.getVisibility() == View.GONE && mViewPager.getVisibility() == View.GONE) {
                    mPagerSlidingTabStrip.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.VISIBLE);
                    if(currentFragment != null) {
                        t.remove(currentFragment).commit();
                        currentFragment = null;
                    }
                }
                break;

            case 3:
                currentFragment = LlibreVisitesFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag").commit();
                break;

            case 4:
                currentFragment = ValoracionsFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag").commit();
                break;

            case 5:
                sessioUsuari.logoutUser();
                finish();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode que recull el resultat de l'activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hola");
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        if (requestCode == 0 && resultCode == RESULT_OK) {
            currentFragment = ValoracionsFragment.newInstance();
            t.replace(R.id.container, currentFragment, "fragment").commit();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return null;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private final String[] TITLES = {"North Shore", "Dirt Jump", "Street", "Big Air", "Park"};
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {

            /*ScrollTabHolderFragment fragment = (SampleListFragment) SampleListFragment.newInstance(position);

            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }
            return fragment;*/

            // Si volem crear els fragments en diferents opcions li assignem una classe diferent
            ScrollTabHolderFragment fragment;
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    fragment = (SampleListFragment) SampleListFragment.newInstance(0);
                    mScrollTabHolders.put(position, fragment);
                    return fragment;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    fragment = (SampleListFragment) SampleListFragment.newInstance(1);
                    mScrollTabHolders.put(position, fragment);
                    return fragment;
                case 2: // Fragment # 1 - This will show SecondFragment
                    fragment = (SampleListFragment) SampleListFragment.newInstance(2);
                    mScrollTabHolders.put(position, fragment);
                    return fragment;
                case 3: // Fragment # 1 - This will show SecondFragment
                    fragment = (SampleListFragment) SampleListFragment.newInstance(3);
                    mScrollTabHolders.put(position, fragment);
                    return fragment;
                case 4: // Fragment # 1 - This will show SecondFragment
                    fragment = (SampleListFragment) SampleListFragment.newInstance(4);
                    mScrollTabHolders.put(position, fragment);
                    return fragment;
                default:
                    return null;
            }
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }

}
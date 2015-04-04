/*************************************************************************************
 *                                                                                   *
 *  MTB FreeRide por Java Norriors se distribuye bajo una                            *
 *  Licencia Creative Commons Atribución-NoComercial-SinDerivar 4.0 Internacional.   *
 *                                                                                   *
 *  http://creativecommons.org/licenses/by-nc-nd/4.0/                                *
 *                                                                                   *
 *  @author: Arnau Roma Vidal  - aroma@infoboscoma.net                               *
 *  @author: Rubén Garcia Torres - rgarcia@infobosccoma.net                          *
 *  @author: Francesc Gallart Vila - fgallart@infobosccoma.net                       *
 *                                                                                   *
/************************************************************************************/
package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

/**
 * Classe MainActivity
 */
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LlibreVisitesFragment.OnFragmentInteractionListener, OnClickListener {

    // User Session Manager Class
    private UsuariSessionManager sessioUsuari;

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

    private static MediaPlayer mediaPlayer;
    private static boolean so;

    private android.support.v4.app.FragmentTransaction t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.so = true;

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

        mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor music = getAssets().openFd("Sons/music.mp3");
            mediaPlayer.setDataSource(music.getFileDescriptor(), music.getStartOffset(), music.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    public void setupGui() {
        // Creació del drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        t = getSupportFragmentManager().beginTransaction();
        currentFragment = ModalitatsFragment.newInstance();
        t.add(R.id.container, currentFragment, "fragment").commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        t = getSupportFragmentManager().beginTransaction();

        switch (position) {

            case 1:
                currentFragment = PerfilUsuariFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag5").commit();
                break;

            case 2:
                currentFragment = ModalitatsFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag4").commit();
                break;

            case 3:
                currentFragment = LlibreVisitesFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag3").commit();
                break;

            case 4:
                currentFragment = ValoracionsFragment.newInstance();
                t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag2").commit();
                break;

            case 5:
                sessioUsuari.logoutUser();
                finish();
                break;
        }
    }

    /**
     * Mètode que quan se surt de l'aplicació, però no es tanca,
     * para les peticions del gps per no gastar bateria.
     */
    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    /**
     * Mètode que quan es torna a l'aplicació oberta en segon pla,
     * torna activar les peticions del gps si estaven activades
     * abans de sortir de l'aplicació.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (so) {
            mediaPlayer.start();
        }
    }

    public static void music(boolean actiu){
        if (actiu){
            so = true;
            mediaPlayer.start();
        }else{
            so = false;
            mediaPlayer.pause();
        }
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
        FragmentManager fm = getSupportFragmentManager();
        PerfilUsuariFragment fragment;
        ValoracionsFragment fragmentValoracions;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                // Cas que un cop penjada la valoració recarrega la llista de valoracions
                case 0:
                    fragmentValoracions = (ValoracionsFragment) fm.findFragmentById(R.id.container);
                    fragmentValoracions.new DescarregarDades().execute("http://provesrasp.ddns.net/aplicacio/valoracions.php");
                    break;

                // Cas que un cop seleccionada una imatge de la galeria la pinta
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    String result;
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor == null) {
                        result = selectedImage.getPath();
                    } else {
                        cursor.moveToFirst();
                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        result = cursor.getString(idx);
                        cursor.close();
                    }
                    cursor.close();
                    fragment = (PerfilUsuariFragment) fm.findFragmentById(R.id.container);
                    fragment.pintaImatge(result);
                    break;

                // Cas que el tirar una foto amb la càmera la pinta
                case 2:
                    fragment = (PerfilUsuariFragment) fm.findFragmentById(R.id.container);
                    fragment.pintaImatge();
                    break;
            }
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
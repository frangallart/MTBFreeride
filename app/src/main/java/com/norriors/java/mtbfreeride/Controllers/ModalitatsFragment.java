package com.norriors.java.mtbfreeride.Controllers;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norriors.java.mtbfreeride.R;
import com.norriors.java.mtbfreeride.astuetz.PagerSlidingTabStrip;

/**
 * Created by Arnau on 04/04/2015.
 */
public class ModalitatsFragment extends android.support.v4.app.Fragment {

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;


    public static ModalitatsFragment newInstance() {
        ModalitatsFragment fragment = new ModalitatsFragment();
        return fragment;
    }

    public ModalitatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Modalitats");

        if (rootView != null) {
            System.out.println("fffff");
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                System.out.println("adeu");
                parent.removeView(rootView);
            }
        }

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_modalitats, container, false);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(4);

        mPagerAdapter = new PagerAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
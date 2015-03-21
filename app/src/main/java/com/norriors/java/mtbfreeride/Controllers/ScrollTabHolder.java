package com.norriors.java.mtbfreeride.Controllers;

import android.widget.AbsListView;

/**
 * Classe ScrollTabHolder
 */
public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
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
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.norriors.java.mtbfreeride.R;

import java.util.List;

/**
 * Classe PersonalDrawerAdapter
 */
public class PersonalDrawerAdapter extends ArrayAdapter<DrawerItems> implements CompoundButton.OnCheckedChangeListener {

    Context context;
    List<DrawerItems> drawerItemsList;
    int layoutResID;
    DrawerItems dItem;

    public PersonalDrawerAdapter(Context context, int layoutResourceID,
                                 List<DrawerItems> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemsList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        dItem = this.drawerItemsList.get(position);
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);


            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

            drawerHolder.headerLayout = (LinearLayout) view
                    .findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) view
                    .findViewById(R.id.itemLayout);

            drawerHolder.tbSo = (ToggleButton) view.findViewById(R.id.tbSo);
            drawerHolder.icon = dItem.getImgUser();

            if (drawerHolder.headerLayout != null && drawerHolder.icon != null) {
                drawerHolder.headerLayout.addView(drawerHolder.icon);

                LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams((int) getContext().getResources().getDimension(R.dimen.photo_user_registerH), (int) getContext().getResources().getDimension(R.dimen.photo_user_registerH));
                layParams.setMargins(0,30,0,20);
                layParams.gravity = Gravity.CENTER;
                drawerHolder.icon.setLayoutParams(layParams);
            }
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        if (position == 1) {
            drawerHolder.title.setText(dItem.getTitle());
            drawerHolder.title.setVisibility(View.VISIBLE);
        } else {
            drawerHolder.ItemName.setText(dItem.getTitle());
        }

        if (position == 6) {
            drawerHolder.tbSo.setVisibility(View.VISIBLE);
            drawerHolder.tbSo.setOnCheckedChangeListener(this);
        }

        if (dItem.getTitle() == null) {
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
        }

        return view;
    }

    /**
     * Mètode que comprova l'estat del togglebutton del so
     * @param compoundButton
     * @param check
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        if(check)
        {
            MainActivity.music(false);
        }
        else
        {
            MainActivity.music(true);
        }
    }

    private static class DrawerItemHolder {
        TextView ItemName, title;
        MLRoundedImageView icon;
        LinearLayout headerLayout, itemLayout;
        ToggleButton tbSo;
    }


}
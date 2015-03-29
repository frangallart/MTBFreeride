package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.norriors.java.mtbfreeride.R;

import java.util.List;

/**
 * Classe PersonalDrawerAdapter
 */
public class PersonalDrawerAdapter extends ArrayAdapter<DrawerItems> {

    Context context;
    List<DrawerItems> drawerItemsList;
    int layoutResID;
    DrawerItems dItem;
    private static int count = 0;

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

            drawerHolder.icon = dItem.getImgUser();

            drawerHolder.topBord = (View)  view.findViewById(R.id.topBord);
            drawerHolder.botBord = (View)  view.findViewById(R.id.botBord);

            if (drawerHolder.headerLayout != null && drawerHolder.icon != null) {
                drawerHolder.headerLayout.addView(drawerHolder.icon);

                LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams((int) getContext().getResources().getDimension(R.dimen.photo_user_registerH), (int) getContext().getResources().getDimension(R.dimen.photo_user_registerH));
                layParams.setMargins(0,20,0,10);
                drawerHolder.icon.setLayoutParams(layParams);
            }
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }


        if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.title.setText(dItem.getTitle());
            drawerHolder.topBord.setVisibility(View.VISIBLE);
            drawerHolder.botBord.setVisibility(View.VISIBLE);


        } else {
            drawerHolder.botBord.setVisibility(View.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.headerLayout.removeView(drawerHolder.title);

        }

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName, title;
        MLRoundedImageView icon;
        LinearLayout headerLayout, itemLayout;
        View topBord, botBord;
    }


}
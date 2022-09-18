package com.profillah.crud;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CLV_DataUser extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vName;
    private ArrayList<String> vNim;
    private ArrayList<String> vAddress;
    private ArrayList<String> vHobby;

    public CLV_DataUser(Activity context, ArrayList<String> Name, ArrayList<String> Nim, ArrayList<String> Address,ArrayList<String> Hobby)
    {
        super(context, R.layout.list_item,Name);
        this.context    = context;
        this.vName      = Name;
        this.vNim       = Nim;
        this.vAddress   = Address;
        this.vHobby     = Hobby;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item, null, true);

        //Declarasi komponen
        TextView name       = rowView.findViewById(R.id.idTXTName);
        TextView nim        =  rowView.findViewById(R.id.idTXTNim);

        //Set Parameter Value sesuai widget textview
        name.setText(vName.get(position));
        nim.setText(vNim.get(position));

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}
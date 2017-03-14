package com.taeksukim.android.firebaseone;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by TaeksuKim on 2017. 3. 13..
 */

public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Bbs> datas;

    public ListAdapter(List<Bbs> datas, Context context){
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item, null);
        TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
        TextView textContent = (TextView) view.findViewById(R.id.textContent);

        Bbs bbs = datas.get(position);
        textTitle.setText(bbs.title);
        textContent.setText(bbs.content);
        return view;
    }
}

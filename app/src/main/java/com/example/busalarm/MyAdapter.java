package com.example.busalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SearchStop> sample;

    public MyAdapter(Context context, ArrayList<SearchStop> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SearchStop getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.bus, null);

        //ImageView imageView = (ImageView)view.findViewById(R.id.poster);
        TextView stationId = (TextView)view.findViewById(R.id.stationName);
        //TextView grade = (TextView)view.findViewById(R.id.grade);

        //imageView.setImageResource(sample.get(position).getPoster());
        stationId.setText(sample.get(position).getbusStop());
        //grade.setText(sample.get(position).getGrade());

        return view;
    }
}

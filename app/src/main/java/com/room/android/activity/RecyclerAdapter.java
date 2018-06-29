package com.room.android.activity;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.room.android.PeristanceDatabase.Entity.models.City;
import com.room.android.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    public List<City> cityList = new ArrayList<>();
    public static int position;
    private selectionLongListener navItemPositionListener;

    public RecyclerAdapter(Context context,
                           List<City> mCountryList) {
        this.mContext = context;
        this.cityList = mCountryList;
    }

    public void setNavItems(selectionLongListener navItemPositionListener) {
        this.navItemPositionListener = navItemPositionListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_country_list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {

        final City city = cityList.get(position);
       if(city != null) {
           holder.mIdText.setText(String.valueOf(city.getId()));
           holder.mCountryIdText.setText(String.valueOf(city.getCountryID()));
           holder.mCountryNameText.setText(city.getCityName());
       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               navItemPositionListener.onSelectionItemsClick(city,position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {

        private TextView mIdText;
        private TextView mCountryIdText;
        private TextView mCountryNameText;

        MyViewHolder(View itemView) {
            super(itemView);

            mIdText = itemView.findViewById(R.id.idText);
            mCountryIdText = itemView.findViewById(R.id.countryIdtext);
            mCountryNameText = itemView.findViewById(R.id.countryNameText);
        }

    }

    public interface selectionLongListener {
        void onSelectionItemsClick(City city,int position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void remove() {
        cityList.get(position);
        notifyItemRemoved(position);
    }
}

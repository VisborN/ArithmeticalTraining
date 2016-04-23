package com.dudkovlad.arithmetical_training;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by vlad on 06.04.2016.
 */
public class StatisticsListAdapter implements ListAdapter {

    private Context context;
    String[] date_at;
    long[] time_in_at;
    int[] count_of_multiplications_at;
    int count_of_days;

    public StatisticsListAdapter(Context context, int count_of_days, String[] date_at, long[] time_in_at, int[] count_of_multiplications_at){
        this.context = context;
        this.date_at = date_at;
        this.time_in_at = time_in_at;
        this.count_of_multiplications_at = count_of_multiplications_at;
        this.count_of_days = count_of_days;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return count_of_days;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater lInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item;
        if (convertView == null) {

            item = lInflater.inflate(R.layout.history_list_item, parent, false);
        } else {
            item = convertView;
        }
        ((TextView) item.findViewById(R.id.date)).setText(date_at[position]);

        ((TextView) item.findViewById(R.id.time_in)).setText(String.format("%.2f", (time_in_at[position] / 60000f)) + context.getString(R.string.minute_count));
        ((TextView) item.findViewById(R.id.count)).setText(count_of_multiplications_at[position] + context.getString(R.string.multiply_count));
        ((TextView) item.findViewById(R.id.speed)).setText((
                count_of_multiplications_at[position] == 0 ? "0" : String.format("%.2f", (time_in_at[position]) / ((float) count_of_multiplications_at[position]) / 60000f)) + context.getString(R.string.speed));


        return item;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

package com.dudkovlad.arithmetical_training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button easymode_t_view;

    ListView statistics_list;
    TextView statistics;
    SharedPreferences sharedPreferences;

    int easymode;
    long first_time_in;
    long last_time_in;
    long all_time_in;
    long this_week_time_in;
    long this_day_time_in;
    int count_of_multiplications;
    int current_week_count_of_multiplications;
    int current_day_count_of_multiplications;
    int[] count_of_multiplications_at;
    long[] time_in_at;
    String[] date_at;
    int count_of_days;

    String [] easymode_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        easymode_s = new String [] {
                getString(R.string.i_am_genious),
                getString(R.string.mult3_3),
                getString(R.string.mult2_3),
                getString(R.string.mult2_2),
                getString(R.string.mult1_2),
                getString(R.string.mult20),
                getString(R.string.mult10),};
        easymode_t_view = (Button)findViewById(R.id.easymode);
        statistics_list = (ListView)findViewById(R.id.statistics_list);
        statistics = (TextView)findViewById(R.id.statistics);

        easymode_t_view.setOnClickListener(this);
        load();

        statistics.setText(
                getString(R.string.speed_min_mult) + ":\n" +
                        (current_day_count_of_multiplications == 0 ? "0" : String.format("%.2f", this_day_time_in / ((float) current_day_count_of_multiplications) / 60000f)) + " " + getString(R.string.today) + "\n" +
                        (current_week_count_of_multiplications == 0 ? "0" : String.format("%.2f", this_week_time_in / ((float) current_week_count_of_multiplications) / 60000f)) + " " + getString(R.string.this_week) + "\n" +
                        (count_of_multiplications == 0 ? "0" : String.format("%.2f", all_time_in / ((float) count_of_multiplications) / 60000f)) + " " + getString(R.string.all_time) +"\n");
        statistics_list.setAdapter(new StatisticsListAdapter(this, count_of_days, date_at, time_in_at, count_of_multiplications_at));
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.easymode) {
            easymode = (easymode+1)%easymode_s.length;
            easymode_t_view.setText(easymode_s[easymode]);
        }else
            ((CheckedTextView) v).setChecked(!(((CheckedTextView) v).isChecked()));


    }

    protected void load(){

        sharedPreferences = this.getSharedPreferences("main_prefs", 0);

        all_time_in = sharedPreferences.getLong("all_time_in", 0);
        first_time_in = sharedPreferences.getLong("first_time_in", 0);
        last_time_in = sharedPreferences.getLong("last_time_in", 0);
        this_week_time_in = sharedPreferences.getLong("this_week_time_in", 0);
        this_day_time_in = sharedPreferences.getLong("this_day_time_in", 0);
        count_of_multiplications = sharedPreferences.getInt("count_of_multiplications", 0);
        current_week_count_of_multiplications = sharedPreferences.getInt("current_week_count_of_multiplications", 0);
        current_day_count_of_multiplications = sharedPreferences.getInt("current_day_count_of_multiplications", 0);
        easymode = sharedPreferences.getInt("easymodei", 0);

        easymode_t_view.setText(easymode_s[sharedPreferences.getInt("easymodei", 3)]);

        count_of_days = sharedPreferences.getInt("count_of_days", 0);
        count_of_multiplications_at = new int[count_of_days];
        time_in_at = new long[count_of_days];
        date_at = new String[count_of_days];

        for (int i = 0; i < count_of_days; i++)
        {
            count_of_multiplications_at[i] = sharedPreferences.getInt("count_of_multiplications_at[" + i + "]", 0);
            time_in_at [i] = sharedPreferences.getLong("time_in_at[" + i + "]", 0);
            date_at [i] = sharedPreferences.getString("date_at[" + i + "]", "большой взрыв");
        }


    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putInt("easymodei", easymode);

        editor.commit();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent myIntent = new Intent(SettingsActivity.this, AboutActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            SettingsActivity.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

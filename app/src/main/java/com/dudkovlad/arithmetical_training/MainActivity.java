package com.dudkovlad.arithmetical_training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    TextView equation, statisticsonmain;
    EditText result;
    int number1;
    int number2;
    SecureRandom srand;
    SharedPreferences sharedPreferences;
    Time time_of_enteringin;
    Time time_of_last_multiply;
    long first_time_in;
    long last_time_in;
    long all_time_in;
    long this_week_time_in;
    long this_day_time_in;
    int count_of_multiplications;
    int current_week_count_of_multiplications;
    int current_day_count_of_multiplications;


    int easymode;
    Boolean showcountdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        time_of_last_multiply = new Time();
        time_of_last_multiply.setToNow();
        time_of_enteringin = new Time();
        time_of_enteringin.setToNow();

        srand = new SecureRandom(new byte[]{((byte) new SecureRandom().nextLong())});

        equation = (TextView)findViewById(R.id.equation);
        result = (EditText)findViewById(R.id.result);
        statisticsonmain = (TextView)findViewById(R.id.statisticsonmain);

        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(((long)number1 * (long)number2) + "")) {
                    current_week_count_of_multiplications++;
                    current_day_count_of_multiplications++;
                    count_of_multiplications++;
                    time_of_last_multiply.setToNow();
                    setRandom();
                    result.setText("");
                    if (easymode==0)
                        result.setText(getString(R.string.congratulations));

                }
            }
        });

    }

    protected void setRandom ()
    {
        switch (easymode)
        {
            case 0:
                number1 = Math.abs(srand.nextInt() % 100000000);
                number2 = Math.abs(srand.nextInt() % 100000000);
                break;
            case 1:
                do {
                    number1 = Math.abs(srand.nextInt() % 890) + 111;
                    number2 = Math.abs(srand.nextInt() % 890) + 111;
                }while (number1%10 == 0 || number2%10 == 0 || (number1/10)%10 == 0 || (number2/10)%10 == 0);
                break;
            case 2:
                do {
                    number1 = Math.abs(srand.nextInt() % 90) + 11;
                    number2 = Math.abs(srand.nextInt() % 890) + 111;
                }while (number1%10 == 0|| number2%10 == 0 || (number2/10)%10 == 0);

                if (srand.nextBoolean())
                {
                    number1 = number1 + number2;
                    number2 = number1 - number2;
                    number1 = number1 - number2;
                }
                break;
            case 3:
                do {
                    number1 = Math.abs(srand.nextInt() % 90) + 11;
                    number2 = Math.abs(srand.nextInt() % 90) + 11;
                }while (number1%10 == 0 || number2%10 == 0);break;
            case 4:
                do {
                    number1 = Math.abs(srand.nextInt() % 11) + 2;
                    number2 = Math.abs(srand.nextInt() % 90) + 11;
                }while (number2%10 == 0);
                if (srand.nextBoolean())
                {
                    number1 = number1 + number2;
                    number2 = number1 - number2;
                    number1 = number1 - number2;
                }
                break;
            case 5:
                number1 = Math.abs(srand.nextInt() % 21) + 2;
                number2 = Math.abs(srand.nextInt() % 21) + 2;
                break;
            case 6:
                number1 = Math.abs(srand.nextInt() % 11) + 2;
                number2 = Math.abs(srand.nextInt() % 11) + 2;
                break;
        }
        if (easymode == 0)
            equation.setText(number1 + "×\n" + number2);
        else
            equation.setText(number1 + "×" + number2);

        statisticsonmain.setText(
                getString(R.string.statistics_this_day)+"\n"+
                String.format("%.2f", (-time_of_enteringin.toMillis(false) + time_of_last_multiply.toMillis(false) + this_day_time_in) / 60000f) + getString(R.string.minute_count)+" | " +
                ((Integer) current_day_count_of_multiplications).toString() + getString(R.string.multiply_count)+" | " +
                (current_day_count_of_multiplications == 0 ? "0": String.format("%.2f", (this_day_time_in-time_of_enteringin.toMillis(false)+time_of_last_multiply.toMillis(false)) / ((float) current_day_count_of_multiplications) / 60000f)) + getString(R.string.speed) );

    }

    @Override
    protected void onResume() {
        result.requestFocus();
        sharedPreferences = this.getSharedPreferences("main_prefs", 0);
        time_of_enteringin.setToNow();
        time_of_last_multiply.setToNow();
        all_time_in = sharedPreferences.getLong("all_time_in",0);
        first_time_in = sharedPreferences.getLong("first_time_in", time_of_enteringin.toMillis(false));
        last_time_in = sharedPreferences.getLong("last_time_in", time_of_enteringin.toMillis(false));
        this_week_time_in= sharedPreferences.getLong("this_week_time_in", 0);
        this_day_time_in = sharedPreferences.getLong("this_day_time_in", 0);
        count_of_multiplications = sharedPreferences.getInt("count_of_multiplications", 0);
        current_week_count_of_multiplications = sharedPreferences.getInt("current_week_count_of_multiplications", 0);
        current_day_count_of_multiplications = sharedPreferences.getInt("current_day_count_of_multiplications", 0);

        Time temp_time = new Time();
        temp_time.set(last_time_in);
        temp_time.normalize(false);


        if (temp_time.yearDay!=time_of_enteringin.yearDay)
        {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            int count_of_days = sharedPreferences.getInt("count_of_days",0);
            count_of_days++;
            editor.putInt("count_of_multiplications_at[" + (count_of_days-1) + "]", current_day_count_of_multiplications);
            editor.putLong("time_in_at[" + (count_of_days-1) + "]", this_day_time_in);
            editor.putString("date_at[" + (count_of_days - 1) + "]", temp_time.monthDay + "." + (temp_time.month + 1) + "." + temp_time.year);
            editor.putInt("count_of_days", count_of_days);
            editor.commit();
            if (temp_time.getWeekNumber()!=time_of_enteringin.getWeekNumber())
            {
                this_week_time_in = 0;
                current_week_count_of_multiplications = 0;
            }
            last_time_in = time_of_enteringin.toMillis(true);
            this_day_time_in = 0;
            current_day_count_of_multiplications = 0;
        }

        easymode = sharedPreferences.getInt("easymodei",3);

        setRandom ();

        super.onResume();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("all_time_in", all_time_in - time_of_enteringin.toMillis(false) + time_of_last_multiply.toMillis(false));
        editor.putLong("first_time_in", first_time_in);
        editor.putLong("last_time_in", last_time_in);
        editor.putLong("this_week_time_in", this_week_time_in - time_of_enteringin.toMillis(false) + time_of_last_multiply.toMillis(false));
        editor.putLong("this_day_time_in", this_day_time_in - time_of_enteringin.toMillis(false) + time_of_last_multiply.toMillis(false));
        editor.putInt("count_of_multiplications", count_of_multiplications);
        editor.putInt("current_week_count_of_multiplications",current_week_count_of_multiplications);
        editor.putInt("current_day_count_of_multiplications",current_day_count_of_multiplications);
        editor.putInt("easymodei",easymode);

        editor.commit();
        super.onPause();
    }

    private Handler mHandler = new Handler();

    /**
     * показываем программную клавиатуру
     */
    protected void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(result, 0);
        }
    }

    private Runnable mShowInputMethodTask = new Runnable() {
        public void run() {
            showInputMethod();
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // если окно в фокусе, то ждем еще немного и показываем клавиатуру
            mHandler.postDelayed(mShowInputMethodTask, 0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            MainActivity.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

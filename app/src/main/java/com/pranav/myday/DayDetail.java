package com.pranav.myday;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pranav.myday.Utils.LetterImageView;

public class DayDetail extends AppCompatActivity {

    private ListView listView;
    private android.support.v7.widget.Toolbar toolbar;
    public static String[] Monday1;
    public static String[] Tuesday1;
    public static String[] Wednesday1;
    public static String[] Thursday1;
    public static String[] Friday1;
    public static String[] Monday2;
    public static String[] Tuesday2;
    public static String[] Wednesday2;
    public static String[] Thursday2;
    public static String[] Friday2;
    public static String[] Time;
    private String[] PreferredDay;
    private String[] PreferredTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timetable_day);

        setupUIViews();
        setupListView();
    }

    private void setupUIViews(){
        listView = findViewById(R.id.lvDayDetail);
    }

    private void setupListView(){

        Monday1 = getResources().getStringArray(R.array.Monday_1);
        Tuesday1 = getResources().getStringArray(R.array.Tuesday_1);
        Wednesday1 = getResources().getStringArray(R.array.Wednesday_1);
        Thursday1 = getResources().getStringArray(R.array.Thursday_1);
        Friday1 = getResources().getStringArray(R.array.Friday_1);
        Monday2 = getResources().getStringArray(R.array.Monday_2);
        Tuesday2 = getResources().getStringArray(R.array.Tuesday_2);
        Wednesday2 = getResources().getStringArray(R.array.Wednesday_2);
        Thursday2 = getResources().getStringArray(R.array.Thursday_2);
        Friday2 = getResources().getStringArray(R.array.Friday_2);

        Time = getResources().getStringArray(R.array.time);

        String selected_day = TimetableFragment.sharedPreferences.getString(TimetableFragment.SEL_DAY, null);

        if(selected_day.equalsIgnoreCase("Monday 1")){
            PreferredDay = Monday1;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Tuesday 1")){
            PreferredDay = Tuesday1;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Wednesday 1")){
            PreferredDay = Wednesday1;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Thursday 1")){
            PreferredDay = Thursday1;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Friday 1")){
            PreferredDay = Friday1;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Monday 2")){
            PreferredDay = Monday2;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Tuesday 2")){
            PreferredDay = Tuesday2;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Wednesday 2")){
            PreferredDay = Wednesday2;
            PreferredTime = Time;
        }else if(selected_day.equalsIgnoreCase("Thursday 2")){
            PreferredDay = Thursday2;
            PreferredTime = Time;
        }else{
            PreferredDay = Friday2;
            PreferredTime = Time;
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(DayDetail.this, PreferredDay, PreferredTime);
        listView.setAdapter(simpleAdapter);

    }

    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView subject, time;
        private String[] subjectArray;
        private String[] timeArray;
        private LetterImageView letterImageView;

        @SuppressWarnings("all")
        public SimpleAdapter(Context context, String[] subjectArray, String[] timeArray){
            mContext = context;
            this.subjectArray = subjectArray;
            this.timeArray = timeArray;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return subjectArray.length;
        }

        @Override
        public Object getItem(int position) {
            return subjectArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.item_day, null);
            }

            subject = convertView.findViewById(R.id.tvSubjectDayDetails);
            time = convertView.findViewById(R.id.tvTimeDayDetail);
            letterImageView = convertView.findViewById(R.id.ivDayDetails);

            subject.setText(subjectArray[position]);
            time.setText(timeArray[position]);

            letterImageView.setOval(true);
            letterImageView.setLetter(subjectArray[position].charAt(0));

            return convertView;

        }
    }

    @SuppressWarnings("all")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

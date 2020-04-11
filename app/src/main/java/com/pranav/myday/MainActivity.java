package com.pranav.myday;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_todo:
                    fragment = new TodoFragment();
                    break;

                case R.id.navigation_forecast:
                    fragment = new ForecastFragment();
                    break;

                case R.id.navigation_timetable:
                    fragment = new TimetableFragment();
                    break;

            }

            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new TodoFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @SuppressWarnings("all")
    public void deleteTask1(View view) {
        FragmentManager fm = getSupportFragmentManager();

        TodoFragment fragment = (TodoFragment) fm.findFragmentById(R.id.fragment_container);
        fragment.deleteTask(view);
    }
}

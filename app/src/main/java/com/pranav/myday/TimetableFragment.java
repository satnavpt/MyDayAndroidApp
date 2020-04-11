package com.pranav.myday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pranav.myday.Utils.LetterImageView;

import static android.content.Context.MODE_PRIVATE;


public class TimetableFragment extends Fragment {

    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void setupListView(){
        String[] week = getResources().getStringArray(R.array.week);

        ListAdapter adapter = new WeekAdapter(requireActivity(), R.layout.item_week, week);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Monday 1").apply();
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday 1").apply();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday 1").apply();
                        break;
                    }
                    case 3: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday 1").apply();
                        break;
                    }
                    case 4: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Friday 1").apply();
                        break;
                    }
                    case 5: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Monday 2").apply();
                        break;
                    }
                    case 6: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday 2").apply();
                        break;
                    }
                    case 7: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday 2").apply();
                        break;
                    }
                    case 8: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday 2").apply();
                        break;
                    }
                    case 9: {
                        startActivity(new Intent(requireContext(), DayDetail.class));
                        sharedPreferences.edit().putString(SEL_DAY, "Friday 2").apply();
                        break;
                    }
                    default:break;
                }
            }
        });

    }

    public class WeekAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater layoutInflater;
        private String[] week = new String[]{};

        public WeekAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.week = objects;
            layoutInflater = (LayoutInflater)requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            WeekAdapter.ViewHolder holder;
            if(convertView == null){
                holder = new WeekAdapter.ViewHolder();
                convertView = layoutInflater.inflate(resource, null);
                holder.ivLogo = convertView.findViewById(R.id.ivLetter);
                holder.tvWeek = convertView.findViewById(R.id.tvMain);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.ivLogo.setOval(true);
            holder.ivLogo.setLetter(week[position].charAt(0));
            holder.tvWeek.setText(week[position]);

            return convertView;
        }

        class ViewHolder{
            private LetterImageView ivLogo;
            private TextView tvWeek;
        }
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable_week, container, false);
        listView = view.findViewById(R.id.lvWeek);
        this.mContext = requireContext();
        sharedPreferences = mContext.getSharedPreferences("MY_DAY", MODE_PRIVATE);
        setupListView();
        return view;
    }
}

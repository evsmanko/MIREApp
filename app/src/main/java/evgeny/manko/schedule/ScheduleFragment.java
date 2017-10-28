package evgeny.manko.schedule;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import hirondelle.date4j.DateTime;

public class ScheduleFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    ArrayList<ArrayList<Lesson>> scheduleDay;
    public static DateTime dateTime;
    int weekNumber;

    static ScheduleFragment newInstance(int page) {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        scheduleFragment.setArguments(arguments);
        return scheduleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);



        dateTime = MainActivity.dateTime.plusDays(pageNumber);
        weekNumber = dateTime.getWeekIndex() - DateTime.forDateOnly(2017, 9, 1).getWeekIndex()+1;

        int getIndex = weekNumber%2==1? 0 : 1;

        if(dateTime.getDay()==4&&dateTime.getMonth()==11) scheduleDay = new ArrayList<>();
        else if(dateTime.getWeekDay()!=1) scheduleDay = MainActivity.schedule.get(getIndex).get(dateTime.getWeekDay()-2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shedule_fragment, null);



        ListView lessonsList = (ListView) view.findViewById(R.id.lessons_list);

        ArrayList<String> list = new ArrayList<>();



        if(scheduleDay!=null&&scheduleDay.size()!=0) for(int i = 0; i < 6; i++){
            if(scheduleDay.get(i).size()!=0){
                list.add(scheduleDay.get(i).get(0).name);
            }
        }

        String[] arr = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            arr[i] = list.get(i);
        }

        lessonsList.setAdapter(new ScheduleListAdapter(getContext(), scheduleDay, weekNumber));

        return view;
    }
}
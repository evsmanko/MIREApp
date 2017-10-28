package evgeny.manko.schedule;

/**
 * Created by Evgen on 12.10.2017.
 */


import java.util.ArrayList;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleListAdapter extends BaseAdapter{

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ArrayList<Lesson>> objects;
    ArrayList<Lesson> lessons;
    int weekNumber;

    ScheduleListAdapter(Context context, ArrayList<ArrayList<Lesson>> lessonsLists, int weekNumber) {
        ctx = context;
        objects = lessonsLists;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.weekNumber = weekNumber;
        lessons = new ArrayList<>();

        if(lessonsLists!=null) for(int i = 0; i < lessonsLists.size(); i++){
            for(int j = 0; j < lessonsLists.get(i).size(); j++){
                if(testLesson(lessonsLists.get(i).get(j))) lessons.add(lessonsLists.get(i).get(j));
            }
        }
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return lessons == null ? 0 : lessons.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.schedule_item, parent, false);
        }



        if(lessons.size()>position) {
            Lesson p = lessons.get(position);
            String typeLesson = Lesson.getLessonTypeString(p.type);
            String typeAndRoomText = "";
            if(typeLesson!=null){
                typeAndRoomText = typeLesson;
                if(p.room!=null&&!p.room.equals("")) typeAndRoomText += " в "+p.room;
            }
            else typeAndRoomText = p.room;


            ((TextView) view.findViewById(R.id.lesson_name)).setText(p.name);
            ((TextView) view.findViewById(R.id.teacher_name)).setText(p.teacherName);
            ((TextView) view.findViewById(R.id.room_text)).setText(typeAndRoomText);
            ((TextView) view.findViewById(R.id.start_time)).setText(UniversityInfo.getStartLessonTime(p.number));
            ((TextView) view.findViewById(R.id.finish_time)).setText(UniversityInfo.getFinishLessonTime(p.number));
        }


        return view;
    }


    boolean testLesson(Lesson lesson){
        switch (lesson.typeSchedule){
            case Lesson.TYPE_REPEAT_ALWAYS: return true;
            case Lesson.TYPE_REPEAT_BESIDES:
                boolean besides = true;
                for(int i = 0; i < lesson.weeks.length; i++) if(lesson.weeks[i]==weekNumber) besides = false;
                return besides;
            case Lesson.TYPE_REPEAT_FROM: return weekNumber >= lesson.weeks[0];
            case Lesson.TYPE_REPEAT_WEEKS:
                boolean has = false;
                for(int i = 0; i < lesson.weeks.length; i++) if(lesson.weeks[i]==weekNumber) has = true;
                return has;
            default: return false;
        }
    }
    // содержимое корзины
}
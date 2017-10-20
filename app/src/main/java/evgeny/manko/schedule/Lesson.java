package evgeny.manko.schedule;

public class Lesson {

    final static int TYPE_REPEAT_ALWAYS = 0;
    final static int TYPE_REPEAT_WEEKS = 1;
    final static int TYPE_REPEAT_FROM = 2;
    final static int TYPE_REPEAT_BESIDES = 3;

    final static int LESSON_TYPE_LECTURE = 0;
    final static int LESSON_TYPE_PRACTICE = 1;
    final static int LESSON_TYPE_LABARATORY = 2;

    String name;
    String room;
    int type;
    String teacherName;
    int typeSchedule;
    int[] weeks;
    int number;

    public Lesson(String name, String room, int type, String teacherName, int typeSchedule, int[] weeks, int number) {
        this.name = name;
        this.room = room;
        this.type = type;
        this.teacherName = teacherName;
        this.typeSchedule = typeSchedule;
        this.weeks = weeks;
        this.number = number;
    }

    public static String getLessonTypeString(int lessonType){
        switch (lessonType){
            case LESSON_TYPE_LECTURE: return "Лекция";
            case LESSON_TYPE_PRACTICE: return "Практика";
            case LESSON_TYPE_LABARATORY: return "Лабаратоная";
            default: return null;
        }
    }

}

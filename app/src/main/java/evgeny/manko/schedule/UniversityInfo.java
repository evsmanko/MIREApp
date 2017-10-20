package evgeny.manko.schedule;

import android.util.Log;

/**
 * Created by Evgen on 22.08.2017.
 */

public class UniversityInfo {

    private static final int FTISID = 0;
    private static final int FTIVID = 1;
    private static final int INTEGYID = 2;
    private static final int ITID = 3;
    private static final int KIBID = 4;
    private static final int KBISPID = 5;
    private static final int RTSID = 6;
    private static final int THTID = 7;
    private static final int IUSTROID = 8;

    private static String FTISNAME = "Физико-технологический институт";
    private static String INTEGYNAME = "Институт инновационных технологий и государственного управления";
    private static String ITNAME = "Институт информационных технологий";
    private static String KIBNAME = "Институт кибернетики";
    private static String KBISPNAME = "Институт комплексной безопасности и специального приборостроения";
    private static String RTSNAME = "Институт радиотехнических и телекоммуникационных систем";
    private static String THTNAME = "Институт тонких химических технологий";
    private static String IUSTRONAME = "Институт экономики и права";

    private static String MIREA = "на проспекте Вернадского 78";
    private static String MGUPI = "на улице Стромынка 20";
    private static String MITHT = "на проспекте Вернадского 86";


    public static int getInstituteIDbyGroup(String group){

        if (group.length()==10){
            if(group.equals("ТВБО-01-16")) return KBISPID;
        }
        if(group.length()>0)
            switch (group.charAt(0)){
                case 'Т': return FTISID;
                case 'Д': return FTISID;
                case 'Э': return FTIVID;
                case 'Г': return INTEGYID;
                case 'И': return ITID;
                case 'К': return KIBID;
                case 'О': return KBISPID;
                case 'П': return KBISPID;
                case 'Б': return KBISPID;
                case 'Р': return RTSID;
                case 'Х': return THTID;
                case 'У': return IUSTROID;
                default:
                    Log.d(UniversityInfo.class.getSimpleName(), " - This institute id is'nt implemented yet");
                    return -1;
        }

        return -1;

    }

    public static String getStartLessonTime(int number){
        switch (number){
            case 1 : return "9:00";
            case 2 : return "10:40";
            case 3 : return "13:00";
            case 4 : return "14:40";
            case 5 : return "16:20";
            case 6 : return "18:00";
            default: return "";
        }
    }

    public static String getFinishLessonTime(int number){
        switch (number){
            case 1 : return "10:30";
            case 2 : return "12:10";
            case 3 : return "14:30";
            case 4 : return "16:10";
            case 5 : return "17:50";
            case 6 : return "19:30";
            default: return "";
        }
    }

    public static String getInstituteNamebyID(int id){
        switch (id){
            case FTISID: return FTISNAME;
            case FTIVID: return FTISNAME;
            case INTEGYID: return INTEGYNAME;
            case ITID: return ITNAME;
            case KIBID: return KIBNAME;
            case KBISPID: return KBISPNAME;
            case RTSID: return RTSNAME;
            case THTID: return THTNAME;
            case IUSTROID: return IUSTRONAME;
            default: return "Институт не найден";
        }
    }

    public static String getScheduleURLbyID(int id, int courseNumber){
        switch (id){
            case FTISID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/15ibht";
                    case 2: return "https://api.myjson.com/bins/u985t";
                    case 3: return "https://api.myjson.com/bins/cznm9";
                    case 4: return "https://api.myjson.com/bins/17y72p";
                    case 5: return "https://api.myjson.com/bins/wmykx";
                    case 6: return null;
                }
            case FTIVID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/1h8ce9";
                    case 2: return "https://api.myjson.com/bins/17wz6f";
                    case 3: return "https://api.myjson.com/bins/cdrt5";
                    case 4: return null; //нет расписания
                    case 5: return "https://api.myjson.com/bins/cz7ex";
                    case 6: return null;
            }
            case INTEGYID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/881gn";
                    case 2: return "https://api.myjson.com/bins/kq55z";
                    case 3: return "https://api.myjson.com/bins/alrvr";
                    case 4: return "https://api.myjson.com/bins/n3vl3";
                    case 6: return null;
                }
            case ITID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/19724n";
                    case 2: return "https://api.myjson.com/bins/a2hfr";
                    case 3: return "https://api.myjson.com/bins/1adxc7";
                    case 4: return "https://api.myjson.com/bins/1azcxz";
                    case 6: return null;
                }
            case KIBID: switch (courseNumber){
                case 1: return "https://api.myjson.com/bins/1247qv";
                case 2: return "https://api.myjson.com/bins/1f7r1z";
                case 3: return "https://api.myjson.com/bins/g36d3";
                case 4: return "https://api.myjson.com/bins/aq9x3";
                case 5: return "https://api.myjson.com/bins/60y8n";
                case 6: return null;
            }
            case KBISPID:
                switch (courseNumber){
                case 1: return "https://api.myjson.com/bins/c9pz5";
                case 2: return "https://api.myjson.com/bins/dzktt";
                case 3: return "https://api.myjson.com/bins/el0fl";
                case 4: return "https://api.myjson.com/bins/18y4a9";
                case 5: return "https://api.myjson.com/bins/19jjw1";
                case 6: return null;
            }
            case RTSID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/li7yv";
                    case 2: return "https://api.myjson.com/bins/m3nkn";
                    case 3: return "https://api.myjson.com/bins/snf87";
                    case 4: return "https://api.myjson.com/bins/zurnb";
                    case 5: return "https://api.myjson.com/bins/16ejav";
                    case 6: return null;
            }
            case THTID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/x213r";
                    case 2: return "https://api.myjson.com/bins/19k4t3";
                    case 3: return "https://api.myjson.com/bins/mpaw7";
                    case 4: return "https://api.myjson.com/bins/hceg7";
                    case 6: return null;
                }
            case IUSTROID:
                switch (courseNumber){
                    case 1: return "https://api.myjson.com/bins/bokhj";
                    case 2: return "https://api.myjson.com/bins/6dt7b";
                    case 3: return "https://api.myjson.com/bins/1cnl5j";
                    case 4: return "https://api.myjson.com/bins/1hf1zr";
                    case 6: return null;
            }
            default: return "Институт не найден";
        }
    }

    public static String getInstituteCampusNamebyID(int id){
        switch (id){
            case FTISID: return MGUPI;
            case FTIVID: return MIREA;
            case INTEGYID: return MIREA;
            case ITID: return MIREA;
            case KIBID: return MIREA;
            case KBISPID: return MGUPI;
            case RTSID: return MIREA;
            case THTID: return MITHT;
            case IUSTROID: return MGUPI;
            default: return "Институт не найден";
        }
    }


}

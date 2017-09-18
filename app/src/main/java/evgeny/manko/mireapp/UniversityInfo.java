package evgeny.manko.mireapp;

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
    private static String IUSTRONAME = "Институт управления и стратегического развития организаций";

    private static String MIREA = "на проспекте Вернадского 78";
    private static String MGUPI = "на улице Стромынка 20";
    private static String MITHT = "на проспекте Вернадского 86";


    public static int getInstituteIDbyCharGroup(char c){
        switch (c){
            case 'Т': return FTISID;
            case 'Э': return FTIVID;
            case 'Г': return INTEGYID;
            case 'И': return ITID;
            case 'К': return KIBID;
            case 'Б': return KBISPID;
            case 'Р': return RTSID;
            case 'Х': return THTID;
            case 'У': return IUSTROID;
            default:
                Log.d(UniversityInfo.class.getSimpleName(), " - This institute id is'nt implemented yet");
                return -1;
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

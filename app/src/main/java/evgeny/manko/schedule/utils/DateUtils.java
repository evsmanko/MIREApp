package evgeny.manko.schedule.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by pinasol on 3/1/18.
 */

public class DateUtils {


    protected static final String DATETIME_FORMAT = "HH:mm dd.MM.yyyy";

    private static final Map<String, String> months = new HashMap<String, String>();
    static {
        months.put("01", "Янв");
        months.put("02", "Фев");
        months.put("03", "Мар");
        months.put("04", "Апр");
        months.put("05", "Мая");
        months.put("06", "Июн");
        months.put("07", "Июл");
        months.put("08", "Авг");
        months.put("09", "Сен");
        months.put("10", "Окт");
        months.put("11", "Ноя");
        months.put("12", "Дек");
    }

    public static SimpleDateFormat getDateTimeFormat() {
        return new SimpleDateFormat(DATETIME_FORMAT, Locale.getDefault());
    }

    public static String ParseDate(Date postDate, Date currentDate) {

        String postDateString = DateUtils.getDateTimeFormat().format(postDate);
        String currentDateString = DateUtils.getDateTimeFormat().format(currentDate);
        String postSubDate = postDateString.substring(6);
        String currentSubDate = currentDateString.substring(6);
        if (postSubDate.equals(currentSubDate)) {
            return postDateString.substring(0, 5);
        }

        return postSubDate.substring(0, 2) + " "
                + toSimpleMonth(postSubDate.substring(3, 5)) + " "
                + postDateString.substring(0, 5);
    }

    public static String toSimpleMonth(String text) {
            return months.get(text);
    }


}

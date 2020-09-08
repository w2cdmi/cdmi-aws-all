package pw.cdmi.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AlphaMath {
	
    /**
     * 生成随机字符串的字母形式
     */
    public final static int RANDOM_STRING = 1; //字符形式

    /**
     * 生成随机字符串的数字形式
     */
    public final static int RANDOM_NUMBER = 2; //数字形式
    
    private AlphaMath() {}

    public static Date add(Date date, int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, year);
        calendar.add(2, month);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static long subtration(Date date1, Date date2)
    {
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.setTime(date1);
        ecal.setTime(date2);
        long difInDays = (ecal.getTime().getTime() - scal.getTime().getTime()) / 0x5265c00L;
        return difInDays;
    }
    
    /**
     * 随机生成一定长度的字符或数字
     * @param radomLength 长度
     * @param type 类型表名生成的随机字符串是数字还是字母
     * @return 字符串
     */
    public static String getRandomValue(int radomLength, int type)
    {
        int seed = 0;
        String seedString = "";
        if (type == 0)
            type = RANDOM_NUMBER;
        if (type == RANDOM_STRING)
            seedString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        else if (type == RANDOM_NUMBER)
            seedString = "0123456789";
        else
            return "";

        String rString = "";

        Random seed_based = new Random();
        seed_based.setSeed(seed);
        seed = Math.abs(Math.abs(seed_based.nextInt()) + Math.abs((int) System.currentTimeMillis()));
        seed_based.setSeed(seed);

        for (int i = 0; i < radomLength; i++)
        {
            int begin;
            double tmp_begin = seed_based.nextDouble() * seedString.length();
            begin = (int) tmp_begin;
            rString = rString + seedString.substring(begin, begin + 1);
        }

        return rString;
    }
    
    /**
     * 随机生成一定长度的字符或数字
     * @param radomLength 长度
     * @param type 类型表名生成的随机字符串是数字还是字母
     * @return 字符串
     */
    public static Long getRandomLong(int radomLength)
    {
        int seed = 0;
        String seedString = "0123456789";

        String rString = "";

        Random seed_based = new Random();
        seed_based.setSeed(seed);
        seed = Math.abs(Math.abs(seed_based.nextInt()) + Math.abs((int) System.currentTimeMillis()));
        seed_based.setSeed(seed);

        for (int i = 0; i < radomLength; i++)
        {
            int begin;
            double tmp_begin = seed_based.nextDouble() * seedString.length();
            begin = (int) tmp_begin;
            rString = rString + seedString.substring(begin, begin + 1);
        }

        return Long.valueOf(rString);
    }
    
    /**
     * 随机生成一定长度的字符或数字
     * @param radomLength 长度
     * @param type 类型表名生成的随机字符串是数字还是字母
     * @return 字符串
     */
    public static String getRandomString(int radomLength)
    {
        int seed = 0;
        String seedString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuffer rString = new StringBuffer();

        Random seed_based = new Random();
        seed_based.setSeed(seed);
        seed = Math.abs(Math.abs(seed_based.nextInt()) + Math.abs((int) System.currentTimeMillis()));
        seed_based.setSeed(seed);

        for (int i = 0; i < radomLength; i++)
        {
            int begin;
            double tmp_begin = seed_based.nextDouble() * seedString.length();
            begin = (int) tmp_begin;
            rString = rString.append(seedString.substring(begin, begin + 1));
        }

        return rString.toString();
    }
}

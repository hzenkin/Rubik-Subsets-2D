package util;

public class ClockB {

    private long timeB, timeAlarm;
    private final String name;

    public ClockB() {
        name = "";
        timeB = System.currentTimeMillis();
    }

    public ClockB(String str) {
        timeB = System.currentTimeMillis();
        name = str;
    }

    public void reset() {
        timeB = System.currentTimeMillis();
        setAlarm(0);
    }

    public void setAlarm(double time) {
        timeAlarm = (long) (time * 1000);
    }

    public void setAlarm(double time, String name) {
        if (name.toLowerCase() == "minutes") {
            timeAlarm = (long) (time * 60000);
        } else if (name.toLowerCase() == "seconds") {
            timeAlarm = (long) time * 1000;
        }
    }

    public boolean alarmTimeNotPassed() {
        return timeAlarm > elapsedMillis();
    }

    public boolean alarmTimePassed() {
        return timeAlarm < elapsedMillis();
    }

    public double elapsedMinutes() {
        return elapsedMillis() / 60000.0;
    }

    public double elapsedSeconds() {
        return elapsedMillis() / 1000.0;
    }

    public String format() {
        return ClockB.Format(elapsedMillis());
    }

    public long elapsedMillis() {
        return System.currentTimeMillis() - timeB;
    }

    public void print(String str) {
        String tmp = ",  :" + name + ":(" + format() + ")";
        System.out.println(str + tmp);
    }

///////////////////////////////////////////////////////////////////////////////////////
//  Static functions
    public static void Sleep(double seconds) {
        long miliseconds = Math.round(1000.0 * seconds);
        if (miliseconds < 8) {
            return;
        }
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public String Format(long time) {
        String str = "";
        int m = (int) time / 60000;
        double th = 1000;
        double s = time / 1000.0 - m * 60;
        int rs = (int) (s * th);
        s = rs / th;
        if (0 < m) {
            str = m + " mins : ";
        }
        str += s + " secs";

        return str;
    }

///////////////////////////////////////////////////////////////////////////////////////
}

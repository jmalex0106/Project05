import java.time.LocalDate;
import java.util.Calendar;

public class Day {
    private int dayOfMonth;
    private Session[] sessions;
    private int month;
    private int year;

    public Day(int dayOfMonth, int month, int year, String store) {  //constructs a day object with 24 empty sessions
        Session[] temp = new Session[24];
        for (int i = 0; i < 24; i++) {
            temp[i] = new Session(i, dayOfMonth, month, year, store);
        }
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public Session getSessionAtHour(int hour) {
        return this.sessions[hour];
    }

    public void setSessionAtHour(int hour, Session session) {
        this.sessions[hour] = session;
    }

    public int getDayOfWeek() {
        return LocalDate.of(year, month + 1, dayOfMonth).getDayOfWeek().getValue();
    }
}
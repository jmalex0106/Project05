public class Year {
    private Month[] months;
    private int year;

    public Year(int year, String store) {
        this.year = year;
        Month[] temp = new Month[12];
        for (int i = 0; i < 12; i++) {
            temp[i] = new Month(i, year, store);
        }
        this.months = temp;
    }

    public Month getMonthAtInt(int month) {
        return this.months[month];
    }
}

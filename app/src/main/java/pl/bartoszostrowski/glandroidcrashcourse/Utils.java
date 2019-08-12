package pl.bartoszostrowski.glandroidcrashcourse;

public class Utils {
    public static int mapRatingToString(float rating) {
        int rate = (int) rating;

        switch (rate) {
            case 0:
                return R.string.star_0;
            case 1:
                return R.string.star_1;
            case 2:
                return R.string.star_2;
            case 3:
                return R.string.star_3;
            case 4:
                return R.string.star_4;
            case 5:
                return R.string.star_5;
        }

        return R.string.star_0;
    }
}

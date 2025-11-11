package theater;

/**
 * Calculator for comedy performances.
 *
 * @null This class does not accept null performance or play objects.
 */

public class ComedyCalculator extends AbstractPerformanceCalculator {
    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amountFor() {
        int result = Constants.COMEDY_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
            result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                    + Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD);
        }
        result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * getPerformance().getAudience();
        return result;
    }
}

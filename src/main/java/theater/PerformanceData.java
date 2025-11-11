package theater;

/**
 * Row data for one performance: exposes play name/type, audience,
 * and the computed amount for that performance.
 *
 * @non_null
 */
public class PerformanceData {
    private final Performance performance;
    private final Play play;

    /**
     * Constructs a PerformanceData row.
     *
     * @param performance the underlying performance
     * @param play the play associated with the performance
     */
    public PerformanceData(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    /**
     * Returns the display name.
     *
     * @return the display name of the play
     */
    public String getName() {
        return play.getName();
    }

    /**
     * Returns the type of the play.
     *
     * @return the type of the play (e.g., "tragedy", "comedy")
     */
    public String getType() {
        return play.getType();
    }

    /**
     * Returns the audience size.
     *
     * @return the audience size for this performance
     */
    public int getAudience() {
        return performance.getAudience();
    }

    /**
     * Calculates the amount owed for this performance based on the play type
     * and audience size.
     *
     * @return the amount in cents
     * @throws RuntimeException if the play type is unknown
     */
    public int amountFor() {
        int result;
        switch (play.getType()) {
            case "tragedy":
                result = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;

            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD);
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;

            default:
                throw new RuntimeException(
                        String.format("unknown type: %s", play.getType()));
        }
        return result;
    }
}

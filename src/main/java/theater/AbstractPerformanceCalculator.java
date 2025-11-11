package theater;

/**
 * Base performance calculator for different play types.
 *
 * @null This class does not accept null performance or play objects.
 */

public abstract class AbstractPerformanceCalculator {
    private final Performance performance;
    private final Play play;

    public AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    /**
     * Creates a performance calculator instance based on the play type.
     *
     * @param performance the performance being evaluated
     * @param play the play for the performance
     * @return a calculator for the specified performance and play
     * @throws IllegalArgumentException if the play type is unknown
     */
    public static AbstractPerformanceCalculator createPerformanceCalculator(
            Performance performance, Play play) {
        switch (play.getType()) {
            case Constants.TYPE_TRAGEDY:
                return new TragedyCalculator(performance, play);
            case Constants.TYPE_COMEDY:
                return new ComedyCalculator(performance, play);
            default:
                throw new IllegalArgumentException("Unknown play type: " + play.getType());
        }
    }

    /**
     * Calculates the amount owed for this performance.
     *
     * @return the amount owed in cents
     */

    public abstract int amountFor();

    /**
     * Calculates the volume credits for this performance, with comedy bonus.
     *
     * @return the volume credits earned for this performance
     */

    public int volumeCredits() {
        int result = Math.max(
                performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);

        if (Constants.TYPE_COMEDY.equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }
}

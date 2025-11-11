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
    private final AbstractPerformanceCalculator calculator;

    /**
     * Constructs a PerformanceData row.
     *
     * @param performance the underlying performance
     * @param play the play associated with the performance
     * @param calculator the performance calculator
     */

    public PerformanceData(Performance performance, Play play,
                           AbstractPerformanceCalculator calculator) {
        this.performance = performance;
        this.play = play;
        this.calculator = calculator;
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
        return calculator.amountFor();
    }

    /**
     * Returns the volume credits earned for this performance.
     *
     * @return the volume credits value
     */

    public int volumeCredits() {
        return calculator.volumeCredits();
    }

}

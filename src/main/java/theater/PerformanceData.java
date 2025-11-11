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
    private final int amount;
    private final int volumeCredits;

    /**
     * Constructs a PerformanceData row with precomputed values.
     *
     * @param performance the underlying performance
     * @param play the play associated with the performance
     * @param amount the computed amount in cents
     * @param volumeCredits the computed volume credits
     */

    public PerformanceData(Performance performance, Play play, int amount, int volumeCredits) {
        this.performance = performance;
        this.play = play;
        this.amount = amount;
        this.volumeCredits = volumeCredits;
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
        return amount;
    }

    /**
     * Returns the volume credits earned for this performance.
     *
     * @return the volume credits value
     */

    public int volumeCredits() {
        return volumeCredits;
    }

}

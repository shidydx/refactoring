package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private final StatementData statementData;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        // Split Phase: build data first, render later
        this.statementData = new StatementData(invoice, plays);
    }

    public String getInvoice() {
        return statementData.getInvoice().toString();
    }

    public Map<String, Play> getPlays() {
        return statementData.getPlays();
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     *
     * @non_null
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        return renderPlainText(statementData);
    }

    /**
     * Renders the prepared statement data in plain text format.
     *
     * @param data the prepared statement data
     * @return the formatted plain-text invoice string
     */

    private String renderPlainText(StatementData data) {
        final StringBuilder result =
                new StringBuilder("Statement for " + data.getCustomer() + System.lineSeparator());

        for (PerformanceData perfData : data.getPerformances()) {
            // one line per performance
            result.append("  ")
                    .append(perfData.getName())
                    .append(": ")
                    .append(usd(perfData.amountFor()))
                    .append(" (")
                    .append(perfData.getAudience())
                    .append(" seats)")
                    .append(System.lineSeparator());
        }

        result.append("Amount owed is ")
                .append(usd(data.totalAmount()))
                .append(System.lineSeparator())
                .append("You earned ")
                .append(data.volumeCredits())
                .append(" credits")
                .append(System.lineSeparator());

        return result.toString();
    }

    protected StatementData getStatementData() {
        return statementData;
    }
    /**
     * Formats a numeric amount into US currency for display.
     *
     * @param amount the amount in cents
     * @return the formatted currency string
     */

    protected String usd(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(amount / (double) Constants.PERCENT_FACTOR);
    }

    /**
     * Returns the amount owed (in cents) for a single performance.
     *
     * @param perf the performance being evaluated
     * @param play the play for the performance
     * @return the amount owed in cents
     */
    public int getAmount(final Performance perf, final Play play) {
        return AbstractPerformanceCalculator
                .createPerformanceCalculator(perf, play)
                .amountFor();
    }

    /**
     * Returns the volume credits for a single performance.
     *
     * @param perf the performance being evaluated
     * @param play the play for the performance
     * @return the volume credits earned
     */
    public int getVolumeCredits(final Performance perf, final Play play) {
        return AbstractPerformanceCalculator
                .createPerformanceCalculator(perf, play)
                .volumeCredits();
    }

    /**
     * Looks up a play by id from the provided map.
     *
     * @param playID the play identifier
     * @param plays  the map of play id to {@link Play}
     * @return the play with the given id, or {@code null} if not found
     */
    public Play getPlay(final String playID, final Map<String, Play> plays) {
        return plays.get(playID);
    }

    /**
     * Sums the amounts for all performances in the statement.
     *
     * @param data the statement data containing performances and plays
     * @return the total amount (in cents)
     */
    public int getTotalAmount(final StatementData data) {
        int total = 0;
        for (final Performance p : data.getPerformances()) {
            final Play play = data.getPlays().get(p.getPlayID());
            total += getAmount(p, play);
        }
        return total;
    }

    /**
     * Sums the volume credits for all performances in the statement.
     *
     * @param data the statement data containing performances and plays
     * @return the total volume credits
     */
    public int getTotalVolumeCredits(final StatementData data) {
        int total = 0;
        for (final Performance p : data.getPerformances()) {
            final Play play = data.getPlays().get(p.getPlayID());
            total += getVolumeCredits(p, play);
        }
        return total;
    }

}

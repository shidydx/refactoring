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

}

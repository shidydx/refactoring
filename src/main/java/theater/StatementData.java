package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aggregates all computed data needed to render a statement.
 *
 * @non_null
 */
public class StatementData {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final List<PerformanceData> performances = new ArrayList<>();

    /**
     * Builds the statement data from an invoice and play map.
     *
     * @param invoice the source invoice
     * @param plays   the lookup table of plays by ID
     */
    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;

        // Build the row data up front.
        for (Performance p : invoice.getPerformances()) {
            final Play play = plays.get(p.getPlayID());
            final AbstractPerformanceCalculator calculator =
                    AbstractPerformanceCalculator.createPerformanceCalculator(p, play);

            final int amount = calculator.amountFor();
            final int credits = calculator.volumeCredits();

            performances.add(new PerformanceData(p, play, amount, credits));
        }

    }

    /**
     * Returns the customer's name.
     *
     * @return the invoice customer's name
     */
    public String getCustomer() {
        return invoice.getCustomer();
    }

    /**
     * Returns the list of performance rows.
     *
     * @return the list of computed performance rows
     */
    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Computes the total amount across all performances.
     *
     * @return the total amount in cents
     */
    public int totalAmount() {
        int result = 0;
        for (PerformanceData perf : performances) {
            result += perf.amountFor();
        }
        return result;
    }

    /**
     * Computes the total volume credits across all performances.
     *
     * @return the total volume credits
     */
    public int volumeCredits() {
        int result = 0;
        for (PerformanceData perf : performances) {
            result += perf.volumeCredits();
        }
        return result;
    }

    // Accessors used by StatementPrinter (not otherwise required)

    /**
     * Returns the backing invoice.
     *
     * @return the backing invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Returns the play map.
     *
     * @return the play map used for lookups
     */
    public Map<String, Play> getPlays() {
        return plays;
    }
}

package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Data holder for the statement: builds computed rows and exposes totals. */
public class StatementData {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final List<PerformanceData> performances = new ArrayList<>();

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;

        // Build the row data up front
        for (Performance p : invoice.getPerformances()) {
            Play play = plays.get(p.getPlayID());
            performances.add(new PerformanceData(p, play));
        }
    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    public int totalAmount() {
        int result = 0;
        for (PerformanceData perf : performances) {
            result += perf.amountFor();
        }
        return result;
    }

    public int volumeCredits() {
        int result = 0;
        for (PerformanceData perf : performances) {
            result += Math.max(perf.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
            if ("comedy".equals(perf.getType())) {
                result += perf.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
            }
        }
        return result;
    }

    // These are used by StatementPrinter getters (not strictly needed otherwise)
    public Invoice getInvoice() { return invoice; }
    public Map<String, Play> getPlays() { return plays; }
}

package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private final Invoice invoice;
    private final Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String getInvoice() {
        return invoice.toString();
    }

    public Map<String, Play> getPlays() {
        return plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     *
     * @non_null
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        int totalAmount = 0;
        int volumeCredits = 0;

        final StringBuilder result =
                new StringBuilder("Statement for " + invoice.getCustomer() + System.lineSeparator());

        final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance p : invoice.getPerformances()) {
            final Play play = plays.get(p.getPlayID());

            int thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = Constants.TRAGEDY_BASE_AMOUNT;
                    if (p.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                        thisAmount += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                                * (p.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                    }
                    break;

                case "comedy":
                    thisAmount = Constants.COMEDY_BASE_AMOUNT;
                    if (p.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                        thisAmount += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                                + Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                                * (p.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD);
                    }
                    thisAmount += Constants.COMEDY_AMOUNT_PER_AUDIENCE * p.getAudience();
                    break;

                default:
                    throw new RuntimeException(String.format("unknown type: %s", play.getType()));
            }

            // volume credits
            volumeCredits += Math.max(
                    p.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);

            if ("comedy".equals(play.getType())) {
                volumeCredits += p.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
            }

            // line for this order (wrapped to avoid >120 chars)
            result.append("  ")
                    .append(play.getName())
                    .append(": ")
                    .append(frmt.format(thisAmount / (double) Constants.PERCENT_FACTOR))
                    .append(" (")
                    .append(p.getAudience())
                    .append(" seats)")
                    .append(System.lineSeparator());

            totalAmount += thisAmount;
        }

        result.append("Amount owed is ")
                .append(frmt.format(totalAmount / (double) Constants.PERCENT_FACTOR))
                .append(System.lineSeparator())
                .append("You earned ")
                .append(volumeCredits)
                .append(" credits")
                .append(System.lineSeparator());

        return result.toString();
    }
}

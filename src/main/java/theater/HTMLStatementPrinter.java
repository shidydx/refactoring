package theater;

import java.util.Map;

/**
 * Printer that renders the invoice in HTML format.
 */
public class HTMLStatementPrinter extends StatementPrinter {

    /**
     * Construct an HTML statement printer.
     *
     * @param invoice the invoice data
     * @param plays   the plays map
     */
    public HTMLStatementPrinter(Invoice invoice, Map<String, Play> plays) {
        super(invoice, plays);
    }

    /**
     * Produce an HTML-formatted statement.
     *
     * @return the rendered HTML invoice
     */
    @Override
    public String statement() {
        final StatementData data = getStatementData();
        final StringBuilder result = new StringBuilder(
                String.format("<h1>Statement for %s</h1>%n", data.getCustomer())
        );

        result.append("<table>").append(System.lineSeparator());
        result.append(String.format(" <caption>Statement for %s</caption>%n", data.getCustomer()));
        result.append(" <tr><th>play</th><th>seats</th><th>cost</th></tr>")
                .append(System.lineSeparator());

        for (PerformanceData perfData : data.getPerformances()) {
            result.append(String.format(
                    " <tr><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    perfData.getName(),
                    perfData.getAudience(),
                    usd(perfData.amountFor())
            ));
        }

        result.append("</table>").append(System.lineSeparator());
        result.append(String.format(
                "<p>Amount owed is <em>%s</em></p>%n", usd(data.totalAmount())
        ));
        result.append(String.format(
                "<p>You earned <em>%s</em> credits</p>%n", data.volumeCredits()
        ));

        return result.toString();
    }
}

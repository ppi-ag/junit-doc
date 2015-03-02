package de.ppi.junitdoc.reporter;

import java.util.List;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import de.ppi.junitdoc.doc.ArrangeActAssertSequence;
import de.ppi.junitdoc.doc.ArrangeActAssertSequence.Step;

/**
 * As simple example of an console-reporter.
 *
 * @author niels
 *
 */
public class AAAConsoleReporter extends TestWatcher {

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println(asReport(ArrangeActAssertSequence.sequence()));
        System.out.println(" => Test failed");
    }

    @Override
    protected void succeeded(Description description) {
        System.out.println(asReport(ArrangeActAssertSequence.sequence()));
        System.out.println(" => Test successful");
    }

    private String asReport(List<Step> sequence) {

        StringBuilder sb = new StringBuilder();
        for (Step step : sequence) {
            sb.append("\n");
            sb.append(step.getPhase());
            sb.append(" - ");
            sb.append(step.getDescription());
        }

        return sb.toString();
    }
}

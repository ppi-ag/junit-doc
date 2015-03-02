package aaa.demo;

import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ACT;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ARRANGE;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ASSUME;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;

import de.ppi.junitdoc.reporter.AAAConsoleReporter;

public class SetTest {

    @Rule
    public AAAConsoleReporter reporter = new AAAConsoleReporter();

    @Test
    public void addOnEmptySet() {

        ARRANGE("an empty set");

        Set<String> set = new HashSet<String>();

        ACT("adding an element to the set");

        set.add("a");

        ASSUME("the set should'nt be empty anymore");

        assertFalse(set.isEmpty());

    }
}

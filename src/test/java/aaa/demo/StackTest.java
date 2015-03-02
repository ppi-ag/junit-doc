package aaa.demo;

import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ACT;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ALSO;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.AND;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ARRANGE;
import static de.ppi.junitdoc.doc.ArrangeActAssertSequence.ASSUME;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Rule;
import org.junit.Test;

import de.ppi.junitdoc.reporter.AAAConsoleReporter;

public class StackTest {

    @Rule
    public AAAConsoleReporter reporter = new AAAConsoleReporter();

    @Test
    public void pushOnEmptyStack() {

        ARRANGE("an empty stack");

        Stack<Integer> stack = new Stack<Integer>();

        ACT("pushing an element onto the stack");

        stack.push(Integer.valueOf(42));

        ASSUME("the stack shouldn't be empty anymore");

        assertFalse(stack.isEmpty());
    }

    @Test
    public void pushElementOStack() {

        ARRANGE("a stack");

        Stack<Integer> stack = new Stack<Integer>();

        AND("an element");

        Integer element = Integer.valueOf(42);

        ACT("pushing the ARRANGE element onto the stack");

        stack.push(element);

        ASSUME("that element should be on top of the stack");

        assertEquals(element, stack.peek());

        ALSO("that element should be the last pushed element");

        assertEquals(element, stack.lastElement());
    }
}

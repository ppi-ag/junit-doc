package de.ppi.junitdoc.doc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Sequence of the following steps
 * <ol>
 * <li><code>ARRANGE</code> first necessary precondition and input. Can only use
 * once.</li>
 * <li><code>AND</code> further necessary preconditions and inputs. Can use 0 to
 * n-times.</li>
 * <li><code>ACT</code> on the object or method under test. Can only use once.</li>
 * <li><code>ASSERT</code> that the expected results have occurred. Can only use
 * once.</li>
 * <li><code>ALSO</code> Further asserts expected results have occurred. Can use
 * 0 to n-times.</li>
 * </ol>
 * 
 * @author niels
 *
 */
public final class ArrangeActAssertSequence {

    private static ThreadLocal<ArrangeActAssertSequence> tl =
            new ThreadLocal<ArrangeActAssertSequence>();

    private Phase currentPhase;
    private List<Step> sequence;

    private ArrangeActAssertSequence(Step initialStep) {
        this.sequence = new ArrayList<Step>();
        this.sequence.add(initialStep);
        this.currentPhase = Phase.ARRANGE;
    }

    /**
     * Delete the current sequence.
     */
    public static void reset() {
        tl.set(null);
    }

    /**
     * Get the current sequence.
     * 
     * @return the sequence of {@link Step}.
     */
    public static List<Step> sequence() {
        return Collections.unmodifiableList(tl.get().sequence);
    }

    /**
     * Arrange a necessary precondition or input.
     * 
     * @param description description of what is done.
     */
    public static void ARRANGE(String description) {
        tl.set(new ArrangeActAssertSequence(
                new Step(Phase.ARRANGE, description)));
    }

    /**
     * Further arrange of necessary precondition and input.
     * 
     * @param description description of what is done.
     */
    public static void AND(String description) {
        tl.get().addStep(new Step(Phase.AND, description));
    }

    /**
     * Act on the object or method under test.
     * 
     * @param description description of what ist done.
     */
    public static void ACT(String description) {
        tl.get().addStep(new Step(Phase.ACT, description));
    }

    /**
     * Assert that the expected result have occurred.
     * 
     * @param description description of what is assert.
     */
    public static void ASSUME(String description) {
        tl.get().addStep(new Step(Phase.ASSUME, description));
    }

    /**
     * Further assert that the expected result have occurred.
     * 
     * @param description description of what is assert.
     */
    public static void ALSO(String description) {
        tl.get().addStep(new Step(Phase.ALSO, description));
    }

    private void addStep(Step step) {
        this.currentPhase = currentPhase.toNextPhaseOrFail(step.getPhase());
        this.sequence.add(step);
    }

    // --
    /**
     * Declares all phases and ensures the correct order.
     * 
     * @author niels
     *
     */
    public static enum Phase {

        ARRANGE {
            @Override
            public Phase toNextPhaseOrFail(Phase nextPhase) {
                return toNextPhaseOrFail(nextPhase, AND, ACT);
            }
        },
        AND {
            @Override
            public Phase toNextPhaseOrFail(Phase nextPhase) {
                return toNextPhaseOrFail(nextPhase, AND, ACT);
            }
        },
        ACT {
            @Override
            public Phase toNextPhaseOrFail(Phase nextPhase) {
                return toNextPhaseOrFail(nextPhase, ASSUME);
            }
        },
        ASSUME {
            @Override
            public Phase toNextPhaseOrFail(Phase nextPhase) {
                return toNextPhaseOrFail(nextPhase, ALSO);
            }
        },
        ALSO {
            @Override
            public Phase toNextPhaseOrFail(Phase nextPhase) {
                return toNextPhaseOrFail(nextPhase, ALSO);
            }
        };

        abstract Phase toNextPhaseOrFail(Phase nextPhase);

        private static Phase toNextPhaseOrFail(Phase nextPhase,
                Phase permittedNextPhase, Phase... permittedNextPhases) {

            if (EnumSet.of(permittedNextPhase, permittedNextPhases).contains(
                    nextPhase)) {
                return nextPhase;
            }

            throw new IllegalStateException("new phase=" + nextPhase
                    + " is not permitted");
        }
    }

    /**
     * A single step of AAA-Pattern.
     * 
     * @author niels
     *
     */
    public static final class Step {

        private Phase phase;
        private String description;

        private Step(Phase phase, String description) {
            this.phase = phase;
            this.description = description;
        }

        /**
         * Get the current phase.
         * 
         * @return the current phase.
         */
        public Phase getPhase() {
            return phase;
        }

        /**
         * Get description what happens.
         * 
         * @return description what happens.
         */
        public String getDescription() {
            return description;
        }
    }
}

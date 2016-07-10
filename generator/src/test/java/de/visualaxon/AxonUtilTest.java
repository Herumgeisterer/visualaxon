package de.visualaxon;

import de.visualaxon.config.AbstractTest;
import de.visualaxon.generator.util.AxonUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Test;

import com.google.inject.Inject;

public class AxonUtilTest extends AbstractTest {

   @Inject
   private AxonUtil axonUtil;

   @Test
   public void shouldFindMultipleAppliedLombokEvents() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(LOMBOK_BODY_WITH_MULTIPLE_APPLIED_EVENTS);
      assertEquals(appliedEvents.size(), 2);
   }

   @Test
   public void shouldFindSingleAppliedLombokEvents() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(LOMBOK_BODY_WITH_SINGLE_APPLIED_EVENT);
      assertEquals(appliedEvents.size(), 1);
   }

   @Test
   public void shouldNotReturnClassWhenNotAppliedLombok() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(NOT_APPLIED_LOMBOK);
      assertEquals(appliedEvents.size(), 0);
   }

   @Test
   public void shouldNotReturnClassWhenNotApplied() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(NOT_APPLIED_CONSTRUCTOR);
      assertEquals(appliedEvents.size(), 0);
   }

   @Test
   public void shouldReturnEventType() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(CONSTRUCTOR_EVENT_WITH_METHOD_CALLS);
      assertEquals(appliedEvents.size(), 1);
      assertEquals(appliedEvents.get(0), "SomeEvent");
   }

   @Test
   public void shouldFindSingleAppliedEvents() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(BODY_WITH_SINGLE_APPLIED_EVENT);
      assertEquals(appliedEvents.size(), 1);
   }

   // TODO find event variable that is declared final
   @Test
   public void shouldFindEventTypeForVariable() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(BODY_WITH_SINGLE_APPLIED_EVENT_AS_VARIABLE);
      assertEquals(appliedEvents.size(), 1);
      assertEquals(appliedEvents.get(0), "SomeEvent");
   }

   @Test
   public void shouldFindEventTypeForMultipleAppliedEventsAsVariables() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(BODY_WITH_MULTIPLE_APPLIED_EVENTS_AS_VARIABLE);
      assertEquals(appliedEvents.size(), 2);
      assertTrue(appliedEvents.contains("Event"));
      assertTrue(appliedEvents.contains("SomeOtherEvent"));
   }

   @Test
   public void shouldFindMultipleAppliedEvents() throws Exception {
      final List<String> appliedEvents = axonUtil.getAppliedEvents(BODY_WITH_MULTIPLE_APPLIED_EVENTS);
      assertEquals(appliedEvents.size(), 2);
      assertTrue(appliedEvents.contains("EventWithParameter"));
      assertTrue(appliedEvents.contains("OtherEvent"));
   }

   @Test
   public void shouldAggregateRootAsAggregate() throws Exception {
      JavaUnit unit = Roaster.parseUnit("public class SomeClass extends AbstractAnnotatedAggregateRoot<String> {"
            + "}");
      final JavaClassSource type = unit.getGoverningType();
      assertTrue(axonUtil.isAggreagte(type));
   }

   @Test
   public void shouldNotReturnListenerAsAggregate() throws Exception {
      JavaUnit unit = Roaster.parseUnit("public class SomeListener {"
            + "}");
      final JavaClassSource type = unit.getGoverningType();
      assertFalse(axonUtil.isAggreagte(type));
   }
}

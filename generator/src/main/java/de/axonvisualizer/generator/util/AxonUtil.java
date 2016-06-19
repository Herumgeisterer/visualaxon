package de.axonvisualizer.generator.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class AxonUtil {

   public static final String EVENT_HANDLER_ANNOTATION_NAME = "EventHandler";
   public static final String ABSTRACT_ANNOTATED_AGGREGATE_ROOT = "AbstractAnnotatedAggregateRoot";
   public static final String SAGA_EVENT_HANDLER_NAME = "SagaEventHandler";
   public static final String EVENT_SOURCING_HANDLER_NAME = "EventSourcingHandler";
   public static final String ABSTRACT_ANNOTATED_ENTITY = "AbstractAnnotatedEntity";
   public static final String COMMAND_HANDLER = "CommandHandler";
   public static final String APPLY = "apply";

   public boolean isEventHandlingMethod(final String annotationName) {
      final boolean isEventHandler = annotationName.equals(EVENT_HANDLER_ANNOTATION_NAME);
      final boolean isEventSourcingHandler = annotationName.equals(EVENT_SOURCING_HANDLER_NAME);
      final boolean isSagaEventHandler = annotationName.equals(SAGA_EVENT_HANDLER_NAME);
      return isEventHandler || isEventSourcingHandler || isSagaEventHandler;
   }

   public boolean isCommandHandler(final MethodSource<JavaClassSource> method) {
      final List<AnnotationSource<JavaClassSource>> annotations = method.getAnnotations();

      for (AnnotationSource<JavaClassSource> annotation : annotations) {
         final String annotationName = annotation.getName();
         if (annotationName.equals(COMMAND_HANDLER)) {
            return true;
         }
      }

      return false;
   }

   public String getEventHandlerType(final String annotationName) {
      if (annotationName.equals(EVENT_SOURCING_HANDLER_NAME)) {
         return "Aggregate";
      }

      if (annotationName.equals(EVENT_HANDLER_ANNOTATION_NAME)) {
         return "Listener";
      }

      if (annotationName.equals(SAGA_EVENT_HANDLER_NAME)) {
         return "Saga";
      }

      return null;
   }

   public boolean isAggreagte(final JavaClassSource myClass) {
      final String superType = myClass.getSuperType();
      return superType.contains(ABSTRACT_ANNOTATED_AGGREGATE_ROOT) || superType.contains(ABSTRACT_ANNOTATED_ENTITY);
   }

   public List<String> getAppliedEvents(final String body) {
      List<String> appliedEvents = new ArrayList<>();

      final int appliedEventsCount = getAppliedEventsCount(body);

      int applySearchStart = 0;
      int builderSearchStart = 0;

      for (int i = 0; i < appliedEventsCount; i++) {
         final int applyIndex = body.indexOf("apply(", applySearchStart);
         final int builderIndex = body.indexOf(".builder", builderSearchStart);

         final int eventTypeNameStart = applyIndex + "apply(".length();
         final int builderOccurenceEnd = builderIndex + ".builder".length();

         final String event = body.substring(eventTypeNameStart, builderIndex);
         appliedEvents.add(event);

         applySearchStart = eventTypeNameStart;
         builderSearchStart = builderOccurenceEnd;
      }
      return appliedEvents;
   }

   private int getAppliedEventsCount(final String body) {
      return StringUtils.countMatches(body, APPLY);
   }
}

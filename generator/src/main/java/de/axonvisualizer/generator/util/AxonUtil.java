package de.axonvisualizer.generator.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

   private static final Pattern BUILDER_PATTERN = Pattern.compile("(?<=apply\\()(.*)(?=.builder)");
   private static final Pattern CONSTRUCTOR_PATTERN = Pattern.compile("(?<=apply\\(new )(.*)(?=\\(.*\\))");

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

      final List<String> lines = Arrays.stream(body.split(System.getProperty("line.separator")))
            .collect(Collectors.toList());

      final List<String> lombokEvents = lines.stream()
            .filter(this::isLombok)
            .map(s -> getEvent(s, BUILDER_PATTERN))
            .collect(Collectors.toList());

      final List<String> constuctorEvents = lines.stream()
            .filter(this::isConstructor)
            .map(s -> getEvent(s, CONSTRUCTOR_PATTERN))
            .collect(Collectors.toList());

      return Stream.concat(lombokEvents.stream(), constuctorEvents.stream())
            .collect(Collectors.toList());
   }

   private String getEvent(final String input, final Pattern regexPattern) {
      Matcher m = regexPattern.matcher(input);
      if (m.find()) {
         return m.group(1);
      }
      return null;
   }

   private boolean isConstructor(final String input) {
      return CONSTRUCTOR_PATTERN.asPredicate()
            .test(input);
   }

   private boolean isLombok(final String input) {
      return BUILDER_PATTERN.asPredicate()
            .test(input);
   }
}

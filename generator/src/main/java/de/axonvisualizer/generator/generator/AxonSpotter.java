package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.event.AggregateSpotted;
import de.axonvisualizer.generator.event.CommandHandlerSpotted;
import de.axonvisualizer.generator.event.EventHandlerSpotted;
import de.axonvisualizer.generator.event.EventListenerSpotted;
import de.axonvisualizer.generator.exception.AxonVisualizerException;
import de.axonvisualizer.generator.logging.Logger;
import de.axonvisualizer.generator.util.AxonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class AxonSpotter {

   @Inject
   private AxonUtil axonUtil;
   @Inject
   private EventBus eventBus;
   @Inject
   private Logger logger;

   public void getEventListener(final JavaClassSource klass) {
      logger.debug("Looking for eventlistener in class: " + klass.getQualifiedName());

      final List<MethodSource<JavaClassSource>> methods = klass.getMethods();
      final List<MethodSource<JavaClassSource>> eventHandlerMethods = new ArrayList<>();

      for (MethodSource<JavaClassSource> method : methods) {
         final boolean isEventHandlingMethod = method.getAnnotations()
               .stream()
               .anyMatch(javaClassSourceAnnotationSource -> axonUtil.isEventHandlingMethod(javaClassSourceAnnotationSource.getName()));

         if (isEventHandlingMethod) {
            eventHandlerMethods.add(method);
         }
      }

      if (eventHandlerMethods.isEmpty()) {
         return;
      }

      logger.info("Found " + klass.getQualifiedName() + " as eventlistener");
      eventBus.post(EventListenerSpotted.builder()
            .name(klass.getQualifiedName())
            .build());

      final List<String> eventTypes = eventHandlerMethods.stream()
            .map(javaClassSourceMethodSource -> javaClassSourceMethodSource.getParameters()
                  .get(0)
                  .getType()
                  .getName())
            .collect(Collectors.toList());
      logger.debug("Found " + eventHandlerMethods.size() + " eventhandler in listener " + klass.getQualifiedName() + " for events: " + eventTypes);
      for (MethodSource<JavaClassSource> method : eventHandlerMethods) {
         getEventHandler(method, klass.getQualifiedName());
      }

   }

   private void getEventHandler(final MethodSource<JavaClassSource> method, final String listenerName) {
      final String eventTypeName = method.getParameters()
            .get(0)
            .getType()
            .getName();

      String eventHandlerType = null;

      for (AnnotationSource<JavaClassSource> annotation : method.getAnnotations()) {
         eventHandlerType = axonUtil.getEventHandlerType(annotation.getName());
      }

      if (eventHandlerType == null) {
         throw new AxonVisualizerException(method.getName() + " is not a valid Axon EventHandler");
      }

      eventBus.post(EventHandlerSpotted.builder()
            .eventName(eventTypeName)
            .type(eventHandlerType)
            .listener(listenerName)
            .build());
   }

   public void getAggregate(final JavaClassSource klass) {
      logger.debug("Looking for Aggregate in class " + klass.getQualifiedName());

      if (!axonUtil.isAggreagte(klass)) {
         return;
      }

      final String aggregateName = klass.getQualifiedName();

      logger.info("Found aggregate in class " + klass.getQualifiedName());
      eventBus.post(AggregateSpotted.builder()
            .name(aggregateName)
            .build());

      final List<MethodSource<JavaClassSource>> methods = klass.getMethods();

      for (MethodSource<JavaClassSource> method : methods) {
         if (!axonUtil.isCommandHandler(method)) {
            continue;
         }

         final ParameterSource<JavaClassSource> command = method.getParameters()
               .get(0);

         final Type<JavaClassSource> commandType = command.getType();

         final List<String> appliedEvents = axonUtil.getAppliedEvents(method.getBody());

         logger.info("Found commandhandler for command " + commandType.getName() + " in class " + klass.getQualifiedName());
         eventBus.post(CommandHandlerSpotted.builder()
               .command(commandType.getName())
               .aggregate(aggregateName)
               .events(appliedEvents)
               .build());

      }
   }
}

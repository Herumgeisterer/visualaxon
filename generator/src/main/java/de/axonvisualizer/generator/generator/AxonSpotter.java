package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.data.Aggregate;
import de.axonvisualizer.generator.data.AxonData;
import de.axonvisualizer.generator.data.CommandHandler;
import de.axonvisualizer.generator.data.EventHandler;
import de.axonvisualizer.generator.data.EventListener;
import de.axonvisualizer.generator.exception.AxonVisualizerException;
import de.axonvisualizer.generator.util.AxonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

public class AxonSpotter {

   private AxonData.AxonDataBuilder dataBuilder = AxonData.builder();
   private AxonUtil axonUtil = new AxonUtil();

   public AxonData traverseFiles(final String inputRoot) {

      final Stream<Path> walk;
      try {
         walk = Files.walk(new File(inputRoot).toPath())
               .filter(p -> p.getFileName()
                     .toString()
                     .endsWith(".java"));
      } catch (IOException e) {
         throw new AxonVisualizerException(e.getMessage(), e.getCause());
      }

      walk.forEach(path -> {
         FileInputStream fileInputStream = null;
         try {
            fileInputStream = new FileInputStream(path.toFile());
         } catch (FileNotFoundException e) {
            throw new AxonVisualizerException(e.getMessage(), e.getCause());
         }

         JavaUnit unit = Roaster.parseUnit(fileInputStream);

         if (!unit.getGoverningType()
               .isClass()) {
            return;
         }

         JavaClassSource myClass = unit.getGoverningType();

         if (myClass.isAbstract()) {
            return;
         }

         if (axonUtil.isAggreagte(myClass)) {
            final Aggregate aggregate = getAggregate(myClass);
            dataBuilder.aggregate(aggregate);
         }

         final EventListener eventListener = getEventListener(myClass);
         if (eventListener != null) {
            dataBuilder.eventListener(eventListener);
         }
      });

      return dataBuilder.build();
   }

   private EventListener getEventListener(final JavaClassSource klass) {

      final List<MethodSource<JavaClassSource>> methods = klass.getMethods();

      final List<EventHandler> eventHandlers = new ArrayList<>();

      for (MethodSource<JavaClassSource> method : methods) {
         eventHandlers.addAll(method.getAnnotations()
               .stream()
               .filter(annotation -> axonUtil.isEventHandlingMethod(annotation.getName()))
               .map(annotation -> getEventHandler(method))
               .collect(Collectors.toList()));
      }

      if (eventHandlers.isEmpty()) {
         return null;
      }

      return EventListener.builder()
            .name(klass.getName())
            .eventHandlers(eventHandlers)
            .build();
   }

   private EventHandler getEventHandler(final MethodSource<JavaClassSource> method) {
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

      return EventHandler.builder()
            .eventType(eventTypeName)
            .type(eventHandlerType)
            .build();
   }

   private Aggregate getAggregate(final JavaClassSource myClass) {
      final String aggregateName = myClass.getName();

      final Aggregate.AggregateBuilder aggregateBuilder = Aggregate.builder()
            .name(aggregateName);

      final List<MethodSource<JavaClassSource>> methods = myClass.getMethods();

      for (MethodSource<JavaClassSource> method : methods) {
         if (!axonUtil.isCommandHandler(method)) {
            continue;
         }

         final ParameterSource<JavaClassSource> command = method.getParameters()
               .get(0);

         final Type<JavaClassSource> commandType = command.getType();

         final CommandHandler.CommandHandlerBuilder commandHandlerBuilder = CommandHandler.builder()
               .command(commandType.getName());

         final List<String> appliedEvents = axonUtil.getAppliedEvents(method.getBody());

         final CommandHandler commandHandler = commandHandlerBuilder.events(appliedEvents)
               .build();

         aggregateBuilder.commandHandler(commandHandler);
      }

      return aggregateBuilder.build();
   }
}

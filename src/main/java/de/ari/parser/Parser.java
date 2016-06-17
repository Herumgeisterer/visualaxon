package de.ari.parser;

import de.ari.data.Aggregate;
import de.ari.data.CommandHandler;
import de.ari.data.Data;
import de.ari.data.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Parser {

   @PostConstruct
   public void init() throws IOException {

      Data.DataBuilder dataBuilder = Data.builder();

      final Stream<Path> walk = Files.walk(new File("/Development/wee/wee-backend/").toPath())
            .filter(p -> p.getFileName()
                  .toString()
                  .endsWith(".java"));

      walk.forEach(path -> {
         try {
            final FileInputStream fileInputStream = new FileInputStream(path.toFile());

            JavaUnit unit = Roaster.parseUnit(fileInputStream);

            if (!unit.getGoverningType()
                  .isClass()) {
               return;
            }

            JavaClassSource myClass = unit.getGoverningType();

            if (myClass.isAbstract()) {
               return;
            }

            if (myClass.getSuperType()
                  .contains("AbstractAnnotatedAggregateRoot")) {
               final Aggregate aggregate = getAggregate(myClass);
               dataBuilder.aggregate(aggregate);
            }

         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
      });

      final Gson gson = new Gson();

      final String toJson = gson.toJson(dataBuilder.build());

      System.out.println(toJson);

   }

   private Aggregate getAggregate(final JavaClassSource myClass) {
      final String aggregateName = myClass.getName();

      final Aggregate.AggregateBuilder aggregateBuilder = Aggregate.builder()
            .name(aggregateName);

      final List<MethodSource<JavaClassSource>> methods = myClass.getMethods();

      for (MethodSource<JavaClassSource> method : methods) {
         final String body = method.getBody();

         if (!body.contains("apply(") || !body.contains("builder")) {
            continue;
         }

         final int builderIndex = body.indexOf(".builder");
         final int applyIndex = body.indexOf("apply(");

         final ParameterSource<JavaClassSource> command = method.getParameters()
               .get(0);

         final Type<JavaClassSource> commandType = command.getType();

         final CommandHandler.CommandHandlerBuilder commandHandlerBuilder = CommandHandler.builder()
               .command(commandType.getName());

         final String event = body.substring(applyIndex + "apply(".length(), builderIndex);

         final CommandHandler commandHandler = commandHandlerBuilder.event(Event.builder()
               .name(event)
               .build())
               .build();

         aggregateBuilder.commandHandler(commandHandler);
      }

      return aggregateBuilder.build();
   }
}

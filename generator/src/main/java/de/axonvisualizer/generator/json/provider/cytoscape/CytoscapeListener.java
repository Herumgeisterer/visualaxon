package de.axonvisualizer.generator.json.provider.cytoscape;

import de.axonvisualizer.generator.event.AggregateSpotted;
import de.axonvisualizer.generator.event.CommandHandlerSpotted;
import de.axonvisualizer.generator.event.EventHandlerSpotted;
import de.axonvisualizer.generator.event.EventListenerSpotted;
import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.provider.cytoscape.data.Data;
import de.axonvisualizer.generator.json.provider.cytoscape.data.Node;
import de.axonvisualizer.generator.json.writer.JsonWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class CytoscapeListener implements DataProvider {

   private List<Node> nodes = new ArrayList<>();
   private Map<String, String> eventToCommandHandlerId = new HashMap<>();

   @Inject
   @Named("outputPath")
   private String outputPath;

   @Inject
   private JsonWriter jsonWriter;

   @Subscribe
   public void onEvent(final CommandHandlerSpotted event) {
      final String commandHandlerId = event.getAggregate() + ":" + event.getCommand();
      nodes.add(Node.builder()
            .data(Data.builder()
                  .id(commandHandlerId)
                  .name(event.getCommand())
                  .parent(event.getAggregate())
                  .build())
            .build());

      for (String eventName : event.getEvents()) {
         eventToCommandHandlerId.put(eventName, commandHandlerId);
      }

      save();
   }

   @Subscribe
   public void onEvent(final AggregateSpotted event) {
      nodes.add(Node.builder()
            .data(Data.builder()
                  .id(event.getName())
                  .name(event.getName())
                  .build())
            .build());

      save();
   }

   @Subscribe
   public void onEvent(final EventListenerSpotted event) {
      nodes.add(Node.builder()
            .data(Data.builder()
                  .id(event.getName())
                  .name(event.getName())
                  .build())
            .build());

      save();
   }

   @Subscribe
   public void onEvent(final EventHandlerSpotted event) {
      final String eventHandlerId = event.getListener() + ":" + event.getEventName();
      nodes.add(Node.builder()
            .data(Data.builder()
                  .id(eventHandlerId)
                  .name(event.getEventName())
                  .parent(event.getListener())
                  .build())
            .build());

      final String commandHandlerId = eventToCommandHandlerId.get(event.getEventName());

      if (commandHandlerId != null) {
         nodes.add(Node.builder()
               .data(Data.builder()
                     .source(commandHandlerId)
                     .target(eventHandlerId)
                     .build())
               .build());
      }

      save();
   }

   private void save() {
      jsonWriter.write(new File(outputPath), nodes);
   }
}

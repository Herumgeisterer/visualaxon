package de.ari.json.cytoscape;

import de.ari.data.Aggregate;
import de.ari.data.AxonData;
import de.ari.data.CommandHandler;
import de.ari.data.EventHandler;
import de.ari.data.EventListener;
import de.ari.json.JsonProvider;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CytoscapeJsonProvider implements JsonProvider {

   private List<Node> nodes = new ArrayList<>();

   @Override
   public String getJson(final AxonData axonData) {

      final List<Aggregate> aggregates = axonData.getAggregates();

      for (Aggregate aggregate : aggregates) {
         nodes.add(Node.builder()
               .data(Data.builder()
                     .id(aggregate.getName())
                     .name(aggregate.getName())
                     .build())
               .build());

         final List<CommandHandler> commandHandlers = aggregate.getCommandHandlers();

         for (CommandHandler commandHandler : commandHandlers) {
            nodes.add(Node.builder()
                  .data(Data.builder()
                        .id(commandHandler.getCommand())
                        .name(commandHandler.getCommand())
                        .parent(aggregate.getName())
                        .build())
                  .build());

            for (String event : commandHandler.getEvents()) {
               if (!hasEventHandler(event, axonData)) {
                  continue;
               }
               nodes.add(Node.builder()
                     .data(Data.builder()
                           .source(commandHandler.getCommand())
                           .target(event)
                           .build())
                     .build());
            }
         }
      }

      for (EventListener listener : axonData.getEventListeners()) {
         nodes.add(Node.builder()
               .data(Data.builder()
                     .id(listener.getName())
                     .name(listener.getName())
                     .build())
               .build());

         for (EventHandler eventHandler : listener.getEventHandlers()) {
            nodes.add(Node.builder()
                  .data(Data.builder()
                        .id(eventHandler.getEventType())
                        .name(eventHandler.getEventType())
                        .parent(listener.getName())
                        .build())
                  .build());
         }
      }

      return new Gson().toJson(nodes);
   }

   private boolean hasEventHandler(final String event, final AxonData axonData) {
      for (EventListener eventListener : axonData.getEventListeners()) {
         for (EventHandler eventHandler : eventListener.getEventHandlers()) {
            if (eventHandler.getEventType()
                  .equals(event)) {
               return true;
            }
         }
      }

      return false;
   }
}

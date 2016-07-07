package de.axonvisualizer.config;

import de.axonvisualizer.generator.event.AggregateSpotted;
import de.axonvisualizer.generator.event.CommandHandlerSpotted;
import de.axonvisualizer.generator.event.EventHandlerSpotted;
import de.axonvisualizer.generator.event.EventListenerSpotted;

import com.google.common.eventbus.Subscribe;

public interface TestHandler {
   @Subscribe
   void handle(final AggregateSpotted aggregateSpotted);

   @Subscribe
   void handle(final CommandHandlerSpotted aggregateSpotted);

   @Subscribe
   void handle(final EventListenerSpotted aggregateSpotted);

   @Subscribe
   void handle(final EventHandlerSpotted aggregateSpotted);
}
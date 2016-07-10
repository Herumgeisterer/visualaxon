package de.visualaxon.config;

import de.visualaxon.generator.event.AggregateSpotted;
import de.visualaxon.generator.event.CommandHandlerSpotted;
import de.visualaxon.generator.event.EventHandlerSpotted;
import de.visualaxon.generator.event.EventListenerSpotted;

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
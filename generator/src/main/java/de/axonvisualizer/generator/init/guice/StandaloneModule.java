package de.axonvisualizer.generator.init.guice;

import de.axonvisualizer.generator.logging.Log4JLogger;
import de.axonvisualizer.generator.logging.Logger;

import com.google.inject.AbstractModule;

public class StandaloneModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(Logger.class).to(Log4JLogger.class);
   }
}
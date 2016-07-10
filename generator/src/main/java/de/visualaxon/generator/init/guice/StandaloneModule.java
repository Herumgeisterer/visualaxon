package de.visualaxon.generator.init.guice;

import de.visualaxon.generator.logging.Log4JLogger;
import de.visualaxon.generator.logging.Logger;

import com.google.inject.AbstractModule;

public class StandaloneModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(Logger.class).to(Log4JLogger.class);
   }
}
package de.axonvisualizer.generator.init.guice;

import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeDataProvider;
import de.axonvisualizer.generator.json.writer.JsonWriter;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;
import de.axonvisualizer.generator.logging.Log4JLogger;
import de.axonvisualizer.generator.logging.Logger;

import com.google.inject.AbstractModule;

public class StandaloneModule extends AbstractModule {

   @Override
   protected void configure() {

      bind(JsonWriter.class).to(GsonWriter.class);
      bind(DataProvider.class).to(CytoscapeDataProvider.class);
      bind(Logger.class).to(Log4JLogger.class);
   }
}
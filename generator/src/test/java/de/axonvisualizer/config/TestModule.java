package de.axonvisualizer.config;

import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeListener;
import de.axonvisualizer.generator.json.writer.JsonWriter;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;
import de.axonvisualizer.generator.logging.Log4JLogger;
import de.axonvisualizer.generator.logging.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class TestModule extends AbstractModule {

   @Override
   protected void configure() {

      bind(JsonWriter.class).to(GsonWriter.class);
      bind(Logger.class).to(Log4JLogger.class);
      bind(DataProvider.class).to(CytoscapeListener.class);
      bindConstant().annotatedWith(Names.named("outputPath"))
            .to("somepath");
      bindConstant().annotatedWith(Names.named("inputRoot"))
            .to("/Development/axonvisualizer");
      bind(EventBus.class).asEagerSingleton();
   }
}
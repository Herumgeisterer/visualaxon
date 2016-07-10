package de.visualaxon.config;

import de.visualaxon.generator.json.provider.DataProvider;
import de.visualaxon.generator.json.provider.cytoscape.CytoscapeListener;
import de.visualaxon.generator.json.writer.JsonWriter;
import de.visualaxon.generator.json.writer.gson.GsonWriter;
import de.visualaxon.generator.logging.Log4JLogger;
import de.visualaxon.generator.logging.Logger;

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
            .to("/Development/visualaxon");
      bind(EventBus.class).asEagerSingleton();
   }
}
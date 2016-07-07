package de.axonvisualizer.generator.init.guice;

import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeListener;
import de.axonvisualizer.generator.json.writer.JsonWriter;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class BaseModule extends AbstractModule {

   @Override
   protected void configure() {

      bind(JsonWriter.class).to(GsonWriter.class);
      bind(DataProvider.class).to(CytoscapeListener.class);
      bindConstant().annotatedWith(Names.named("outputPath"))
            .to(outputPath);
      bindConstant().annotatedWith(Names.named("inputRoot"))
            .to(inputRoot);
      bind(EventBus.class).asEagerSingleton();
   }


   public BaseModule(final String inputRoot, final String outputPath) {
      this.inputRoot = inputRoot;
      this.outputPath = outputPath;
   }

   public BaseModule() {
   }

   private String inputRoot;
   private String outputPath;
}
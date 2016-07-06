package de.axonvisualizer.generator.init.guice;

import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeListener;
import de.axonvisualizer.generator.json.writer.JsonWriter;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;
import de.axonvisualizer.generator.logging.Logger;
import de.axonvisualizer.generator.logging.MavenPluginLogger;

import org.apache.maven.plugin.logging.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class MavenPluginModule extends AbstractModule {

   private Log log;
   private String outputPath;

   @Override
   protected void configure() {

      bind(JsonWriter.class).to(GsonWriter.class);
      bind(DataProvider.class).to(CytoscapeListener.class);
      bind(Logger.class).to(MavenPluginLogger.class);
      bindConstant().annotatedWith(Names.named("outputPath"))
            .to(outputPath);
   }

   public MavenPluginModule(final Log log, final String outputPath) {
      this.log = log;
      this.outputPath = outputPath;
   }

   @Provides
   public Log log() {
      return log;
   }
}
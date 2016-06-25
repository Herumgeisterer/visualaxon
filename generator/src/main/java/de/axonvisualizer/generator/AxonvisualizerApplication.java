package de.axonvisualizer.generator;

import de.axonvisualizer.generator.generator.Generator;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeDataProvider;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;
import de.axonvisualizer.generator.logging.Log4JLogger;

import java.io.File;

public class AxonvisualizerApplication {

   public static final String INPUT_ROOT = "/Development/wee/wee-backend/";
   public static final String OUTPUT_PATH = "/Development/axonvisualizer/webapp/app/data/output.json";

   public static void main(String[] args) {
      final Generator generator = new Generator(new CytoscapeDataProvider(), new GsonWriter(), new Log4JLogger());
      generator.generateFile(new File(INPUT_ROOT), new File(OUTPUT_PATH));
   }
}

package de.axonvisualizer.generator;

import de.axonvisualizer.generator.generator.Generator;
import de.axonvisualizer.generator.json.provider.simple.SimpleDataProvider;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;

public class AxonvisualizerApplication {

   public static final String INPUT_ROOT = "/Development/wee/wee-backend/";
   public static final String OUTPUT_PATH = "/Development/axonvisualizer/webapp/app/data/output.json";

   public static void main(String[] args) {
      final Generator generator = new Generator(new SimpleDataProvider(), new GsonWriter());
      generator.generateFile(INPUT_ROOT, OUTPUT_PATH);
   }
}

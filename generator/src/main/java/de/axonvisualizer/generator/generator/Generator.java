package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.data.AxonData;
import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.writer.JsonWriter;

public class Generator {

   private DataProvider dataProvider;
   private JsonWriter jsonWriter;

   public Generator(final DataProvider dataProvider, final JsonWriter jsonWriter) {
      this.dataProvider = dataProvider;
      this.jsonWriter = jsonWriter;
   }

   public void generateFile(final String inputRoot, final String outputPath) {
      final AxonData axonData = new AxonSpotter().traverseFiles(inputRoot);

      jsonWriter.write(outputPath, dataProvider.getData(axonData));
   }
}
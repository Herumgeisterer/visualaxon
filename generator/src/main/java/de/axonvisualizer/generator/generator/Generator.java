package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.data.AxonData;
import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.json.writer.JsonWriter;
import de.axonvisualizer.generator.logging.Logger;

import java.io.File;

import com.google.inject.Inject;

public class Generator {

   private DataProvider dataProvider;
   private JsonWriter jsonWriter;
   private Logger logger;
   private AxonSpotter axonSpotter;

   @Inject
   public Generator(final DataProvider dataProvider, final JsonWriter jsonWriter, final Logger logger, final AxonSpotter axonSpotter) {
      this.dataProvider = dataProvider;
      this.jsonWriter = jsonWriter;
      this.logger = logger;
      this.axonSpotter = axonSpotter;
   }

   public void generateFile(final File inputRoot, final File outputPath) {
      final AxonData axonData = axonSpotter.traverseFiles(inputRoot);

      jsonWriter.write(outputPath, dataProvider.getData(axonData));

      logger.info("done");
   }
}

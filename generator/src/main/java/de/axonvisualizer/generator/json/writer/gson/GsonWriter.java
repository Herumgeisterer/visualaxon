package de.axonvisualizer.generator.json.writer.gson;

import de.axonvisualizer.generator.exception.AxonVisualizerException;
import de.axonvisualizer.generator.json.writer.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;

public class GsonWriter implements JsonWriter {

   @Override
   public void write(final String outputFilePath, final Object object) {

      File outPutFile = new File(outputFilePath);

      if (outPutFile.exists()) {
         final boolean deleted = outPutFile.delete();
         if (!deleted) {
            throw new AxonVisualizerException("Could not delete old file " + outputFilePath + ". File could be inconsistent state.");
         }
      }
      try {
         try (Writer writer = new FileWriter(new File(outputFilePath))) {
            Gson gson = new Gson();
            gson.toJson(object, writer);
         }
      } catch (IOException e) {
         throw new AxonVisualizerException(e.getMessage(), e.getCause());
      }
   }
}

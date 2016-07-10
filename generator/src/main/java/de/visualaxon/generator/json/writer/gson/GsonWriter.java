package de.visualaxon.generator.json.writer.gson;

import de.visualaxon.generator.exception.VisualAxonException;
import de.visualaxon.generator.json.writer.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;

public class GsonWriter implements JsonWriter {

   @Override
   public void write(final File outputFilePath, final Object object) {

      if (outputFilePath.exists()) {
         final boolean deleted = outputFilePath.delete();
         if (!deleted) {
            throw new VisualAxonException("Could not delete old file " + outputFilePath + ". File could be inconsistent state.");
         }
      }
      try {
         try (Writer writer = new FileWriter(outputFilePath)) {
            Gson gson = new Gson();
            gson.toJson(object, writer);
         }
      } catch (IOException e) {
         throw new VisualAxonException(e.getMessage(), e.getCause());
      }
   }
}

package de.axonvisualizer.generator.json.writer;

import java.io.File;

public interface JsonWriter {

   void write(final File outputFile, final Object jsonString);
}

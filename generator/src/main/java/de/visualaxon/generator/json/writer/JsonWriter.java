package de.visualaxon.generator.json.writer;

import java.io.File;

public interface JsonWriter {

   void write(final File outputFile, final Object object);
}

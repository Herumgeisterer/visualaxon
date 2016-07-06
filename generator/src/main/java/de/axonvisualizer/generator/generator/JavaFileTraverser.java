package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.exception.AxonVisualizerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class JavaFileTraverser {

   @Inject
   @Named("inputRoot")
   private String inputRoot;

   public void traverse(final TraverseCallback traverseCallback) {
      try {
         Files.walk(new File(inputRoot).toPath())
               .filter(p -> p.getFileName()
                     .toString()
                     .endsWith(".java"))
               .forEach(traverseCallback::onFile);
      } catch (IOException e) {
         throw new AxonVisualizerException(e.getMessage(), e.getCause());
      }
   }

   public interface TraverseCallback {
      void onFile(final Path path);
   }
}

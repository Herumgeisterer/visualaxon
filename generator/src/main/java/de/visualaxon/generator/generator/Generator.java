package de.visualaxon.generator.generator;

import de.visualaxon.generator.json.provider.DataProvider;
import de.visualaxon.generator.logging.Logger;

import java.io.File;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Generator {

   @Inject
   private JavaFileTraverser javaFileTraverser;

   @Inject
   private EventBus eventBus;

   @Inject
   private DataProvider listener;

   @Inject
   private Logger logger;

   @Inject
   @Named("inputRoot")
   private String inputRoot;

   @Inject
   @Named("outputPath")
   private String outputPath;

   public void generateFile() {
      final File inputputRoot = new File(inputRoot);
      final File outputFile = new File(outputPath);

      if (!inputputRoot.exists()) {
         throw new IllegalArgumentException("Given inputroot " + inputputRoot.getAbsolutePath() + " does not exist");
      }

      if (!inputputRoot.isDirectory()) {
         throw new IllegalArgumentException("Given inputroot " + inputputRoot.getAbsolutePath() + " is not a directory");
      }

      if (!inputputRoot.canRead()) {
         throw new IllegalArgumentException("Given inputroot " + inputputRoot.getAbsolutePath() + " is not readable");
      }

      final File parentOutputFile = outputFile.getParentFile();

      if (parentOutputFile == null) {
         throw new IllegalArgumentException("Parent directory for outputfile " + outputFile.getAbsolutePath() + " cannot be created.");
      }

      if (!parentOutputFile.canRead()) {
         throw new IllegalArgumentException("Cannout write outputfile to " + parentOutputFile.getAbsolutePath() + " because of missing read permission.");
      }

      if (!parentOutputFile.canWrite()) {
         throw new IllegalArgumentException("Cannout write outputfile to " + parentOutputFile.getAbsolutePath() + " because of missing write permission.");
      }

      if (!parentOutputFile.exists()) {
         final boolean mkdirs = parentOutputFile.mkdirs();

         if (!mkdirs) {
            throw new IllegalArgumentException("Could not creat directory " + parentOutputFile.getAbsolutePath() + ". Are permissions for the current user set?");
         }
      }

      logger.info("Starting generate file with\n"
            + "\tinputputroot: " + inputputRoot + "\n"
            + "\toutput: " + outputPath);

      eventBus.register(listener);

      javaFileTraverser.traverseFiles();

      logger.info("Done");
   }
}

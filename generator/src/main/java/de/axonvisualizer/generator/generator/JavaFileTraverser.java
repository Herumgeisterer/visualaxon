package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.exception.AxonVisualizerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class JavaFileTraverser {

   @Inject
   @Named("inputRoot")
   private String inputRoot;

   @Inject
   private AxonSpotter javaFileParser;

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

   public void traverseFiles() {
      traverse(path -> {
         FileInputStream fileInputStream = null;
         try {
            fileInputStream = new FileInputStream(path.toFile());
         } catch (FileNotFoundException e) {
            throw new AxonVisualizerException(e.getMessage(), e.getCause());
         }

         final Optional<JavaClassSource> classSource = getClass(fileInputStream);

         if (!isCandidate(classSource)) {
            return;
         }

         final JavaClassSource klass = classSource.get();

         javaFileParser.getAggregate(klass);
      });

      traverse(path -> {
         FileInputStream fileInputStream = null;
         try {
            fileInputStream = new FileInputStream(path.toFile());
         } catch (FileNotFoundException e) {
            throw new AxonVisualizerException(e.getMessage(), e.getCause());
         }

         final Optional<JavaClassSource> classSource = getClass(fileInputStream);

         if (!isCandidate(classSource)) {
            return;
         }

         final JavaClassSource klass = classSource.get();

         javaFileParser.getEventListener(klass);
      });
   }

   private Optional<JavaClassSource> getClass(final FileInputStream fileInputStream) {
      JavaUnit unit = Roaster.parseUnit(fileInputStream);

      if (!unit.getGoverningType()
            .isClass()) {
         return Optional.absent();
      }

      return Optional.of(unit.getGoverningType());
   }

   private boolean isCandidate(Optional<JavaClassSource> javaClassSource) {
      return javaClassSource.isPresent() && !javaClassSource.get()
            .isAbstract();

   }

   public interface TraverseCallback {
      void onFile(final Path path);
   }
}

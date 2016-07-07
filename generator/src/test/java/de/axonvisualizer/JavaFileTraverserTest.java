package de.axonvisualizer;

import de.axonvisualizer.config.AbstractTest;
import de.axonvisualizer.generator.generator.JavaFileTraverser;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Inject;

public class JavaFileTraverserTest extends AbstractTest {

   @Inject
   private JavaFileTraverser javaFileTraverser;

   @Test
   public void shouldOnlyTraverseJavaFiles() throws Exception {
      javaFileTraverser.traverse(path -> Assert.assertTrue(path.toAbsolutePath()
            .toString()
            .endsWith(".java")));
   }
}

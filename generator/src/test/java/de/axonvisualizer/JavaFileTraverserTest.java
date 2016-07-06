package de.axonvisualizer;

import de.axonvisualizer.generator.generator.JavaFileTraverser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
@GuiceModules(TestModule.class)
public class JavaFileTraverserTest {

   @Inject
   private JavaFileTraverser javaFileTraverser;

   @Test
   public void shouldOnlyTraverseJavaFiles() throws Exception {

      javaFileTraverser.traverse(path -> Assert.assertTrue(path.toAbsolutePath()
            .toString()
            .endsWith(".java")));
   }
}

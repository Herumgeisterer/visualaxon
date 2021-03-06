package de.visualaxon.generator;

import de.visualaxon.generator.generator.Generator;
import de.visualaxon.generator.init.guice.BaseModule;
import de.visualaxon.generator.init.guice.MavenPluginModule;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Mojo(name = "generate", requiresDependencyCollection = ResolutionScope.COMPILE, aggregator = true)
public class GenerateGoal extends AbstractMojo {

   @Parameter(defaultValue = "${project.basedir}", property = "baseDir", required = true)
   private File rootDir;

   @Parameter(defaultValue = "${project.basedir}/output.json", property = "outputFilePath", required = true)
   private File outputFilePath;

   public void execute() throws MojoExecutionException {
      Injector injector = Guice.createInjector(new BaseModule(rootDir.getAbsolutePath(), outputFilePath.getAbsolutePath()), new MavenPluginModule(getLog()));

      final Generator generator = injector.getInstance(Generator.class);

      generator.generateFile();
   }
}

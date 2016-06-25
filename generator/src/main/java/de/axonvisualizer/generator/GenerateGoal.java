package de.axonvisualizer.generator;

import de.axonvisualizer.generator.generator.Generator;
import de.axonvisualizer.generator.json.provider.cytoscape.CytoscapeDataProvider;
import de.axonvisualizer.generator.json.writer.gson.GsonWriter;
import de.axonvisualizer.generator.logging.MavenPluginLogger;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "generate", requiresDependencyCollection = ResolutionScope.COMPILE)
public class GenerateGoal extends AbstractMojo {

   @Parameter(defaultValue = "${project.basedir}", property = "baseDir", required = true)
   private File rootDir;

   @Parameter(defaultValue = "${project.basedir}/out.json", property = "outputFilePath", required = true)
   private File outputFilePath;

   public void execute() throws MojoExecutionException {
      Generator generator = new Generator(new CytoscapeDataProvider(), new GsonWriter(), new MavenPluginLogger(getLog()));
      generator.generateFile(rootDir, outputFilePath);
   }
}

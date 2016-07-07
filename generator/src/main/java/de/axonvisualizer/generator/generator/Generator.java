package de.axonvisualizer.generator.generator;

import de.axonvisualizer.generator.json.provider.DataProvider;
import de.axonvisualizer.generator.logging.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class Generator {

   private Logger logger;
   private JavaFileTraverser javaFileTraverser;
   private EventBus eventBus;
   private DataProvider listener;

   @Inject
   public Generator(final Logger logger, final JavaFileTraverser javaFileTraverser, final EventBus eventBus, final DataProvider listener) {
      this.logger = logger;
      this.javaFileTraverser = javaFileTraverser;
      this.eventBus = eventBus;
      this.listener = listener;
   }

   public void generateFile() {
      eventBus.register(listener);

      javaFileTraverser.traverseFiles();

      logger.info("done");
   }
}

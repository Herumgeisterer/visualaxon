package de.visualaxon.generator.logging;

import org.apache.maven.plugin.logging.Log;

import com.google.inject.Inject;

public class MavenPluginLogger implements Logger {

   private final Log log;

   @Inject
   public MavenPluginLogger(Log log) {
      this.log = log;
   }

   @Override
   public void info(final String message) {
      log.info(message);
   }

   @Override
   public void error(final String message) {
      log.error(message);
   }

   @Override
   public void error(final String message, final Throwable error) {
      log.error(message, error);
   }

   @Override
   public void error(final Throwable error) {
      log.error(error);
   }

   @Override
   public void debug(final String message) {
      log.debug(message);
   }
}

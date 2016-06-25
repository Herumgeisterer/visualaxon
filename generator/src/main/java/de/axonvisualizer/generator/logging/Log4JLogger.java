package de.axonvisualizer.generator.logging;

public class Log4JLogger implements Logger {

   org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Log4JLogger.class);

   @Override
   public void info(final String message) {
      logger.info(message);
   }

   @Override
   public void error(final String message) {
      logger.error(message);
   }

   @Override
   public void error(final String message, final Throwable error) {
      logger.error(message, error);
   }

   @Override
   public void error(final Throwable error) {
      logger.error(error);
   }
}

package de.axonvisualizer.generator.logging;

public interface Logger {

   void info(String message);

   void error(String message);

   void error(String message, Throwable error);

   void error(Throwable error);
}

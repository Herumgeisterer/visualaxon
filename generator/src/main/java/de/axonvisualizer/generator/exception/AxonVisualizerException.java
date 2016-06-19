package de.axonvisualizer.generator.exception;

public class AxonVisualizerException extends RuntimeException {

   public AxonVisualizerException(final String message, final Throwable cause) {
      super(message, cause);
   }

   public AxonVisualizerException(final String message) {
      super(message);
   }
}

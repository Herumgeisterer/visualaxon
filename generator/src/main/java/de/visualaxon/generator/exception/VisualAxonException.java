package de.visualaxon.generator.exception;

public class VisualAxonException extends RuntimeException {

   public VisualAxonException(final String message, final Throwable cause) {
      super(message, cause);
   }

   public VisualAxonException(final String message) {
      super(message);
   }
}

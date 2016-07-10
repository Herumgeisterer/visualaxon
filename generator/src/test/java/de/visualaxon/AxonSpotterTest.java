package de.visualaxon;

import de.visualaxon.config.AbstractTest;
import de.visualaxon.config.TestHandler;
import de.visualaxon.generator.event.AggregateSpotted;
import de.visualaxon.generator.event.CommandHandlerSpotted;
import de.visualaxon.generator.event.EventListenerSpotted;
import de.visualaxon.generator.generator.AxonSpotter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.google.common.eventbus.EventBus;
import com.google.common.io.Resources;
import com.google.inject.Inject;

public class AxonSpotterTest extends AbstractTest {

   @Inject
   private EventBus eventBus;

   @Inject
   private AxonSpotter javaFileParser;

   @Mock
   private TestHandler testHandler;

   @Before
   public void setUp() throws Exception {
      testHandler = Mockito.mock(TestHandler.class);

      eventBus.register(testHandler);
   }

   @Test
   public void shouldSpotAggregate() throws Exception {
      final JavaClassSource javaClassSource = getJavaClassSource("SomeAggregate.java");

      javaFileParser.getAggregate(javaClassSource);

      Mockito.verify(testHandler, Mockito.times(1))
            .handle(AggregateSpotted.builder()
                  .name("SomeAggregate")
                  .build());
   }

   @Test
   public void shouldSpotEntityAsCommandHanldingClass() throws Exception {
      final JavaClassSource javaClassSource = getJavaClassSource("SomeEntity.java");

      javaFileParser.getAggregate(javaClassSource);

      Mockito.verify(testHandler, Mockito.times(1))
            .handle(AggregateSpotted.builder()
                  .name("SomeEntity")
                  .build());
   }

   @Test
   public void shouldFindCommandHandler() throws Exception {
      final JavaClassSource javaClassSource = getJavaClassSource("SomeAggregate.java");

      javaFileParser.getAggregate(javaClassSource);

      Mockito.verify(testHandler, Mockito.times(1))
            .handle(CommandHandlerSpotted.builder()
                  .aggregate("SomeAggregate")
                  .command("SomeCommand")
                  .event("SomeEvent")
                  .build());

      Mockito.verify(testHandler, Mockito.times(1))
            .handle(CommandHandlerSpotted.builder()
                  .aggregate("SomeAggregate")
                  .command("AnotherCommand")
                  .event("AnotherEvent")
                  .build());
   }

   @Test
   public void shouldFindEventHandler() throws Exception {
      final JavaClassSource javaClassSource = getJavaClassSource("SomeAggregate.java");

      javaFileParser.getEventListener(javaClassSource);

      Mockito.verify(testHandler, Mockito.times(1))
            .handle(EventListenerSpotted.builder()
                  .name("SomeAggregate")
                  .build());
   }

   private JavaClassSource getJavaClassSource(final String fileName) throws FileNotFoundException {
      final FileInputStream fileInputStream = new FileInputStream(Resources.getResource(fileName)
            .getFile());

      JavaUnit unit = Roaster.parseUnit(fileInputStream);

      return unit.getGoverningType();
   }

}

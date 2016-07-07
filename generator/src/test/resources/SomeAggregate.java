import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class SomeAggregate extends AbstractAnnotatedAggregateRoot<String> {

   @AggregateIdentifier
   private String someId;

   @CommandHandler
   public SomeAggreagate(final SomeCommand command) {
      apply(SomeEvent.builder()
            .someField(command.someValue())
            .build());
   }

   @CommandHandler
   public void onCommand(final AnotherCommand command) {
      apply(AnotherEvent.builder()
            .build());
   }

   @EventSourcingHandler
   public void onEvent(final SomeEvent event) {
      someId = event.getSomeId();
   }

   @EventSourcingHandler
   public void onEvent(final AnotherEvent event) {
      field = event.getSomeValue();
   }
}

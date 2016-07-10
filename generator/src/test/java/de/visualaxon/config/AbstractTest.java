package de.visualaxon.config;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;

@RunWith(GuiceTestRunner.class)
@GuiceModules(TestModule.class)
@Ignore
public class AbstractTest {

   protected final String LOMBOK_BODY_WITH_SINGLE_APPLIED_EVENT = "validator.throwIfInvalid(command);\n"
         + "      apply(UsernameChanged.builder()\n"
         + "            .build());";
   protected final String LOMBOK_BODY_WITH_MULTIPLE_APPLIED_EVENTS = "partnerCommandValidator.throwIfInvalid(command);\n"
         + "      apply(PartnerCreated.builder()\n"
         + "            .build());\n"
         + "\n"
         + "      apply(PartnerBasicDataCreationStarted.builder()\n"
         + "            .build());";

   protected final String BODY_WITH_SINGLE_APPLIED_EVENT = "String some = \"other code\";"
         + "// some comments"
         + "apply(new UserSignedUpEvent());";

   protected final String BODY_WITH_SINGLE_APPLIED_EVENT_AS_VARIABLE = "String some = \"other code\";"
         + "// some comments \n"
         + "final Some random = new Some();\n"
         + "final SomeEvent event = new SomeEvent();\n"
         + "apply(event);\n";

   protected final String BODY_WITH_MULTIPLE_APPLIED_EVENTS_AS_VARIABLE = "String some = \"other code\";"
         + "// some comments \n"
         + "Some random = new Some();\n"
         + "final Event event = new Event();\n"
         + "final SomeOtherEvent someOtherEvent = new SomeOtherEvent();\n"
         + "apply(someOtherEvent);\n"
         + "apply(event);\n";

   protected final String BODY_WITH_MULTIPLE_APPLIED_EVENTS = "String some = \"other code\";\n"
         + "// some comments\n"
         + "apply(new EventWithParameter(value1, value2));\n"
         + "apply(new OtherEvent());";

   protected final String NOT_APPLIED_CONSTRUCTOR = "String some = \"other code\";\n"
         + "// some comments\n"
         + "SomeObject object = new SomeObject();";

   protected final String NOT_APPLIED_LOMBOK = "String some = \"other code\";\n"
         + "// some comments\n"
         + "SomeObject object = SomeObject.builder().build();";

   protected final String CONSTRUCTOR_EVENT_WITH_METHOD_CALLS = "apply(new SomeEvent(command.getSomeValue()));";

}

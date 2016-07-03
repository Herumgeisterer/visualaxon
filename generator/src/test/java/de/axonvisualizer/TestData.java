package de.axonvisualizer;

public abstract class TestData {

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
}

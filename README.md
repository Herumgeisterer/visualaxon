# VisualAxon

This tool allows you to visualize an axonframework based application.
It shows you for every given commandhandler (methods annotated with @CommandHandler) which events it applies and where these events are handled (methods annotated with @SagaEventHandler, @EventHandler, @EventSourcingHandler). The result is a graph which shows commandhandler and eventhandler as nodes, grouped by aggregates and eventlistener (eventhandling classes).
More info on the axonframework is found on [axonframework.org](http://axonframework.org/)

## Usage

First you need to execute the tool to generate a json file containing all relevant data. To do this, you have two options:

### Build it yourself

* Clone this repository `git clone git@github.com:Herumgeisterer/visualaxon.git`
* `cd visualaxon`
* run `mvn clean install`
* Include the maven plugin in your projects root `pom.xml`
```xml
<plugin>
   <groupId>de.visualaxon</groupId>
   <artifactId>visualaxon-maven-plugin</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <executions>
      <execution>
         <phase>compile</phase>
         <goals>
            <goal>generate</goal>
         </goals>
         <configuration>
            <!-- optional configuration -->
         </configuration>
      </execution>
   </executions>
</plugin>
```

* run `mvn de.visualaxon:visualaxon-maven-plugin:generate`

###### Options

```xml
<configuration>
  <inputRoot>/some/path/myproject</inputRoot> <!-- default: ${project.basedir} -->
  <outputFilePath>/another/path/output.json</outputFilePath> <!-- default: ${project.basedir}/output.json -->
</configuration>
```

### Use as executable jar

Download the the latest jar release and run

`java -jar visualaxon-<version>.jar <inputroot> <outputfilename>`

e.g.

`java -jar visualaxon-0.1.0.jar /Development/myproject /tmp/output.json`

After you generated the json file, go to the [website](https://herumgeisterer.github.io/visualaxon/) and load the json file to see the graph.


## Example

![example image][example]

## Some facts
* Builder-Pattern-Support (e.g. lombok)
* Based and tested on axonframework version 2.4.4
* (PDF and image export (coming soon))
* Support for eventhandler and sagaeventhandler

## Limits

* It doesn't show where a command is coming from (but planned for a future release)
* For very large projects the graph can be very cluttered
* Your code needs to be properly formatted. Most source file parsing is done by the presence of annoations and class names, but some parts are done by manually parsing the java source code

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).


[example]: https://github.com/herumgeisterer/visualaxon/raw/master/raw/example.png "Example image"

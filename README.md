# Axonvisualizer

This tool allows you to visualize an axonframework based application.
It shows you for every given commandhandler which events it applies to the eventbus and where these events are handled.
More info on the axonframework is found on [axonframework.org](http://axonframework.org/)

## Some facts
* Builder-Pattern-Support (e.g. lombok)
* Based and tested on axonframework version 2.4.4
* (PDF and image export (coming soon))
* Support for eventhandler and sagaeventhandler

## Usage

You have two options.

### Build it yourself

* Clone this repository
* go into the generate directory `cd axonvisualizer/generator/`
* run `mvn clean install`
* Include the maven plugin in your projects root `pom.xml`
```xml
<plugin>
  <groupId>de.axonvisualizer</groupId>
  <artifactId>axonvisualizer-maven-plugin</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <executions>
    <execution>
      <phase>compile</phase>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <outputFilePath>${project.basedir}/output.json</outputFilePath>
      </configuration>
    </execution>
  </executions>
</plugin>
```

* run `mvn de.axonvisualizer:axonvisualizer-maven-plugin:generate`

### Use as executable jar

Download the the latest jar release and run

`java -jar axonvisualizer-<version>.jar <inputroot> <outputfilename>`

e.g.

`java -jar axonvisualizer-0.0.1.jar /Development/myproject /tmp/output.json`

After you generated the .json file, go to the [website](https://herumgeisterer.github.io/axonvisualizer/) and load the .json file to see the graph.


## Example

![example image][example]


## Limits

* It doesn't show where a command is comming from
* Your code needs to be properly formatted. Most source file parsing is done by the presence of annoations and class names, but some parts are done by manually parsing the java source code

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).


[example]: https://github.com/herumgeisterer/axonvisualizer/raw/master/raw/example.png "Example image"


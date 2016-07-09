# Axonvisualizer

This tool allows you to visualize an axonframework based.
It shows you for every given commandhandler which events it produces and where these are handled.
More info on the axonframework is found on [axonframework.org](http://axonframework.org/)

## Usage

You have two options.

### Build it yourself

* Clone this repository
* go into `cd axonvisualizer/generator/``
* run `mvn clean install`
* Include plugin in your projects root `pom.xml`
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

* run `mvn axonvisualizer:generate`

### Use as executable jar

Download the the latest jar release and run

`java -jar axonvisualizer.java <inputroot> <outputfilename>`

e.g.

`java -jar axonvisualizer.java /Development/myproject /tmp/output.json`

After you generated the .json file, go to the [webapp](http://github.com/) and load the .json file to see the graph.

## Features

* Builder-Pattern-Support (e.g. lombok)
* Based and tested on axonframework 2.4.0
* PDF and image export (coming soon)

## Example

![example image][example]


## Limits

* When a command is created and fired in an saga eventhandler
the relationship between eventhandler and commandhandler is not shown.
* Your code needs to be properly formatted. Most parsing is done by the presence of annoations and class names, but some parts are done by manually parsing the java source code


[example]: https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Example image"

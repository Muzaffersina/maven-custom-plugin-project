# MyCustomPlugin

### -- It is used to print the program information given below to the file. (Program Summary)
- project details (group id, build id, version) 
- project developers 
- project dependencies
- project plugins 
- release date

### -- How it works ?
- You need to build the downloaded file with the "install" command.

- Then write the given plugin in the pom.xml file 

```
   <plugin>
		<groupId>com.msa</groupId>
		<artifactId>customPlugin</artifactId>
		<version>1.0</version>
			<executions>
				<execution>
					<id>msa</id>
					<phase>install</phase>
					<goals>
						<goal>summarize</goal>
					</goals>
				</execution>
			</executions>
    </plugin>
```
#
<img src =https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/maven/maven.png  height="44" width="44" >

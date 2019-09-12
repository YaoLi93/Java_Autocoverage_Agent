# cs6367

#This is a tool to test class based on the statement coverage.

1. First use MVN -build to build the project to an agent.jar
2. Select a target project and put the Agent.jar file under the path.
3. To collect the coverage, add the below information in the pom.xml
in the target folder
   Add a dependency for ASM
   org.ow2.asm asm 5.0.3
   Add java agent details in the surefire plugin in -javaagent
4. Run mvn test use maven command line at the path of test folder.
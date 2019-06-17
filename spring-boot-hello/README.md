# spring-boot-hello

## TODO
1. [Using springboot-cli to create the project](##init-project)
2. [Creating a simple restful web app](##creating-app)
3. [Running the app using command-line](##running-app)
4. [Creating an executable jar](##packaging-app)


## Init project
Installation with SDKMAN
```bash
# Install skdman
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"

$ sdk install springboot
$ spring --version
Spring Boot v2.1.1.RELEASE

# Command-line Completion
$ . ~/.sdkman/candidates/springboot/current/shell-completion/bash/spring
$ spring <HIT TAB HERE>
  grab  help  jar  run  test  version 
```

OSX Homebrew Installation
> Homebrew installs spring to /usr/local/bin
```bash
$ brew tap pivotal/tap
$ brew install springboot
```

MacPorts Installation
```bash
sudo port install spring-boot-cli
```

Windows Scoop Installation
> Scoop installs spring to ~/scoop/apps/springboot/current/bin
```powershell
> scoop bucket add extras
> scoop install springboot
```

## Creating app
```bash
$ spring init --dependencies=web \
--build=maven --packaging=jar --java-version=1.8 \
-g spring-boot-hello -a spring-boot-hello -v 0.0.1-SNAPSHOT \
--name=spring-boot-hello \
spring-boot-hello
```

## Running app
Using mvn/mvnw
```bash
# mvn (mvn has already been installed)
## default port 8080
$ mvn spring-boot:run

# mvnw
## spring 1.x
$ ./mvnw spring-boot:run -Drun.arguments=--server.port=8085
## spring 2.x
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8085
```
If you use `curl localhost:${port:8080/8085}` to access the app, you should see the following output:
```
hello, world
```

## Packaging app
```bash
# mvn (mvn has already been installed)
$ mvn package

# mvnw
$ ./mvnw package
```

There will be two package, one `spring-boot-hello-0.0.1-SNAPSHOT.jar.original` which doesn't include the lib jars like `spring-core`. The spring boot maven plugin takes it(jar or war) and repackages it and lib jars to make them executable from the command line.

From the plugin docs:
> "Repackages existing JAR and WAR archives so that they can be executed from the command line using java -jar."

You can peek inside the jar using command below:
```bash
$ jar tvf target/spring-boot-hello-0.0.1-SNAPSHOT.jar.jar
```

And also you can run that application, use the `java -jar` command, as follows:
```bash
$ java -jar target/spring-boot-hello-0.0.1-SNAPSHOT.jar
```


FROM openjdk:17-ea-jdk-buster as jdkbase-build 
FROM debian:11-slim

RUN mkdir -p "/usr/share/dotnet"
COPY --from=mcr.microsoft.com/dotnet/sdk:6.0 /usr/share/dotnet /usr/share/dotnet

RUN apt-get update && apt-get install -y --no-install-recommends maven unzip

RUN mkdir -p "/usr/local/openjdk-17"
COPY --from=jdkbase-build /usr/local/openjdk-17 /usr/local/openjdk-17

ENV DOTNET_HOME=/usr/share/dotnet
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$JAVA_HOME/bin:$DOTNET_HOME:$PATH
ENV SOLUTION_PATH=/usr/dt_maze_challenge/test
ENV LANGUAGE=java

RUN echo Verifying copy in smbase-prod ... \
    && javac --version \
    && echo copy Java Complete.

WORKDIR $SOLUTION_PATH

RUN echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n \
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n \
    xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n \
    <modelVersion>4.0.0</modelVersion>\n \
\n \
    <groupId>org.springframework</groupId>\n \
    <artifactId>Solution</artifactId>\n \
    <packaging>jar</packaging>\n \
    <version>1.0.0</version>\n \
\n \
    <properties>\n \
        <maven.compiler.source>1.8</maven.compiler.source>\n \
        <maven.compiler.target>1.8</maven.compiler.target>\n \
    </properties>\n \
\n \
    <build>\n \
        <plugins>\n \
        </plugins>\n \
    </build>\n \
</project>\n " > $SOLUTION_PATH/pom.xml

RUN mkdir -p $SOLUTION_PATH/src/main/java/dt_maze_challenge/

RUN mvn package
RUN rm -rf src
RUN rm -rf target

COPY ./src.zip $SOLUTION_PATH/src.zip
RUN unzip $SOLUTION_PATH/src.zip

COPY ./test.zip $SOLUTION_PATH/test.zip
RUN unzip $SOLUTION_PATH/test.zip

RUN mvn -o package

WORKDIR $SOLUTION_PATH/test

ENTRYPOINT ["/usr/dt_maze_challenge/test/test/run_example_maze_solution.sh"]
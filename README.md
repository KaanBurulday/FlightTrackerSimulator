<h1>Hello All,</h1>

Before we start, to make a project about kafka, you need to start your kafka server.

The proper guide: https://kafka.apache.org/documentation/

Kafka server works with java and scala, so extract the zip file and open it as a project with intellij.
After building Gradle, you can start your server with these Bash commands:
### Build a jar and run it ###
    ./gradlew jar

### Build source jar ###
    ./gradlew srcJar

### Running a Kafka broker in KRaft mode
    KAFKA_CLUSTER_ID="$(./bin/kafka-storage.sh random-uuid)"
    ./bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
    ./bin/kafka-server-start.sh config/kraft/server.properties

After these steps, your kafka server should be running at 127.0.0.1:9092

Now, for the flight tracker simulator project;
1. Start your spring application
2. Enter the Map.html via localhost:8080/Render
3. Generate and Start environment via requesting to the endpoints:
   1. localhost:8080/GenerateEnv -> Generates airports and fills them with planes
   2. localhost:8080/EnvStart -> Every plane in airport will take off, after that a continuous loop will begin as every plane that are on flight will move and every plane that are in airport will take off.
4. Each event of the planes will publish flight information
5. You can stop the iteration via localhost:8080/EnvStop

### To Consume All Messages (messages will be printed to console)
    localhost:8080/Consumer/Messages

<h2>The second branch includes button support for the generate, start and stop the environment</h2>
FROM azul/zulu-openjdk-alpine:11

WORKDIR /home/application

COPY target/distributed-chat-0.1.jar /home/application/distributed-chat.jar

ENTRYPOINT ["java", "-Xmx400m", "-jar", "./distributed-chat.jar"]
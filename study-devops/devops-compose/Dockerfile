FROM eclipse-temurin:8

MAINTAINER jack

RUN mkdir -p /home/work/devops-compose/
RUN mkdir -p /home/work/log/devops-compose/

COPY target/devops-compose.jar /home/work/devops-compose/

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Xms512M","-Xmx512M","-XX:+UseG1GC","-XX:+PrintReferenceGC","-XX:+PrintGCDetails","-XX:+PrintGCDateStamps","-XX:+PrintHeapAtGC","-Xloggc:/home/work/log/devops-compose/gc.log","/home/work/devops-compose/devops-compose.jar"]

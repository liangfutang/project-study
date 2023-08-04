FROM openjdk:8-jdk-alpine

RUN mkdir -p /home/work/boot-jna-cpp/
RUN mkdir -p /home/work/log/boot-jna-cpp/

COPY target/boot-jna-cpp.jar /home/work/boot-jna-cpp/

COPY native/libgetgsv.so /usr/lib/

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Xms512M","-Xmx512M","-XX:+UseG1GC","-XX:+PrintReferenceGC","-XX:+PrintGCDetails","-XX:+PrintGCDateStamps","-XX:+PrintHeapAtGC","-Xloggc:/home/work/log/boot-jna-cpp/gc.log","/home/work/boot-jna-cpp/boot-jna-cpp.jar"]

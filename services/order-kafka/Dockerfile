FROM tomee:8-jre-7.0.3-plus
COPY target/order-kafka-1.jar .
CMD /usr/bin/java -Xmx400m -Xms400m -jar order-kafka-1.jar
EXPOSE 8080

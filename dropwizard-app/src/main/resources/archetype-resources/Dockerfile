FROM anapsix/alpine-java:9

COPY target/${artifactId}-${version}.jar /app/app.jar

CMD ["java","-jar","/app/app.jar","server"]

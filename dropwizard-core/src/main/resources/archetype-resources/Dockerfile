FROM anapsix/alpine-java:9

COPY target/${artifactId}-${version}.jar /app/app.jar

CMD ["java","--add-modules","java.xml.bind","-jar","/app/app.jar","server"]

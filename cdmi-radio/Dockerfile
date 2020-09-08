FROM java:8-jre-alpine

WORKDIR /home/apps/
ADD target/radio-1.0.0.jar .
ADD target/lib ./lib
ADD start.sh .

ENTRYPOINT ["sh", "/home/apps/start.sh"]
FROM clojure:temurin-18-tools-deps-alpine

RUN apk update && apk add py3-pip ffmpeg
RUN pip3 install youtube-dl --upgrade
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
EXPOSE 3001
COPY . /usr/src/app
RUN mv "$(clojure -T:build uber | sed -n 's/^Created \(.*telegram-chat-bot-.*\.jar\)/\1/p')" app-standalone.jar
CMD ["java", "-jar", "app-standalone.jar"]

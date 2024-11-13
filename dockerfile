FROM alpine/java:22.0.2-jdk

EXPOSE 80 443
LABEL author="Ivan Rudakov"
LABEL pray="Sasha ne kikay s internaturi"
ENV PORT0=8080
RUN apk update && apk add nginx
#COPY /ssl/ /etc/ssl/
COPY conf/ etc/nginx/http.d/
ADD simpleweb-java/ /app/java-web/
ADD exec.sh /app/java-web/exec.sh
WORKDIR /app/java-web/
VOLUME /var/log/nginx
USER root
STOPSIGNAL SIGQUIT
SHELL ["/bin/sh", "-c"]
ONBUILD RUN apk add git
HEALTHCHECK CMD ping -c 10 google.com || exit 1
ARG VERSION=1.0
ENTRYPOINT ["/bin/sh","/app/java-web/exec.sh"]


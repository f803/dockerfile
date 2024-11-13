# Перед тем как залить на гит все проверил 
- билдится
- работает

Все делал сам.  
Откуда взял приложение: [линк](https://github.com/github.com/fersantxez/simpleweb-java)  
Там в usage есть *docker image is available*, но я туда не переходил. Даже сейчас, пока пишу этот readme.md

# Dockerfile
```
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
```
# exec.sh  
```
#!/bin/sh  

nginx &  
java -jar /app/java-web/simpleweb.jar
```

# nginx
## Сначала еще прописал серты для практики, но раз гитхаб то sert.key sert.crt заливать сюда несекьюрно (в докерфайле тоже закоментил COPY /ssl/ /etc/ssl/). Но все работало.
## [Первоначальный конфиг закоментил тут же](https://github.com/f803/dockerfile/blob/main/conf/default.conf)
```
server {  
        listen 80 default_server;  
        listen [::]:80 default_server;  
        server_name localhost;  
        location / {  
	   proxy_set_header Host $host;  
	   proxy_set_header X-Real-IP $remote_addr;  
	   proxy_pass http://127.0.0.1:8080;  
        }  
}  
  ```

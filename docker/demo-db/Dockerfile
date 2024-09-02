FROM postgres:16

ADD demo.sql /docker-entrypoint-initdb.d

RUN chmod +r /docker-entrypoint-initdb.d/demo.sql

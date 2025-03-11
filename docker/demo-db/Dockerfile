FROM postgres:17

ADD demo.sql /docker-entrypoint-initdb.d

RUN chmod +r /docker-entrypoint-initdb.d/demo.sql

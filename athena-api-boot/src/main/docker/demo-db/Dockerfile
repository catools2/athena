FROM postgres

ADD ./dumpfile.sql /docker-entrypoint-initdb.d

RUN chmod +r /docker-entrypoint-initdb.d/dumpfile.sql
import logging

logging.root.setLevel(logging.INFO)


def get_logger(logger_name):
    return logging.getLogger(logger_name)


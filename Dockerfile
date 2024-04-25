FROM maven:3.9-eclipse-temurin-11

RUN mkdir -p /home/ubuntu/mock_tests

WORKDIR /home/ubuntu/mock_tests

COPY . /home/ubuntu/mock_tests

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]
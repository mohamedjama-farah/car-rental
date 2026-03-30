# Car Rental Management System

[![Build Status](https://github.com/mohamedjama-farah/car-rental/actions/workflows/maven.yml/badge.svg)](https://github.com/mohamedjama-farah/car-rental/actions)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=mohamedjama-farah_car-rental&metric=alert_status)](https://sonarcloud.io/project/overview?id=mohamedjama-farah_car-rental)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mohamedjama-farah_car-rental&metric=coverage)](https://sonarcloud.io/project/overview?id=mohamedjama-farah_car-rental)

A Java desktop application for managing a car rental fleet.

## How to build and run

Open Docker Desktop first, then run:

```
mvn verify
```

This single command compiles, runs all tests, starts and stops MongoDB automatically, and generates coverage reports.

## Technologies

- Java 17, Apache Maven, JUnit 5, AssertJ, Mockito
- MongoDB with fabric8 docker-maven-plugin (automatic Docker lifecycle)
- Java Swing desktop UI with AssertJ Swing end-to-end tests
- Cucumber BDD, Google Guice dependency injection, Log4j 2
- JaCoCo code coverage, PIT mutation testing, SonarCloud
- GitHub Actions CI/CD, Docker, DockerHub

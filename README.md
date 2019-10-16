# GoHenry Coding Challenge

Simple Spring boot 2 service for user CRUD service

## Project building pipeline

This project contains docker file for deployment 

## Build project from source

Run following commands under project directory

- Build Maven project
```
mvn clean package
```
- Build Docker image
```
docker build -t gohenry-coding-challenge:latest .
```

## Run project

- Copy scripts from [scripts](./scripts) directory
- Make the script executable
- Run in dev mode needs to turn on spring boot active profile to "local"
```
chmod 700 ./dockerrun
```
- Run script
```
./dockerrun
```

## Run project
Access service Document API
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


### Further information
| "Stable" Release Version | JDK Version compatibility | Release Date |
| ------------------------ | ------------------------- | ------------ |
| 1.0-SNAPSHOT             | 11.0+                     | \*15/10/2019 |

## Release notes

| Release       |                        Notes                                       |
| ------------- | :----------------------------------------------------------------: |
| 1.0-SNAPSHOT  | [v1.0-snapshot release notes](docs/release_v1.0-snapshot_notes.md) |

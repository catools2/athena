![Athena](docs/img.png)

# Install Charts

## Tools

To install chart you need

* Docker Desktop WIth k8s enabled
* Helm 

## Installation Sequence

Start local registry

```shell 
docker run -d -p 5000:5000 --restart=always --name local-registry registry:2
```

Build docker images and push them to local registry. 
> you need to push images everytime you start local registry container. Z

```shell 
./mvnw clean package docker:build -DskipTests
```

Install helm charts

```shell 
cd orchestration/athena
./install.sh
```

![Athena](docs/img.png)

# Install Charts

## Tools

To install chart you need

* Docker
* Minikube
* Helm 

## Installation Sequence

Start minikube and enable local registry addons. Answer No to all questions. [Read more here](https://minikube.sigs.k8s.io/docs/handbook/registry/)

```shell 
minikube start --insecure-registry localhost:5000 --memory 16192 --cpus 6
minikube addons configure registry-creds
minikube addons enable registry
```

Start local registry

```shell 
docker run --rm -it --name registry --network=host alpine ash -c "apk add socat && socat TCP-LISTEN:5000,reuseaddr,fork TCP:$(minikube ip):5000"
```

Build docker images and push them to local registry. 
> you need to push images everytime you start local registry container. 

```shell 
./mvnw package docker:build -DskipTests

docker tag akeshmiri/athena-boot-core:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-core:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-git:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-git:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-kube:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-kube:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-metric:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-metric:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-pipeline:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-pipeline:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-spec:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-spec:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-boot-tms:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-boot-tms:0.0.1-SNAPSHOT
docker tag akeshmiri/athena-gateway:0.0.1-SNAPSHOT localhost:5000/akeshmiri/athena-gateway:0.0.1-SNAPSHOT

docker push localhost:5000/akeshmiri/athena-boot-core:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-git:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-kube:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-metric:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-pipeline:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-spec:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-boot-tms:0.0.1-SNAPSHOT
docker push localhost:5000/akeshmiri/athena-gateway:0.0.1-SNAPSHOT
```

Install helm charts

```shell 
cd orchestration/helm
helm install athena .
```

Start minikube tunnel for ingress

```shell 
minikube tunnel
```


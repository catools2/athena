# Core Automation Toolset 2

- Tools:

    - Java 17
    - Maven 3.8 or later

- Add Lombok and Enable Annotation Processing for your idea
- put following line in your testNG Run/Debug configuration for VM parameter (we need this for JMockit)

```
    -ea -javaagent:/root/.m2/repository/org/jmockit/jmockit/1.44/jmockit-1.44.jar
```

#GPG
Follow [gpg_signed_commits](https://docs.gitlab.com/ee/user/project/repository/gpg_signed_commits/)


##Send Key To Server
```
gpg2 --send-keys --keyserver hkps://keyserver.ubuntu.com XXXXXX
```

##Receive Key To Server
```
gpg2 --keyserver hkps://keyserver.ubuntu.com --receive-key XXXXXX
```

##Export Keys
```
gpg --output pubkey.gpg --armor --export XXXXXX
gpg --output seckey.gpg --armor --export-secret-key XXXXXX
```

##Import Keys
```
gpg --import pub
gpg --allow-secret-key-import --import key

```

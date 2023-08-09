# CATools

With many breaking changes from v1 to adopt source code with new toolsets and improve test maintenance and reliability.

The majority of the challenges I faced are very specific to the projects I was/am worked with, so I am not sure if you need to use the whole code as is.
Feel free to use libs if that works for you or copy/paste any part of the code you want.

If you have any questions or recommendation I will be more than glad to know about them.
I love new challenges and in this journey, there is no judgment, bad or wrong knowledge so do not hesitate to share your thoughts by skype (akeshmiri) or email (akeshmiri110@gmail.com).

- Tools:

    - Java 17
    - Maven 3.6.3 or later

- Add Lombok and Enable Annotation Processing for your Idea
- put following line in your testNG Run/Debug configuration for VM parameter (we need this for JMockit)

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

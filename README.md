[![Build Status](https://travis-ci.org/mrexmelle/line-example-weblogin-java-v2.1.svg?branch=master)](https://travis-ci.org/mrexmelle/line-example-weblogin-java-v2.1)
[![CodeFactor](https://www.codefactor.io/repository/github/mrexmelle/line-example-weblogin-java-v2.1/badge)](https://www.codefactor.io/repository/github/mrexmelle/line-example-weblogin-java-v2.1)

# line-example-weblogin-java-v2.1 #

This repository demonstrates how to use LINE Web Login using Spring Framework.

### How do I get set up? ###

* Compile
> gradle clean build

* Run Server
> gradle bootRun

* Use  
> By default the server will serve at http://localhost:8080/welcome

### How to use docker? ###

* Repository
https://hub.docker.com/r/mrexmelle/line-weblogin-java/

* Run Server
> docker run --rm -p 8080:8080 -e 'CHANNEL_ID=1479418979' -e 'CHANNEL_SECRET=a17951d01dd8719452544a5b57a22b85' -e 'REDIRECT_URI=http://localhost:8080/line/auth' mrexmelle/line-weblogin-java

### How do I contribute? ###

* Add your name and e-mail address into CONTRIBUTORS.txt

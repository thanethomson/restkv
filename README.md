# restkv

## Overview
`restkv` is a simple RESTful key/value store. It's based on [Spring
Boot](http://projects.spring.io/spring-boot/), and can be configured with the
following backends at present:

* In-memory (using a simple HashMap implementation)
* [Redis](https://redis.io/)

## Requirements
To build and run `restkv`, you'll need the following software
installed on your machine:

* Java 8 (JDK)
* Redis (if you want to use Redis)

## Building
To build the application, simply do the following:

```bash
> ./gradlew build -Dspring.profiles.active=inmemory
```

The `-Dspring.profiles.active=inmemory` parameter tells the tests
to run in in-memory mode (without Redis).

You'll find the built JAR file here: `build/libs/restkv.jar`

## Running
To run the application in in-memory mode:

```bash
> ./gradlew bootRun -Dspring.profiles.active=inmemory
```

To run the application connecting to a Redis instance at host `localhost`,
port 6379, database 0:

```bash
> ./gradlew bootRun -Dspring.profiles.active=redis -Drestkv.redis.url=redis://localhost:6379/0
```

## License
The MIT License (MIT)

Copyright 2018 Thane Thomson

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

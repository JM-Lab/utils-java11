JMLab Java 11 Utility Libraries
==============================
## version
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/kr.jmlab/jmlab-utils-java11/badge.svg)](http://search.maven.org/#artifactdetails%7Ckr.jmlab%7Cjmlab-utils-java11%7C0.1.4.1%7Cjar)

## Prerequisites:
* Java 11 or later

## Usage
Gradle:
```groovy
compile 'kr.jmlab:jmlab-utils-java11:0.1.4.1'
```
Maven:
```xml
<dependency>
    <groupId>kr.jmlab</groupId>
    <artifactId>jmlab-utils-java11</artifactId>
    <version>0.1.4.1</version>
</dependency>
```

## Installation
Checkout the source code:

    git clone https://github.com/JM-Lab/utils-java11.git
    cd utils-java11
    git checkout -b 0.1.4.1 origin/0.1.4.1
    mvn install

## Useful Utilities With Features Of Java 11  :
* **Flow Package ([Reactive Programming with JDK 9 Flow API](https://community.oracle.com/docs/DOC-1006738) Utility)**

`JMSubmissionPublisher` `BulkSubmissionPublisher` 
`ResourceSubmissionPublisher` `StdInLinePublisher`

`JMTransformProcessor` `JMTransformProcessorBuilder`

`JMSubscriber` `JMSubscriberBuilder`
* **JMJson**
* **RestfulResourceUpdater**
* **etc.**

### For Example :
* **[TestCase](https://github.com/JM-Lab/utils-java11/tree/master/src/test/java/kr/jm/utils)**

## LICENSE
Copyright 2020 Jemin Huh (JM)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
# Jenkins Shared Libraries:
## What is a Shared Library
A shared library is a collection of independent Groovy scripts which you pull into your Jenkinsfile at runtime from a SCM.

## Why is it used:
To Make the pipeline code reusable for different projects and thus save time and reduce maintenance overhead.

## Composition:
- ### Steps:
  - Called Global Variables in Jenkins terminology, and these are the steps that you want to be available to Jenkins pipelines 
  - Should be under vars/YourStepName.groovy
  - Should define a call function 
```groovy
#!/usr/bin/env groovy
// vars/YourStepName.groovy

def call() {
    // Do something here...
}
```
- ### Other common code:
  - This might include helper classes, or common code that needs to be included inside pipeline steps themselves or a place to store static constants
  - src/your/package/name directory
```groovy
#!/usr/bin/env groovy

// under src/xyz/tmod/GlobalVars.groovy
package xyz.tomd

class GlobalVars {
    static String foo = "bar"
}
```

## Demo:
- Creating a SCM Repo for the library having vars and src folders
- Create the steps.groovy under vars
- Create otherCode.groovy files under src
- Set up the library in Jenkins
- Use the library in a pipeline
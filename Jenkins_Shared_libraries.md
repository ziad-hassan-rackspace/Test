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
- ### Creating a SCM Repo for the library named test having vars and src folders
- ### Create the vars/actionStep.groovy
  ```groovy
  def call( action ) {
      pipeline {
          agent any
          stages {
              stage ('Run only if approval exists') {
                  when {
                      expression { action }
                  }
                  steps {
                      echo "Performing steps as the build has been approved!!!"
                  }
              }
          }
      }
  }
  ```
- ### Create src/com/mcnz/uatInput.groovy
  ```groovy
  package com.mcnz
    public class uatInput {
      def buildIsUatApproved() {
      def file = new File("/tmp/approved.txt")
      if (file.exists()){
          println "Approval file exists."
        return true;
      }
      else {
        println "Approval file does not exist."
      } 
      return false; 
    }
  }
  ```
- ### Install and configure jenkins:
  - **Create an EC2 instance with the below userdata**
  ```bash
  wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
  sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
  sudo apt update
  sudo apt install -y openjdk-11-jdk
  sudo apt -y install jenkins
  sudo systemctl start jenkins
  sudo systemctl status jenkins
  sudo systemctl enable jenkins
  ```
  - **Open Jenkins url and obtain initial password by ssh to ec2 and** ```sudo cat /var/lib/jenkins/secrets/initialAdminPassword```
  - **Set up the library in Jenkins:**
  ```
  # Manage Jenkins > Global Pipeline Libraries
  Name: shared-library
  Default version (branch): master
  GitHub URL: https://github.com/ziad-hassan-rackspace/Test.git
  ```
  - **Use the library in a pipeline:**
  ```groovy
  @Library('shared-library')
  import com.mcnz.uatInput
  def uatInput = new uatInput()
  def action = uatInput.buildIsUatApproved() //will resolve to true or false
  actionStep(action)
  ```
  - Create "/tmp/approved.txt" file and run the job
  - Delete "/tmp/approved.txt" file and run the job
package com.mcnz

  public class uatInput {
    def buildIsUatApproved() {
    def file = new File("./approved.txt")
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

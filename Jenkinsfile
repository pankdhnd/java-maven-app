@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    //Maven is installed as a plugin and hence will not be available scripted pipeline, hence we have to add tools block to make it available
    tools {
        maven 'maven-3.8.1'

    }
    stages {
    
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"                    
                }
            }
        }

        stage("increment version") {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }

        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }

        stage("build image") {
            /*when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }*/
            steps {
              script {                  
                  buildImage()               
              }
            }
        }

           stage("deploy") {
                /*when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }*/
                 steps {
              script {                  
                  gv.deployApp()
               
              }
            }
        }
    
     //Commit the build version to git so that it could be used as reference for next auto increment
        stage("commit build version to git") {
            /*when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }*/
            steps {
              script {                  
                  gv.commitVersion()               
              }
            }
        }
    }    
}
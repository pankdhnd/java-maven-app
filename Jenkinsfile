def gv
pipeline {
    agent any
    //Maven is installed as a plugin and hence will not be available scripted pipeline, hence we have to add tools block to make it available
    tools {
        maven 'maven-3.8.1'

    }
    stages {
    //Initialize and load groovy script
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"                    
                }
            }
        }
    
    //Auto increment the build version
        stage("increment version") {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }

    //Build jar file
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

    //Build image
        stage("build image") {
            when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }
            steps {
              script {                  
                  gv.buildImage()
               
              }
            }
        }
    
    //Deploy the build/image
        stage("deploy") {
            when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }
            steps {
              script {                  
                  gv.deployApp()               
              }
            }
        }

        //Commit the build version to git so that it could be used as reference for next auto increment
        stage("commit build version to git") {
            when {
                expression {
                    env.BRANCH_NAME == 'master'
                }
            }
            steps {
              script {                  
                  gv.commitVersion()               
              }
            }
        }
    }
}
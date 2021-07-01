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

        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("build image") {
                 steps {
              script {                  
                  gv.buildImage()
               
              }
            }
        }

           stage("deploy") {
                 steps {
              script {                  
                  gv.deployApp()
               
              }
            }
        }
    }
}

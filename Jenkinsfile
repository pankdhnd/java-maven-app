pipeline {
    agent any
    //Maven is installed as a plugin and hence will not be available scripted pipeline, hence we have to add tools block to make it available
    tools {
        maven 'maven-3.8.1'

    }
    stages {
    
        stage("build jar") {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }

        stage("deploy") {
            input {
            message "Kindly input the image version number"
            ok "Accept"
            parameters {
                  string (name: 'VERSION', defaultValue: '', description: '')
            }
          }

            steps {
              sh 'docker build -t pankajdh/testrepo:java-maven-app-${VERSION} .'

              script {
                  withCredentials([
                    usernamePassword(credentialsId: '3703ba37-a8bc-4d6e-8ab4-4bbbf4df5e8e', usernameVariable: 'USER', passwordVariable: 'PASS')
                  ])

                {
                 sh 'echo $PASS | docker login -u $USER --password-stdin'
                 sh 'docker push pankajdh/testrepo:java-maven-app-${VERSION}'
                }
               
              }
            }
        }
    }
    /* 
    post{
        always{
            echo "send email on pipeline completion"
        }
        failure{
            echo "send email on pipeline failure"
        }
        success{
            echo "send email on pipeline success"
        }
        changed{
            echo "it seems build stauts was changed from the last time"
        }
    }*/
}
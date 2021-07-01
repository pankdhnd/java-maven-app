def gv
pipeline {
  agent any
  //parameterize the pipeline. This pipeline will ask for selection of parameters during exeuction, hence requires manual intervention
  parameters {
    //string(name: 'VERSION', defaultValue: '', description: 'version to deploy')
    choice (name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: 'select build version to deploy')
    booleanParam (name: 'executeTests', defaultValue: true, description: 'decided wheter to execute tests')
  }  

  //declare custom environment variable
  environment{
    NEW_ENV_VAR = "JENKINS_TEST"
    SERVER_CREDENTIALS = credentials('server-cred')
  }
    stages{
      stage("init"){
        steps{
          script{
            gv = load "script.groovy"
          }

        }

      }

      stage("build"){
        steps{          
          echo "Environment varialbe value: ${NEW_ENV_VAR}"
          echo "${SERVER_CREDENTIALS}"

          script {
            gv.buildApp()
          }
        }
      }
      stage("test"){
          when{
              expression {
                  //env.BRANCH_NAME == 'dev' || env.BRANCH_NAME =='test' || env.BRANCH_NAME == 'jenkins-jobs'
                  params.executeTests
              }
          }
        steps{
          script {
            gv.testApp()
          }          
        }
      }


      stage("deploy"){
        steps{
          
          script {
            gv.deployApp()
          }

          //fetch credentails using withCredentials (requires Credentials Plugin)
          withCredentials([
              usernamePassword(credentialsId: 'server-cred', usernameVariable: 'USER', passwordVariable: 'PASS')
          ])
            {
                echo "username= " + "$USER"
                echo "passwrod= " + "$PASS"
            }
          //Accpting version a parameter
         
        
        }
      }
      stage("cleanup"){
        steps{
          echo "printing from cleanup step"
        }
      }
   }
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
   }
} 

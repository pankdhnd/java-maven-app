pipeline {
  agent any
  //declare custom environment variable
  environment{
    NEW_ENV_VAR = "JENKINS_TEST"
    SERVER_CREDENTIALS = credentials('server-cred')
  }
    stages{
      stage("build"){
        steps{
          echo "printing from build step"
          echo "Environment varialbe value: ${NEW_ENV_VAR}"
          echo "${SERVER_CREDENTIALS}"
        }
      }
      stage("test"){
          when{
              expression {
                  env.BRANCH_NAME == 'dev' || env.BRANCH_NAME =='test' || env.BRANCH_NAME == 'jenkins-jobs'
              }
          }
        steps{
          echo "printing from test step"
        }
      }
      stage("deploy"){
        steps{
          echo "printing from deploy step"

          //fetch credentails using withCredentials (requires Credentials Plugin)
          withCredentials([
              usernamePassword(credentials: 'server-cred',usernameVariable: 'USER', passwordVariable: 'PASS')
          ])
            {
                echo "username= ${USER}, password= ${PASS}"
            }

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

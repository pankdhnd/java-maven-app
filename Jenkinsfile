pipeline {
  agent any
    stages{
      stage("build"){
        steps{
          echo "printing from build step"
        }
      }
      stage("test"){
        steps{
          echo "printing from test step"
        }
      }
      stage("deply"){
        steps{
          echo "printing from deploy step"
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
   }
} 

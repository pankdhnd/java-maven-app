def incrementVersion() {
    echo "Incrementing application version"
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
    versions:commit'
    def extractedVersion = readFile("pom.xml") =~ '<version>(.+)</version>'
    env.imageVersion = extractedVersion[0][1] + '-' + env.BUILD_NUMBER
    echo "updated version is ${imageVersion}"
    //In above statement, array element 0 stands for <version tag>, and element 1 stands for the value inside of it. This second dimention can also be used to read child node elements in xml
    //BUILD_NUMBER is the jenkins environment variable, which prvides the build number of the pipeline
}

def buildJar() {
    echo "Building jarfile"
    //cleaning the package first to delete old files form target folder
    sh 'mvn clean package'
    sh 'mvn package'    
}

def buildImage() {    
    echo "Building docker image"
    withCredentials([
        usernamePassword(credentialsId: '3703ba37-a8bc-4d6e-8ab4-4bbbf4df5e8e', usernameVariable: 'USER', passwordVariable: 'PASS')
    ])

    {
        sh "docker build -t pankajdh/testrepo:java-maven-app-$imageVersion ."
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh "docker push pankajdh/testrepo:java-maven-app-$imageVersion"
    }
}

/*
Accpets build version input from user. This process has been automated in the above function with same name
def buildImage() {    
    def imageVersion = input message: "Kindly input the image version number", ok: "Accept", parameters: [string(name: 'VERSION', defaultValue: '', description: '')]
    echo "Building docker image"
    withCredentials([
                    usernamePassword(credentialsId: '3703ba37-a8bc-4d6e-8ab4-4bbbf4df5e8e', usernameVariable: 'USER', passwordVariable: 'PASS')
                  ])

                {
                sh "docker build -t pankajdh/testrepo:java-maven-app-${imageVersion} ."
                 sh 'echo $PASS | docker login -u $USER --password-stdin'
                 sh "docker push pankajdh/testrepo:java-maven-app-${imageVersion}"
                }
}

*/
def deployApp() {
    echo "Deploying the application"    
}



def commitVersion() {
    echo "Committing incremented build version to git"
    //Login to Git
    withCredentials([
                    usernamePassword(credentialsId: '20542441-57da-423b-b453-e284a0dd851b', usernameVariable: 'USER', passwordVariable: 'PASS')
                  ])

                {
                //Encode password to avoid errors in URL formation/access
                def encodedPassword = URLEncoder.encode("$PASS",'UTF-8')
                //set user.email and user.name configuration (not setting globally here)
                 sh 'git config user.email "jenkins-user@example.com"'
                 sh 'git config user.name "Jenkins"'

                 //set git remote url
                 sh "git remote set-url origin https://${USER}:${encodedPassword}@github.com/pankdhnd/java-maven-app.git"
                 sh 'git add .'
                 sh 'git commit -m "incremented build version from pipeline"'
                 sh 'git push origin HEAD:jenkins-job-docker'
                }
}

return this

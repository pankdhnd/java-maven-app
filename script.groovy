def incrementVersion() {
    echo "Incrementing application version"
    sh 'maven build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
    versions:commit'
    def extractedVersion = readFile("pom.xml") = ~ '<version>(.+)</version>'
    env.imageVersion = extractedVersion[0][1]-$BUILD_NUMBER
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

return this

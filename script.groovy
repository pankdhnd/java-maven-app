def buildJar() {
    echo "Building jarfile"
    sh 'mvn package'    
}

def buildImage() {    
    input message: "Kindly input the image version number", ok: "Accept", parameters: [string(name: 'VERSION', defaultValue: '', description: '')]
    echo "Building docker image"
    withCredentials([
                    usernamePassword(credentialsId: '3703ba37-a8bc-4d6e-8ab4-4bbbf4df5e8e', usernameVariable: 'USER', passwordVariable: 'PASS')
                  ])

                {
                sh 'docker build -t pankajdh/testrepo:java-maven-app-${VERSION} .'
                 sh 'echo $PASS | docker login -u $USER --password-stdin'
                 sh 'docker push pankajdh/testrepo:java-maven-app-${VERSION}'
                }
}

def deployApp() {
    echo "Deploying the application"    
}

return this
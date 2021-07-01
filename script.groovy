def buildApp() {
    echo "Building the application"
}

def testApp() {
    echo "Testing the application"
}

def deployApp() {
    decho "Deploying the application"
    echo "deploying version: ${params.VERSION}" 
}

return this
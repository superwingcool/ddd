node {
    checkout scm
    try {
        docker.image('ywchang/maven:3.3.3').inside {
            stage('package') { sh 'mvn clean install site' }
            stage('archive') { uploadArtifacts() }
        }
        stage('checkTestCoverage') { refreshTestCoverage() }
        stage('build-image') { buildImage() }
        stage('push-image') { pushImage() }
        playSuccessSound()
    }
    catch (err) {
        playFailureSound()
        throw err
    }
}

def buildImage() {
    sh 'docker build -t 10.202.129.42:58704/code-check-summary-service:latest .'
}

def pushImage() {
    sh 'docker push 10.202.129.42:58704/code-check-summary-service:latest'
}

def refreshTestCoverage() {
    validateTestCoverage()
    refreshThreshold()
}

def uploadArtifacts() {
    archiveArtifacts(artifacts: '**/target/*.jar')
    archiveArtifacts(artifacts: '**/target/**/findbugsXml.html')
    archiveArtifacts(artifacts: '**/target/**/site/**')
    archiveArtifacts(artifacts: '**/target/**/checkstyle/**')
}

def playSuccessSound() {
    httpRequest 'http://yanwei:981dd6ff08fc4f7c1b5216c1e35ed0af@10.202.129.42:49001/sounds/playSound?src=file:/var/jenkins_home/plugins/sounds/WEB-INF/lib/success.wav'
}

def playFailureSound() {
    httpRequest 'http://yanwei:981dd6ff08fc4f7c1b5216c1e35ed0af@10.202.129.42:49001/sounds/playSound?src=file:/var/jenkins_home/plugins/sounds/WEB-INF/lib/failure.wav'
}

def getJobNameInURL() {
    env.JOB_NAME.replaceAll("/", "%2f")
}

def getJobName() {
    env.JOB_NAME
}

def getBuildNumber() {
    env.BUILD_NUMBER
}

def getCurrentTestCoverage() {
    def matchSet = (readFile("target/site/jacoco/jacoco.xml") =~ /type="INSTRUCTION" missed="(\d+)" covered="(\d+)"/) as Set
    def missed = matchSet.last()[1] as int
    def covered = matchSet.last()[2] as int
    covered / (missed + covered) * 100
}

def fetchRemoteTestCoverage() {
    def resp;
    try {
        resp = httpRequest(url: "http://10.202.129.42:51080/jobs/${getJobNameInURL()}", acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'GET')
    }
    catch (err) {
        httpRequest(url: "http://10.202.129.42:51080/jobs", acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: "{\"id\": \"${getJobName()}\", \"testCoverage\": \"0\", \"buildNumber\": \"${getBuildNumber()}\"}")
        return 0
    }
    readJSON(text: resp.content).testCoverage as double
}

def validateTestCoverage() {
    def current = getCurrentTestCoverage()
    def remote = fetchRemoteTestCoverage()
    if (current < fetchRemoteTestCoverage() && current < 95 ) {
        throw new Exception("The current test coverage (${current}) is lower than the value(${remote}) from last run!")
    }
}

def refreshThreshold() {
    def current = getCurrentTestCoverage()
    httpRequest(url: "http://10.202.129.42:51080/jobs/${getJobNameInURL()}", acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'PUT', requestBody: "{\"id\": \"${getJobName()}\", \"testCoverage\": \"${current}\", \"buildNumber\": \"${getBuildNumber()}\"}")
}
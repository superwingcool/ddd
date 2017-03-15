pipeline {
    agent { docker 'ywchang/maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}

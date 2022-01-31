pipeline {
    agent {
        docker {
            image 'maven:3.8.4-openjdk-8'
            args '-v /opt/.m2:/root/.m2'
        }
    }
    stages {
        stage("Env Variables") {
            steps {
                sh "printenv"
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh './resources/build-image.sh'
            }
        }
    }
}
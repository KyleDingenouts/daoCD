pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
	stage('Sonarqube') {
    environment {
        scannerHome = tool 'SonarServer'
    }
    steps {
        withSonarQubeEnv('sonarqube') {
            sh "${scannerHome}/bin/sonar-scanner"
        }
        timeout(time: 10, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }
}
   
    stage('unittests'){
	steps{
	sh 'mvn test'
	}

	}
stage('mutation tests'){
steps{
sh 'mvn org.pitest:pitest-maven:mutationCoverage' 

}
	}}
post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }	
}

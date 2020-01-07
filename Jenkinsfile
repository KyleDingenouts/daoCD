pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
	
     stage('SonarQube analysis') {
	 steps{
    withSonarQubeEnv(installationName: 'SonarServer') { // You can override the credential to be used
      sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
    }}}
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

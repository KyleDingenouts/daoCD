pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
   stage('Build and Code Quality Test') {
            steps {
                withSonarQubeEnv('scanserver') {
                    // Optionally use a Maven environment you've configured already
                    sh 'mvn -B -DskipTests clean package sonar:sonar'
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

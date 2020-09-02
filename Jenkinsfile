pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {

      stage('unittests'){
	       steps{
	         sh 'mvn test'
	       }

	    }

      stage('mutation tests'){
        steps{
          sh 'mvn org.pitest:pitest-maven:mutationCoverage'

        }
	    }
    }
post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}

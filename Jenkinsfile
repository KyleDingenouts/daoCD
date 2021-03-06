pipeline {
	agent {
		docker {
			image 'maven:3-alpine'
			args '-v /root/.m2:/root/.m2'
		}
	}
	environment {
		NEXUS_VERSION = "nexus3"
		NEXUS_PROTOCOL = "http"
		NEXUS_URL = "sudohub.nl:8082"
		NEXUS_REPOSITORY = "maven-demo-repo"
		NEXUS_CREDENTIAL_ID = "nexus-credentials"
	}
	stages {

		stage('Build and Code Quality Test') {
			steps {
				withSonarQubeEnv('SonarServer') {
                    // Optionally use a Maven environment you've configured already
                    sh 'mvn -B -DskipTests clean package sonar:sonar'
                }
            }
        }

        stage('Unit tests'){
        	steps{
        		sh 'mvn test'
        	}
        }

        stage('Mutation tests'){
        	steps{
        		sh 'mvn org.pitest:pitest-maven:mutationCoverage'

        	}
        }

        stage("Build Maven") {
        	steps {
        		script {
        			sh 'mvn package -DskipTests=true'
        		}
        	}
        }

        stage("Publish to Nexus") {
        	steps {
        		script {
        			pom = readMavenPom file: "pom.xml";
        			filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
        			echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
        			artifactPath = filesByGlob[0].path;
        			artifactExists = fileExists artifactPath;
        			if(artifactExists) {
        				echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
        				nexusArtifactUploader(
        					nexusVersion: NEXUS_VERSION,
        					protocol: NEXUS_PROTOCOL,
        					nexusUrl: NEXUS_URL,
        					groupId: pom.groupId,
        					version: pom.version,
        					repository: NEXUS_REPOSITORY,
        					credentialsId: NEXUS_CREDENTIAL_ID,
        					artifacts: [
        					[artifactId: pom.artifactId,
        					classifier: '',
        					file: artifactPath,
        					type: pom.packaging],
        					[artifactId: pom.artifactId,
        					classifier: '',
        					file: "pom.xml",
        					type: "pom"]
        					]
        					);
        				} else {
        					error "*** File: ${artifactPath}, could not be found";
        				}
        			}
        		}
        	}
        }

        post {
        	always {
        		junit 'target/surefire-reports/*.xml'
        	}
        }
    }

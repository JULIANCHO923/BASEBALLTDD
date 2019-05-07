pipeline{	
	agent{		
		label 'Slave_Induccion' 		
	}	
	
      //Opciones específicas de Pipeline dentro del Pipeline  
    options {		
        //Mantener artefactos y salida de consola para el # específico de ejecuciones recientes del Pipeline.
        buildDiscarder(logRotator(numToKeepStr: '3'))
        //No permitir ejecuciones concurrentes de Pipeline
        disableConcurrentBuilds()  		
    }

    //Define las herramientas 
    tools {    		
        jdk 'JDK8_Centos' //Preinstalada en la Configuraci?n del Master    
        gradle 'Gradle4.5_Centos' //Preinstalada en la Configuraci?n del Master  	
    }
	
	environment {		
        isPublish = false
        dir = "Java/TDD/BaseballTDD"	
        dirArtifactoryCeiba = " http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local"
        dirEnArtifactory = "CoachEPM/Java/TDD/BaseballTDD"
        project = "BaseballTDD"			
    }
  
    //Aquí comienzan los items del Pipeline  
    stages{
        stage('Checkout') {
            steps{
				echo "####################->Init Checkout<-####################"
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], 
					doGenerateSubmoduleConfigurations: false, 
					extensions: [[$class: 'RelativeTargetDirectory', 
					relativeTargetDir: '']], 
					gitTool: 'Git_Centos', submoduleCfg: [], 
					userRemoteConfigs: [[credentialsId: 'Coach_EPM_Julian_Henao', url: 'https://tfs.ceiba.com.co/tfs/DefaultCollection/_git/EPM%20-%20Entrenamiento%20coach']]])									
					sh 'gradle --b ./${dir}/build.gradle clean'
				echo "####################->End Checkout<-####################"
      		}
    	}
	
		stage('Compile'){
            steps{
                echo "####################->Init Compile<-####################"
                //sh './Java/PrincipiosSOLID/openClosed/WithOpenClose/ gradle clean'
				sh 'gradle --b ./${dir}/build.gradle compileJava'	
				echo "####################->End Compile<-####################"
            }
            
        }    
    
    	stage('Unit Tests') {      
      		 steps{        
        		echo "####################->Init Unit Tests<-####################"
				sh 'gradle --b ./${dir}/build.gradle test'	
				echo "####################->End Unit Tests<-####################"
      		}   
    	}
    
    
    	stage('Static Code Analysis') {
 			environment {				
            	sonarScanner = "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"				
        	}		
    		steps{
		    	echo "####################->Init Static Code Analysis<-####################"  		
		      	withSonarQubeEnv('Sonar') {
					sh "${env.sonarScanner} -Dproject.settings=./${dir}/sonar-project.properties"			   		              
					echo "####################->End Static Code Analysis<-####################"
        		}     
    		}
  		}
       
  		stage('Build') {      
    		steps {
      			echo "####################->Init Build<-####################"      			
      			sh 'gradle --b ./${dir}/build.gradle build -x test'      			
				echo "####################->End Build<-####################"
    		}    
  		}
  		
  		stage('Publish') {       
	        steps{
		        echo "####################->Init Publish [Artifactory]<-####################"
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{
		                "pattern": "**/${project}/build/libs/*.jar",
		                "target": "libs-snapshot-local/${dirEnArtifactory}/build/"
		                }]}'''
	                def buildInfo = server.upload(uploadSpec)
	            	isPublish = server.publishBuildInfo(buildInfo)	            	
				echo "####################->End Publish [Artifactory]<-####################"
		       }
            }
        }

//		stage("Deployment in testing environment") {
//			steps {
//				echo "####################->Init Deployment in testing environment<-####################"				
//				sshPublisher(
//					publishers: [
//						sshPublisherDesc(
//							configName: 'FunctionalTest', 
//							transfers: [
//								sshTransfer(excludes: '', 
//								execCommand: ''' wget ${dirArtifactoryCeiba}/${dirEnArtifactory}/build/BaseballTDD-2.0-SNAPSHOT.jar
//								mv BaseballTDD-2.0-SNAPSHOT.jar pruebaDespliegue/JulianHenao_BaseballTDD-2.0-SNAPSHOT.jar ''', 
//								execTimeout: 120000, 
//								flatten: false, 
//								makeEmptyDirs: false, 
//								noDefaultExcludes: false, 
//								patternSeparator: '', 
//								remoteDirectory: '', 
//								remoteDirectorySDF: false, 
//								removePrefix: '', 
//								sourceFiles: 'BaseballTDD-2.0-SNAPSHOT.jar')
//							], 
//							usePromotionTimestamp: false, 
//							useWorkspaceInPromotion: false, 
//							verbose: false
//						)
//					]
//				)
//				echo "####################->End Deployment in testing environment<-####################"              
//			}
//			
//			post { 
//				success {      
//	    			echo "####################->Success Deployment in testing environment<-####################"
//	  			}
//				failure {   	   
//	    			echo "####################->Failure Deployment in testing environment<-####################"
//	  			}
//	  			unstable {   	   
//	    			echo "####################->Unstable Deployment in testing environment<-####################"
//	  			}
//			}
//		} 

	}

	post {    
	  always {      
		echo "####################->Init Post always<-####################"
	    junit 'Java/TDD/BaseballTDD/build/test-results/test/*.xml'	  
	    jacoco classPattern:'Java/TDD/BaseballTDD/build/classes/java', sourcePattern:'Java/TDD/BaseballTDD/src/main/java', execPattern:'Java/TDD/BaseballTDD/build/jacoco/*.exec' 
		echo "####################->End Post always<-####################"
	  }    
	  failure {   	   
	    echo "####################->Init Post failure<-####################"
	    //      Send notifications about a Pipeline to an email
	    mail (to: 'julian.henao@ceiba.com.co',
	               subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
	               body: "Something is wrong with ${env.BUILD_URL}")
		echo "####################->End Post failure<-####################"
	  }      
	  changed {      
		echo "####################->Init Post Changed<-####################"	    
	    //      This will run only if the state of the Pipeline has changed
	    //      For example, if the Pipeline was previously failing but is now successful'
	    //      Send notifications about a Pipeline to an email
	          mail (to: 'julian.henao@ceiba.com.co',
	               subject: "Changed State Pipeline: ${currentBuild.fullDisplayName}",
	               body: "The state of the Pipeline has changed. See ${env.BUILD_URL}")
		echo "####################->End Post Changed<-####################"
	  }  
	 }    
	}
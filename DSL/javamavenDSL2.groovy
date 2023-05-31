job('Java Maven App DSL 2 - File Script') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/carnol1989/simple-java-maven-app.git', 'master') { node ->
            node / gitConfigName('carnol1989')
            node / gitConfigEmail('carnole1989@outlook.com')
        }
    }
    steps {
        maven {
          mavenInstallation('udemy-jenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('udemy-jenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL 2/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}

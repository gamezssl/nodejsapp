job('/Learning-jobs/Udemy-Jenkins-Cero-Experto-2024/7-Jobs-DSL-Node.js/2-app-con-node.js-docker-DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/gamezssl/nodejsapp.git', 'master') { node ->
            node / gitConfigName('gamezssl')
            node / gitConfigEmail('hunterwebs89@protonmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('nodejs-jenkins')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('gamezssl/nodejsapp')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('dockerhub')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
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

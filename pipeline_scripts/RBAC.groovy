pipeline {
    agent any

    stages {
        stage('Git clone') {
              steps {
                git branch: 'main',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            }
        }
        stage('Deploy RBAC') {
            steps {
               script {
                    sh """
                    cd ${WORKSPACE}/Kubernetes/
                        kubectl ${kube_command} -f RBAC 
                    """
               }
            }
        }
    }
}

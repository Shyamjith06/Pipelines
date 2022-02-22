pipeline {
    agent { label 'slave_one' }

    stages {
    
        stage('Cloning factory repo') {
              steps {
              
                git branch: 'main',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            
            }
            
            }
      stage('Building AMI'){
            steps {
                 withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
              
                script {
                    sh """
                    cd ${WORKSPACE}/packerfiles/
                       packer --version
                    """
                   }
                  }
                }
              }
            }
}

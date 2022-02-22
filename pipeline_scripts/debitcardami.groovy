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
                script {
                    sh """
                    cd ${WORKSPACE}/packer/
                     packer build -var 'aws_access_key=${env.ACCESS_KEY}' -var 'aws_secret_key=${env.SECRET_KEY}' debitcard.json
                     }
                     }
                     }
                     }
                     }

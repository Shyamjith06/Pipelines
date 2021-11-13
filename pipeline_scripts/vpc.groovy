pipeline {
    agent any

    stages {
        stage('Cloning factory Repo') {
            steps {
                git branch: 'main',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            }
              }
        stage('Execute Terraform to Provison EKS'){
            steps {
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/factory_vpc/
                        terraform init
                        terraform workspace select ${params.env}
                        terraform plan
                        
                        terraform ${params.terraform_option} -auto-approve
                        
                        """
                       }
                    }
            }
    }

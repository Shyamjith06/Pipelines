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
                    cd ${WORKSPACE}/eks_setup/terraform/config/eks_setup/
                        terraform init
                        terraform plan
                        
                        terraform apply -auto-approve
                        """
                       }
                    }
            }
        }
}

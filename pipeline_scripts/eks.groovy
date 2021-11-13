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
        stage('parameter check') {
            steps {
                sh """
                echo "hello-world"
                echo '${Environment}'
                """
              }
        stage('Execute Terraform to Provison EKS'){
            steps {
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/eks_setup/
                        terraform workspace select factory
                        terraform init
                        terraform plan
                        
                        terraform apply -auto-approve
                        """
                       }
                    }
            }
        }
}

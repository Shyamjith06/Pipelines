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
                    cd ${WORKSPACE}/terraform/config/eks_setup/
                        terraform init
                        terraform workspace select ${params.env}
                        terraform plan
                        
                        terraform ${params.terraform_option} -auto-approve
                        terraform output >> aws-auth-conf.yaml
                        """
                       }
                    }
            }
        stage('Setting up the Cluster Access'){
            steps {
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/eks_setup/
                        aws eks --region eu-west-1 update-kubeconfig --name ${params.env}
                        kubectl apply -f aws-auth-conf.yaml
                        """
                }
            }
        }
        }
}

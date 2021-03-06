pipeline {
    agent { label 'slave_one' }

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
                withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/eks_setup/
                        terraform init
                        terraform workspace select ${params.env}
                        terraform plan
                        
                        terraform ${params.terraform_option} -auto-approve
                        
                        """
                       }
                
                }
            }
            }
        stage('Setting up the Cluster Access'){
             when {
                expression {
                 params.terraform_option == 'apply';
                    }
                }
            steps {
                withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/eks_setup/
                        terraform output auth-config >> aws-auth-conf.yaml
                        aws eks --region eu-west-1 update-kubeconfig --name ${params.env}
                        kubectl apply -f aws-auth-conf.yaml
                        rm aws-auth-conf.yaml
                        """
                }
                }
            }
        }
        }
}

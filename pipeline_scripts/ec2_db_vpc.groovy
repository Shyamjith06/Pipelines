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
              withAWS(credentials: 'jenkins_aws_user', region: 'eu-west-1'){
                script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/db_ec2_vpc/
                        terraform init
                        terraform workspace select factory
                        terraform plan
                        
                        terraform ${params.terraform_option} -auto-approve
                        
                        """
                       }
                    }
            }
    }
}

pipeline {
    agent any

    stages {
        stage('SCM') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Execute Terraform to Provison EKS'){
            steps {
                script {
                    sh """
                    cd ${WORKSPACE}/Terraform/Eks
                        terraform init
                        terraform plan
                        
                        terraform ${Terraform_condition} -auto-approve
                        """
                       }
                    }
            }
        }
}

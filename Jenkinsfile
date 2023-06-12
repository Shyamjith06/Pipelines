pipeline {
    agent any

    stages {
 stage('Cloning the App Repo') {
              steps { 
                  
                  dir('${WORKSPACE}/code'){
                git branch: 'master',
               
                    url: 'https://github.com/Shyamjith06/Hospital.git'      
            }          
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

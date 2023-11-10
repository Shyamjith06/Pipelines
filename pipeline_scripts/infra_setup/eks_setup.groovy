pipeline {
    agent  { 
        label "ubuntu"
    }
     parameters {
        choice(name: 'action', choices: ['apply', 'destroy'], description: 'Select the operation')
    }
   environment {
        TF_ACTION = "${params.action}"
    }
    stages {
    
        stage('Cloning factory repo for Terrafom scripts') {
              steps {
              
                git branch: 'main',
                    credentialsId: 'git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            
            }
            
            }
        stage('Creating VPC for eks'){
            steps {
                withAWS(credentials: 'aws', region: 'eu-west-1'){
                    script{
                    sh '''
                    cd ${WORKSPACE}/terraform/config/eks_vpc/
                    if terraform workspace list | grep -q "dev"; then
                        terraform workspace select dev
                    else
                        terraform workspace new dev
                    fi
                    terraform init
                    terraform ${TF_ACTION} -auto-approve
                    '''
                }
            }
        }
    }
    stage("Creating cluster"){
        steps {
            withAWS(credentials: 'aws', region: 'eu-west-1'){
            script{
                sh '''
                cd ${WORKSPACE}/terraform/config/eks_setup/
                if terraform workspace list | grep -q "dev"; then
                    terraform workspace select dev
                else
                    terraform workspace new dev
                fi
                terraform init
                terraform ${TF_ACTION} -auto-approve
                '''
            }}
        }
    }
}
}

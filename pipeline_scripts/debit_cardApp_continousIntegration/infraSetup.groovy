pipeline {
    agent { label 'slave_one' }

    stages {
    
        stage('Cloning factory repo for Terrafom scripts') {
              steps {
              
                git branch: 'main',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            
            }
            
            }
         
       
      
          stage('Installing Mysql'){
              steps {
              
              withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
              script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/CI_Mysql_SetUp/
                        terraform init
                        terraform plan -var dbpass=${env.DB_PASS} --out=plan.out
                            
                        terraform ${params.terraform_option} -var dbpass=${env.DB_PASS} -auto-approve
                                              
                  """               
                  }
                  
                
                }
               }
               }

pipeline {
    agent any

    stages {
    
        stage('Cloning factory repo for Terrafom scripts') {
              steps {
              
                git branch: 'main',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/factory.git'
            
            }
            
            }
         
       
      
          stage('Setting up infra'){
              steps {
              
              withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
              script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/CI_Mysql_SetUp/
                        terraform init
                        terraform plan -var dbpass=${env.DB_PASS} --out=plan.out
                            
                        terraform apply -var dbpass=${env.DB_PASS} -auto-approve
                                              
                  """               
                  }
                  
                
                }
               }
               }
         stage('Building the App..'){
              steps {
              script {
                  build job: 'buildApp',
                      parameters : [
                          string(name: 'BuildNumber', value: "${BUILD_NUMBER}")
                     ],
                          propagate: false
              }
              }
         }
        
      
        stage('Destroying Infra'){
              steps {
              
              withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
              script {
                    sh """
                    cd ${WORKSPACE}/terraform/config/CI_Mysql_SetUp/
                            
                        terraform destroy -var dbpass=${env.DB_PASS} -auto-approve
                                              
                  """               
                  }
                  
                
                }
               }
            }
        
    }
}
                      

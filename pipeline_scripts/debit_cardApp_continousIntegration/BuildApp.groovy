pipeline {
    agent { label 'slave_one' }
    parameters {
    string(name: 'BuildNumber', defaultValue: ' ', description: 'BUILD source')
    }
    stages {
    
        stage('Cloning the App Repo') {
              steps { 
                git branch: 'master',
                    credentialsId: 'Git',
                    url: 'https://github.com/Shyamjith06/debit_card_app.git'      
            }          
            }
      stage('Build app') {
              steps {
              script {
            //  withEnv(["DB_HOST=${dbHost}"]) {
                    sh """
                    cd ${WORKSPACE}/project_code/
                    mvn clean install
                  """
             //     }
                }
                }
                
       }
        
     stage('Uploading artifact to S#'){
              steps {
              
              withAWS(credentials: 'L-jenkinsuser', region: 'eu-west-1'){
                  
                  s3Upload(file:'project_code/target/*.war', bucket:'debitcard-app-artifact', path:'${WORKSPACE}/project_code/target/debitcardcustomer-0.0.1-SNAPSHOT.war')
            //  script {
                  //  sh """
                   // cd ${WORKSPACE}/terraform/config/CI_Mysql_SetUp/
                     //   terraform init
                     //   terraform plan -var dbpass=${env.DB_PASS} --out=plan.out
                            
                    //    terraform apply -var dbpass=${env.DB_PASS} -auto-approve
                                              
                 // """               
                 // }
                  
                
                }
               }
               }
        
        
    }
}

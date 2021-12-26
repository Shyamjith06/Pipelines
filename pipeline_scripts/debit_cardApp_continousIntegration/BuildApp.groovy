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
    }
}

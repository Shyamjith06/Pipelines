import groovy.json.JsonSlurper
pipeline {
    agent {
        label 'agent'
    }

    environment {
        REMOTE_MACHINE_NAME = '172.31.4.252'
        CRED_ID = 'server_cred'
        // machines = ['172.31.4.252', '172.31.2.206']
        // creds = [
        //     "3.22.41.76": 'server_cred',
        //     "172.31.2.206": 'value2',
        // ]
    }

    
    
    
    stages {
//         stage('Create PowerShell session') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: env.CRED_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
//                     powershell '''
//                         $content = get-content .\\dotnetDeploy\\idev1.json -Raw
//                         $contentobject = $content | ConvertFrom-Json
//                         $remoteHost=$contentobject.bei_services
//                         write-output "$remoteHost"
//                        # $session = New-PSSession -ComputerName $remoteHost -Credential (New-Object System.Management.Automation.PSCredential("$env:USERNAME", $(ConvertTo-SecureString "$env:PASSWORD" -AsPlainText -Force)))
//                        # Invoke-Command -Session $session -ScriptBlock {start-WebAppPool -Name "DefaultAppPool"}
//                         '''
//                 }
//             }
//         }
    
        stage('json') {
    steps {
        script {
            def jsonFilePath = "C:\\jenkins\\workspace\\deploy_to_iis\\dotnetDeploy\\idev1.json"  // Replace with your JSON file path
            def jsonSlurper = new groovy.json.JsonSlurper()
            def jsonData = jsonSlurper.parse(new File(jsonFilePath))
            def remoteHostIPs = jsonData.InternalIps
            for (remoteIp in remoteHostIPs.keySet()) {
                env.ip = remoteIp
                def cred = remoteHostIPs[env.ip]
                echo remoteIp
                // Read JSON file and parse data
            }
        }
    }
}
             
    }
}








stage('Print JSON File') {
    steps {
        script {
            // Read the text file
            def fileContent = bat(returnStdout: true, script: 'type C:\\jenkins\\workspace\\deploy_to_iis\\dotnetDeploy\\idev1.json')

            // Print the file content
            echo fileContent.trim()

            // Parse the JSON file
            def jsonFilePath = "C:\\jenkins\\workspace\\deploy_to_iis\\dotnetDeploy\\idev1.json"
            def jsonSlurper = new groovy.json.JsonSlurper()
            def jsonData = jsonSlurper.parseText(fileContent)

            // Access JSON data
            // Example: echo jsonData.keyName
        }
    }
}

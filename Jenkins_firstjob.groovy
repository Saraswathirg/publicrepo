pipeline{
    agent any
    parameters{
        string(name:'BRANCH', defaultValue:'master')
        string(name:'BUILD_NUMBER', defaultValue:'')
        string(name:'SERVER_IP', defaultValue:'')
    }
    stages{
        stage("clone the code"){
            steps{
                println "here the code cloned"
                sh "ls -l"
                checkout([
                    $class:'GitSCM',
                    branches:[[name:'${BRANCH}']],
                    userRemoteConfigs:[[url:'https://github.com/Saraswathirg/branchpubrepo.git']]
                ])
            }
        }
        stage("build the code"){
            steps{
                println "code built"
                sh """
                ls -l
                mvn clean package
                 """
            }
        }
        stage("store to s3"){
            steps{
                println "artifact stored to s3"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://alltime/${BRANCH}/${BUILD_NUMBER}/"
            }
        }
        stage("download to present location"){
            steps{
                println "downloaded"
                sh """
                aws s3 ls
                aws s3 ls s3://alltime
                aws s3 cp s3://alltime/${BRANCH}/${BUILD_NUMBER}/hello-${BUILD_NUMBER}.war ."""
    
            }
        }
        stage("copied artifact"){
            steps{
                println "artifact copied"
                sh "scp -o StrictHostKeyChecking=no -i /tmp/awsaws.pem hello-${BUILD_NUMBER}.war ec2-user@${SERVER_IP}:/var/lib/tomcat/webapps" 
            }
        }
    }
}
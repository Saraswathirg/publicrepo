pipeline{
    agent any
    parameters{
        string(name:'BRANCH', defaultValue:'master')
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
                sh "ls -lart ./*"
                sh "mvn clean package"
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
    
            }
        }
        stage("copied artifact"){
            steps{
                println "artifact copied"
            }
        }
    }
}
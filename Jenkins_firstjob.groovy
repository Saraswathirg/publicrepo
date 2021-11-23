pipeline{
    agent any
    stages{
        stage("clone the code"){
            steps{
                println "here the code cloned"
            }
        }
        stage("build the code"){
            steps{
                println "code built"
            }
        }
        stage("store to s3"){
            steps{
                println "artifact stored to s3"
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
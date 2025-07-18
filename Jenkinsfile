pipeline {
    agent any

    tools {
        jdk 'JAVA'
        maven 'mvn'
        git 'git'
    }

    environment {
        MVN_CMD = 'mvn clean install -DskipTests'
    }

    stages {
        stage('Clean Workspace') {
          steps {
          cleanWs()
       }
      } 
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/anwarr1/Scalable-E-Commerce-Platform.git', branch: 'master'
            }
        }

        stage('Build Services') {
            parallel {
                stage('Eureka Server') {
                    steps {
                        dir('Ecom-Backend/registry-server') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
                stage('API Gateway') {
                    steps {
                        dir('Ecom-Backend/gateway-ms') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
                stage('User MS') {
                    steps {
                        dir('Ecom-Backend/user-ms') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
                stage('Order MS') {
                    steps {
                        dir('Ecom-Backend/order-ms') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
                stage('Product Catalog') {
                    steps {
                        dir('Ecom-Backend/product-catalog-ms') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
                stage('Shopping Cart') {
                    steps {
                        dir('Ecom-Backend/shopping-cart-ms') {
                            sh "${MVN_CMD}"
                        }
                    }
                }
            }
        }

        stage('Docker Build') {
            parallel {
                stage('Build user-ms image') {
                    steps {
                        dir('Ecom-Backend/user-ms') {
                            sh 'docker build -t anwarswe/user-ms:latest .'
                        }
                    }
                }
                stage('Build order-ms image') {
                    steps {
                        dir('Ecom-Backend/order-ms') {
                            sh 'docker build -t anwarswe/order-ms:latest .'
                        }
                    }
                }
                stage('Build product-catalog-ms image') {
                    steps {
                        dir('Ecom-Backend/product-catalog-ms') {
                            sh 'docker build -t anwarswe/product-catalog-ms:latest .'
                        }
                    }
                }
                stage('Build shopping-cart-ms image') {
                    steps {
                        dir('Ecom-Backend/shopping-cart-ms') {
                            sh 'docker build -t anwarswe/shopping-cart-ms:latest .'
                        }
                    }
                }
                stage('Build gateway-ms image') {
                    steps {
                        dir('Ecom-Backend/gateway-ms') {
                            sh 'docker build -t anwarswe/gateway-ms:latest .'
                        }
                    }
                }
                stage('Build registry-server image') {
                    steps {
                        dir('Ecom-Backend/registry-server') {
                            sh 'docker build -t anwarswe/registry-server:latest .'
                        }
                    }
                }
            }
        }
        stage('Docker Login') {
    steps {
        withCredentials([usernamePassword(credentialsId: 'efa9d019-f829-401a-9205-b7e2fe3a4295', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
        }
    }
}

        stage('Push to Docker Hub') {
            parallel {
                stage('Push user-ms image') {
                    steps {
                        sh 'docker push anwarswe/user-ms:latest'
                    }
                }
                stage('Push order-ms image') {
                    steps {
                        sh 'docker push anwarswe/order-ms:latest'
                    }
                }
                stage('Push product-catalog-ms image') {
                    steps {
                        sh 'docker push anwarswe/product-catalog-ms:latest'
                    }
                }
                stage('Push shopping-cart-ms image') {
                    steps {
                        sh 'docker push anwarswe/shopping-cart-ms:latest'
                    }
                }
                stage('Push gateway-ms image') {
                    steps {
                        sh 'docker push anwarswe/gateway-ms:latest'
                    }
                }
                stage('Push registry-server image') {
                    steps {
                        sh 'docker push anwarswe/registry-server:latest'
                    }
                }
            }
        }
  
        stage('Deploy App') {
            steps {
                dir('Ecom-Backend/') {
                    sh '''
                        docker compose -f docker-compose.prod.yml down
                        docker compose -f docker-compose.prod.yml pull
                        docker compose -f docker-compose.prod.yml up -d
                    '''
                }
            }

        }
        
    }
}

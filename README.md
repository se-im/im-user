# DistributedMall
Project for course software engineering

# modules
- common   
    common dependency, such as beans, utils and mybatis generator
- dao  
    mapper
- service  
    service code


## notes on adding mybatis generator's generate command to the run menu for intellij idea

1. click the run target menu, select `Edit Configurations..`
2. Then a panel will be open. click the `+` button in the left up corner
3. choose maven. Then what you need to do is just to fill the parameters
4. The name option's value will be your command's name
5. For working directory, choose the absolute path of common module's root 
path in your computer
6. The most and last step, fill the Command line with `mybatis-generator:generate`
then click apply. 


## For docker-compose

to start it, just run the following command in the floder where the docker-compose file located
which is Docker/deploy  
`docker-compose up`  
if you want run it background, run it with parameter `-d`
if you want Build images before starting containers.
run it with parameter `--build`

to stop the container
run the following command
`docker-compose down` 




## install and run it
Environment requirement
- jdk8+
- maven3.6+
- mysql5.7+
- docker
- redis  
#### To run it
1. modify the configuration in application.yaml to your
environment, 
2. init the databases with the sql file in Docker/deploy-server/mmall.sql
3. start mysql server, redis server
4. run `mvn install` in the root directory
5. run `mvn spring-boot:run` to execute the program


git add .
git commit -m ""
git push origin master

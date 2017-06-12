# monitorapi
time api

This api is made for project Cluster & SmartMirror, it is designed to run on a raspberry pi in a docker container. the application uses a tomcat server in order to work, the default port is 8080. make sure to setup a tomcat server if you havent already. this is a RESTfull api which will return the current time in JSON format including the hour,minute, second, day, day_of_month, month and year.
setup tomcat server

https://www.jetbrains.com/help/idea/2017.1/creating-and-running-your-first-web-application.html
Examples

connect to the application(should start when you run the application):
{ip_cluster}:{port_api}/
http://localhost:8080/

to get the default time
{ip_cluster}:{poort_api}/time
http://localhost:8086/monitor/

JSON feed example:
{ "192.168.1.1":false,</br>
  "192.168.1.3":false,</br>
  "192.168.1.2":false,</br>
  "192.168.1.5":false,</br>
  "192.168.1.4":false,</br>
  "192.168.1.7":false,</br>
  "192.168.1.6":false</br>
}

to get the time of a specific time zone
{ip_cluster}:{port_api}/time/{Continent}/{Area}
http://localhost:8080/time/Asia/Seoul

JSON feed example


#Cluster

more information about the cluster: https://github.com/INF2A/RPI-docker-cluster
#SmartMirror

more information about the SmartMirror: https://github.com/INF2A/Smart-mirror

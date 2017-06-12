# monitorapi

This api is made for project Cluster & SmartMirror, it is designed to run on a raspberry pi in a docker container. the application uses a tomcat server in order to work, the default port is 8086. make sure to setup a tomcat server if you havent already. this is a RESTfull api which will return the current status of the cluster in JSON format. http://localhost:8086/monitor/ give you a output if the docker nodes are running(socket test).http://localhost:8086/monitor/{node name} give you a output of the information of containers on the node. </br>

setup tomcat server</br>

https://www.jetbrains.com/help/idea/2017.1/creating-and-running-your-first-web-application.html</br>
Examples

To get the default information.</br>
{ip_cluster}:{poort_api}/monitor</br>
http://localhost:8086/monitor/

JSON feed example:</br>
{ </br>
  "192.168.1.1":false,</br>
  "192.168.1.3":false,</br>
  "192.168.1.2":false,</br>
  "192.168.1.5":false,</br>
  "192.168.1.4":false,</br>
  "192.168.1.7":false,</br>
  "192.168.1.6":false</br>
}

To get the container information of a node.</br>
{ip_cluster}:{port_api}/monitor/{node name}</br>
http://localhost:8086/monitor/Shanghai

JSON feed example


<h1>Cluster</h1>

More information about the cluster: https://github.com/INF2A/RPI-docker-cluster
<h1>SmartMirror</h1>

More information about the SmartMirror: https://github.com/INF2A/Smart-mirror

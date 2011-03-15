#Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2006-2010.
#Licensed under the Apache License, Version 2.0 (the "License");
#you may not use this file except in compliance with the License.
#You may obtain a copy of the License at
#       http://www.apache.org/licenses/LICENSE-2.0
#Unless required by applicable law or agreed to in writing, software
#distributed under the License is distributed on an "AS IS" BASIS,
#WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#See the License for the specific language governing permissions and
#limitations under the License.
 
#during an install, the value of the argument passed in is 1
#during an upgrade, the value of the argument passed in is 2
if [ "$1" = "1" -o "$1" = "2" ] ; then
	#echo "The StoRM GridHTTPS plugin is installed but NOT configured yet. You need to use yaim to configure the plugin."
	#echo 'Creating links to StoRM GridHTTPS plugin jars in StoRM BackEnd lib folder'
	ln -sf /usr/share/java/storm-gridhttps-plugin/storm-gridhttps-plugin.jar /usr/share/java/storm-backend-server/
	ln -sf /usr/share/java/storm-gridhttps-plugin/httpclient-*.jar /usr/share/java/storm-backend-server/httpclient.jar
	ln -sf /usr/share/java/storm-gridhttps-plugin/httpcore-*.jar /usr/share/java/storm-backend-server/httpcore.jar
fi
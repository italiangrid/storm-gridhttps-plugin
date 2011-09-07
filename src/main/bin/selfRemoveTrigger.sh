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
 
#during an upgrade where this package is the new one and where this package is the old one the value of the first argument passed in is 2
#during a remove the value of the first argument passed in is 1

#NOTE: This script is executed also when this package is updated by a new version. 
#      Consider that it is executed before an analogous script possibly present in the new version.

if [ $1 -eq 2 ]; then
	if [ ! -s /usr/share/java/storm-backend-server/storm-gridhttps-plugin.jar ] ; then 
		echo 'Restoring links to StoRM GridHTTPS plugin jars in StoRM BackEnd lib folder'
		ln -sf /usr/share/java/storm-gridhttps-plugin/storm-gridhttps-plugin.jar /usr/share/java/storm-backend-server/
	fi
	if [ ! -s /usr/share/java/storm-backend-server/httpclient.jar ] ; then
		ln -sf /usr/share/java/storm-gridhttps-plugin/httpclient-*.jar /usr/share/java/storm-backend-server/httpclient.jar
	fi
	if [ ! -s /usr/share/java/storm-backend-server/httpcore.jar ] ; then
		ln -sf /usr/share/java/storm-gridhttps-plugin/httpcore-*.jar /usr/share/java/storm-backend-server/httpcore.jar
	fi
elif [ "$1" = "1" ] ; then
	#nothing to do
fi
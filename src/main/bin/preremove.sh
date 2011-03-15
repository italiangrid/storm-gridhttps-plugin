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

#during an upgrade, the value of the argument passed in is 1
#during an uninstall, the value of the argument passed in is 0
if [ "$1" = "0" -o "$1" = "1" ] ; then
	echo 'Removing old links to StoRM GridHTTPS plugin jars from StoRM BackEnd lib folder'
	if [ -s /usr/share/java/storm-backend-server/storm-gridhttps-plugin.jar ] ; then
		unlink /usr/share/java/storm-backend-server/storm-gridhttps-plugin.jar ;
	fi
	if [ -s /usr/share/java/storm-backend-server/httpclient.jar ] ; then
		unlink /usr/share/java/storm-backend-server/httpclient.jar ;
	fi
	if [ -s /usr/share/java/storm-backend-server/httpcore.jar ] ; then
		unlink /usr/share/java/storm-backend-server/httpcore.jar ;
	fi
fi;
The StoRM GridHTTPs Plugin
===============================

StoRM GridHTTPs Plugin is a plugin for StoRM BackEnd that allows the BackEnd component 
to interact seamlessly with StoRM GridHTTPs Server while managing file transfer requests.

## Supported platforms

* Scientific Linux 5 x86_64
* Scientific Linux 6 x86_64

## Required repositories

In order to build the StoRM GridHTTPs Plugin, please enable the following repositories on your build machine

### EPEL

<pre>
yum install epel-release
</pre>

## Building
Required packages:

* git
* maven

Download source files:
<pre>
git clone https://github.com/italiangrid/storm-gridhttps-plugin.git --branch GHTTPSP_1.1
cd storm-gridhttps-plugin
</pre>

Build commands:
<pre>
wget --no-check-certificate https://raw.github.com/italiangrid/build-settings/master/maven/cnaf-mirror-settings.xml -O mirror-settings.xml
mvn -s mirror-settings.xml clean
mvn -s mirror-settings.xml package -P EMI
</pre>

It could be necessary to set JAVA_HOME environment variable, for example:
<pre>
export JAVA_HOME="/usr/lib/jvm/java"
</pre>

# Contact info

If you have problems, questions, ideas or suggestions, please contact us at
the following URLs

* GGUS (official support channel): http://www.ggus.eu

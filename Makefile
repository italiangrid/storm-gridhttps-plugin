ifndef JAVA_HOME
  $(error JAVA_HOME is undefined)
endif

version:=$(shell mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression="project.version" | grep -v '\[' | tr -d ' ')
release:=1
osdist:=$(shell cat /etc/redhat-release | awk 'BEGIN {FS="release "} {print $$2}' |  awk 'BEGIN {FS="."} {print $$1}')

# mvn settings mirror conf url
mirror_conf_url=https://raw.github.com/italiangrid/build-settings/master/maven/cnaf-mirror-settings.xml

# name of the mirror settings file
mirror_conf_name=mirror-settings.xml

mvn_settings=-s $(mirror_conf_name)

local_maven_repository:=/tmp/m2-repository

all: clean bin_rpm

src_rpm: build_sources
	mkdir -p rpmbuild/BUILD rpmbuild/RPMS rpmbuild/SOURCES rpmbuild/SPECS rpmbuild/SRPMS
	cp target/storm-gridhttps-plugin-${version}-src.tar.gz rpmbuild/SOURCES/storm-gridhttps-plugin-${version}.tar.gz
	rpmbuild -bs --define "_topdir ${PWD}/rpmbuild" target/spec/storm-gridhttps-plugin.spec

build_sources: prepare
	mvn ${mvn_settings} -Drelease=$(release) -Dmaven_settings="$(mvn_settings)" -Dlocal_repository_dir="${local_maven_repository}" -P EMI process-sources

bin_rpm: src_rpm
	rpmbuild --rebuild --define "_topdir ${PWD}/rpmbuild" --define "dist .el$(osdist)" rpmbuild/SRPMS/storm-gridhttps-plugin-${version}-$(release).src.rpm

clean:
	rm -rf rpmbuild
	mvn ${mvn_settings} clean
	rm -f ${mirror_conf_name}

prepare:
	wget --no-check-certificate $(mirror_conf_url) -O $(mirror_conf_name)
	wget http://storm.forge.cnaf.infn.it/repository/internal/backend/storm-backend-server.jar -O storm-backend-server.jar

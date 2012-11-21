ifndef JAVA_HOME
  $(error JAVA_HOME is undefined)
endif
release:=1
version:=$(shell mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression="project.version" | grep -v '\[' | tr -d ' ')
platform:=sl5
age=$(release).$(platform)
# mvn settings mirror conf url
mirror_conf_url=https://raw.github.com/italiangrid/build-settings/master/maven/cnaf-mirror-settings.xml

# name of the mirror settings file
mirror_conf_name=mirror-settings.xml

mvn_settings=-s $(mirror_conf_name)

all: clean bin_rpm

src_rpm: build_sources
	mkdir -p rpmbuild/BUILD rpmbuild/RPMS rpmbuild/SOURCES rpmbuild/SPECS rpmbuild/SRPMS
	cp target/storm-gridhttps-plugin-${version}-src.tar.gz rpmbuild/SOURCES/storm-gridhttps-plugin-${version}.tar.gz
	rpmbuild -bs --define "_topdir ${PWD}/rpmbuild" target/spec/storm-gridhttps-plugin.spec

build_sources: prepare
	mvn ${mvn_settings} -Drelease=$(age) -Dmaven_settings="$(mvn_settings)" process-sources

bin_rpm: src_rpm
	rpmbuild --rebuild --define "_topdir ${PWD}/rpmbuild" rpmbuild/SRPMS/storm-gridhttps-plugin-${version}-$(age).src.rpm

clean:
	rm -rf rpmbuild
	mvn ${mvn_settings} clean
	rm -f ${mirror_conf_name}

prepare:
	wget $(mirror_conf_url) -O $(mirror_conf_name)

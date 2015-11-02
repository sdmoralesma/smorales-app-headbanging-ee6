FROM ubuntu:14.04.3
MAINTAINER Sergio Morales "sdmoralesma@gmail.com"

#Install latest java jdk
RUN export DEBIAN_FRONTEND=noninteractive && \ 
    apt-get -qq update && \
    apt-get -qq install -y software-properties-common python-software-properties unzip && \
    add-apt-repository ppa:webupd8team/java && \
    apt-get -qq update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get -qq install -y oracle-java8-installer oracle-java8-set-default

# Install WildFly to /opt
ENV WILDFLY_VERSION 9.0.1.Final
RUN cd /opt && wget --quiet http://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
    tar xvf wildfly-$WILDFLY_VERSION.tar.gz && \
    ln -s /opt/wildfly-$WILDFLY_VERSION /opt/wildfly && \
    rm wildfly-$WILDFLY_VERSION.tar.gz
ENV JBOSS_HOME /opt/wildfly

# Create admin user for wildfly
RUN $JBOSS_HOME/bin/add-user.sh admin admin123 --silent

#add datasource to wildfly
ADD wildfly-config/scripts $JBOSS_HOME/scripts/
ADD wildfly-config/connector $JBOSS_HOME/connector/
RUN $JBOSS_HOME/scripts/execute.sh

# Solves bug in history
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/current

# Install Maven
ENV MAVEN_VERSION 3.3.3
RUN wget --quiet -O /tmp/apache-maven-$MAVEN_VERSION.tar.gz http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar xzf /tmp/apache-maven-$MAVEN_VERSION.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-$MAVEN_VERSION /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin && \
    rm -f /tmp/apache-maven-$MAVEN_VERSION.tar.gz
ENV MAVEN_HOME /opt/maven
ENV USER_HOME /home/wildfly
ENV MAVEN_M2 $USER_HOME/.m2/repository

# Create the wildfly user and group
RUN groupadd -r wildfly-group -g 433 && \
    useradd -u 431 -r -g wildfly-group -s /bin/false wildfly -m

# Cache dependencies
ADD headbanging-app/pom.xml $USER_HOME/headbanging-app/
RUN mvn verify clean --fail-never -f $USER_HOME/headbanging-app/pom.xml -Dmaven.repo.local=$MAVEN_M2

# Auto-Deploy headbanging-app to Wildfly
ADD headbanging-app/src $USER_HOME/headbanging-app/src
RUN mvn package -f $USER_HOME/headbanging-app/pom.xml -Dmaven.repo.local=$MAVEN_M2 && \
    cp $USER_HOME/headbanging-app/target/headbanging-app.war $JBOSS_HOME/standalone/deployments/

# Run everything below as the wildfly user
RUN chown -R wildfly:wildfly-group $JBOSS_HOME/* && \
    chown -R wildfly:wildfly-group $USER_HOME/* && \
    chmod -R 777 $JBOSS_HOME/* && \
    chmod -R 777 $USER_HOME/*
USER wildfly

# Expose the ports
EXPOSE 8080 9990 8787

# Boot WildFly in the standalone mode and bind to all interfaces
CMD ["sh", "-c", "${JBOSS_HOME}/bin/standalone.sh", "--debug", "8787", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

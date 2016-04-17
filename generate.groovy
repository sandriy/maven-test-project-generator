#!/usr/bin/env groovy
class PomFile {
  String groupId
  String artifactId
  String version
  File folder

  PomFile (File folder, String groupId, String artifactId, String version) {
    this.folder = folder
    this.groupId = groupId
    this.artifactId = artifactId
    this.version = version
  }

  def writePomFile () {
    new File(this.folder,'pom.xml').withWriter('utf-8') { writer ->
        def builder = new groovy.xml.MarkupBuilder(writer)
        def mkp = builder.getMkp()
        mkp.xmlDeclaration(
          'version':'1.0',
          'encoding':'UTF-8'
        )
/*
          builder.bind {
            mkp.namespaces << [
              'xmlns':"http://maven.apache.org/POM/4.0.0",
              'xmlns:xsi':"http://www.w3.org/2001/XMLSchema-instance",
              'xsi:schemaLocation':"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            ]
          }
          builder.encoding = "UTF-8"
*/
          builder.project ( 
              'xmlns':"http://maven.apache.org/POM/4.0.0",
              'xmlns:xsi':"http://www.w3.org/2001/XMLSchema-instance",
              'xsi:schemaLocation':"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            ){
            modelVersion("4.0.0")
            parent {
              groupId("xyz")
              artifactId("abc")
              version(this.version)
            }
            groupId(this.groupId)
            artifactId(this.artifactId)

            packaging('pom')
        }
    }    
  }
}


def folder = new File (".", "reactor")

if ( folder.exists() ) {
  folder.deleteDir()
}
folder.mkdirs()

def levelList = []
(1..10).each {
  level ->
  def levelFormat = sprintf ("%03d", level)
  def levelModuleName = 'level-' + levelFormat
  println "Level: ${levelModuleName}"

  levelFolder = new File (folder, levelModuleName);
  levelFolder.mkdirs()

  PomFile pf = new PomFile (levelFolder, "org.test", levelModuleName, "1.0-SNAPSHOT")

  pf.writePomFile()
   
  levelList << levelModuleName 
}


/*
(1..1000).each {
  module ->
  println module

  def moduleFolder = new File ( folder, "module" + module)
  moduleFolder.mkdirs()
  
}
*/

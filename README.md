# VTK-JAVA-Natives

This library makes it easy to access [vtk](www.vtk.org) from java.
It automatically extracts and loads all the required native libraries through maven artifacts.

## Use VTK in your Java project

```xml
<dependency>
	<groupId>ch.unibas.cs.gravis</groupId>
	<artifactId>vtkjavanativesall</artifactId>
	<version>0.1.1</version>
</dependency>
```
This brings VTK 9.1 libraries for Windows, Linux and macOS+Intel (M1 will come later). It also brings JOGL and Glugen 2.4-rc4.

Unpacking and loading native libraries requires this line at the beginning of a program :

```java
VtkNativeLibraries.initialize(new File(System.getProperty("java.io.tmpdir")));
```

Using a temporary directory is not compulsory, this is just an example.

An example of how this library can be used is given in ```Main.java```, which is located
in the `src` directory.


## Build VTK Maven artifacts

### Structure

The project is divided in the following modules:

* The logic for initialization and loading the libraries can be found in `vtkJavaNatvies`
* `vtkJar` is a dummy module, whose purpose is to manage `vtk.jar` as a dependency
* The modules `vtkJavaNativesPLATFORMImpl` are platform specific packages, which contain all the
  native libraries for the corresponding platform in the resource folder.


### Unpack all classes and native library files
#### Unpacking GLUEGEN Jars (containing natives)

mkdir gluegenJar/src/
mkdir gluegenJar/src/main/
mkdir gluegenJar/src/main/resources/
cd gluegenJar/src/main/resources/

jar -xf ../../../lib/gluegen-rt-natives-linux-aarch64.jar
jar -xf ../../../lib/gluegen-rt-natives-linux-amd64.jar
jar -xf ../../../lib/gluegen-rt-natives-linux-armv6hf.jar
jar -xf ../../../lib/gluegen-rt-natives-linux-i586.jar
jar -xf ../../../lib/gluegen-rt-natives-macosx-universal.jar
jar -xf ../../../lib/gluegen-rt-natives-windows-amd64.jar
jar -xf ../../../lib/gluegen-rt-natives-windows-i586.jar

Then in IDE make src/main/resources/ a classpath folder

#### Unpacking JOGL Jars (containing natives)

mkdir joglJar/src/
mkdir joglJar/src/main/
mkdir joglJar/src/main/resources/
cd joglJar/src/main/resources/

jar -xf ../../../lib/jogl-all-v2.4.0-rc4.jar

jar -xf ../../../lib/jogl-all-natives-linux-aarch64.jar
jar -xf ../../../lib/jogl-all-natives-linux-amd64.jar
jar -xf ../../../lib/jogl-all-natives-linux-armv6hf.jar
jar -xf ../../../lib/jogl-all-natives-linux-i586.jar
jar -xf ../../../lib/jogl-all-natives-macosx-universal.jar
jar -xf ../../../lib/jogl-all-natives-windows-amd64.jar
jar -xf ../../../lib/jogl-all-natives-windows-i586.jar

Then in IDE make src/main/resources/ a classpath folder

#### Unpacking VTK Jar

mkdir vtkJar/src/
mkdir vtkJar/src/main/
mkdir vtkJar/src/main/resources/
cd vtkJar/src/main/resources/

jar -xf ../../../lib/vtk-9.1.0.jar

Then in IDE make src/main/resources/ a classpath folder


#### Unpacking VTK binaries



mkdir target

wget -O target/vtk-linux.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Linux-x86_64-9.1.0-jdk11.zip
7z x target/vtk-linux.zip -ovtkJavaNativesLinuxImpl/src/main/resources/  vtk-Linux-x86_64

wget -O target/vtk-macos-x86_64.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-x86_64-9.1.0-jdk11.zip
7z x target/vtk-macos-x86_64.zip -ovtkJavaNativesLinuxImpl/src/main/resources/ vtk-Darwin-x86_64

| OS      | OS Versions   | CPU          | Java       | VTK | Archive                        |
|---------|---------------|--------------|------------|-----|--------------------------------|
| Ubuntu  | 20            | Intel x86_64 | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Linux-x86_64-9.1.0-jdk11.zip">vtk-Linux-x86_64-9.1.0-jdk11</a> |                                       
| macOS   | 10.12, 10.15  | Intel x86_64 | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-x86_64-9.1.0-jdk11.zip">vtk-Darwin-x86_64-9.1.0-jdk11</a> |                                       
| macOS   | 11.4          | Apple M1     | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-arm64-9.1.0-jdk11.zip">vtk-Darwin-arm64-9.1.0-jdk11</a> |                                       
| Windows | 10            | Intel x86_64  | JDK 14     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Windows-x86_64.zip">vtk-Windows-x86_64</a> |   



#### Add more platform build

The binaries in this project are listed [here](https://github.com/jzy3d/vtk-java-wrapper).


https://maven.apache.org/plugins/maven-dependency-plugin/unpack-mojo.html
https://maven.apache.org/plugins/maven-dependency-plugin/unpack-dependencies-mojo.html

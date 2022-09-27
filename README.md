# VTK-JAVA-Natives

This library makes it easy to access [vtk](www.vtk.org) from java.
It automatically extracts and loads all the required native libraries through maven artifacts.

## Use VTK in your Java project

```xml
<dependency>
    <groupId>org.jzy3d</groupId>
    <artifactId>vtk-java-all</artifactId>
    <version>1.0.0-SNAPSHOT</version>
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

The project is divided in the following modules:

* The logic for initialization and loading the libraries can be found in `vtkJavaNatvies`
* `vtkJar` is a dummy module, whose purpose is to manage `vtk.jar` as a dependency
* The modules `vtkJavaNativesPLATFORMImpl` are platform specific packages, which contain all the
  native libraries for the corresponding platform in the resource folder.

There are several steps to build all, they are depicted here after
* Unpack all JARs
* Unpack all natives for each platform
* Build all with maven
* Deploy all to a maven repository


### Unpack all JARs containing compiled Java classes

Here we regenerate jars containing classes suitable for all platforms.

#### Unpacking GLUEGEN Jars

```
mkdir gluegenJar/src/
mkdir gluegenJar/src/main/
mkdir gluegenJar/src/main/resources/
cd gluegenJar/src/main/resources/
```

#### Unpacking JOGL Jars

```
mkdir joglJar/src/
mkdir joglJar/src/main/
mkdir joglJar/src/main/resources/

cd joglJar/src/main/resources/
jar -xf ../../../lib/jogl-all-v2.4.0-rc4.jar
```

#### Unpacking VTK Jar

```
mkdir vtkJar/src/
mkdir vtkJar/src/main/
mkdir vtkJar/src/main/resources/
cd vtkJar/src/main/resources/
jar -xf ../../../lib/vtk-9.1.0.jar
```

Then in IDE make src/main/resources/ a classpath folder


#### Unpacking Binaries

Here we create one Jar per platform containing all natives : VTK, JOGL, GLUEGEN.

After handling one platform, it is worth verifying that a unit test exists and that
it can assert that libraries can be loaded.

##### Unpacking natives for Mac M1

###### Unpacking natives for Mac M1 : VTK libraries

```
rm -rf vtkJavaNativesMacOSM1Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/*
wget -O target/vtk-macos-arm64.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-arm64-9.1.0-jdk11.zip
7z x target/vtk-Darwin-arm64.zip -otarget
mv target/vtk-Darwin-arm64/* vtkJavaNativesMacOSM1Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/
rm target/vtk-Darwin-arm64.zip
rm -rf target/vtk-Darwin-arm64
```

###### Unpacking natives for Mac M1 : Gluegen

```
cd vtkJavaNativesMacOSM1Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../gluegenJar/lib/gluegen-rt-natives-macosx-universal.jar
cp natives/macosx-universal/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder


###### Unpacking natives for Mac M1 : JOGL

```
cd vtkJavaNativesMacOSM1Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../joglJar/lib/jogl-all-natives-macosx-universal.jar
cp natives/macosx-universal/* ./
rm -rf natives
cd ../../../../../../../../../../
```

###### Verify Mac M1 bundle works

* Run `TestVtkNativeLibrariesImplMacOS_M1`
* Run `DemoVtkNativeLibrariesImplMacOS_M1`





##### Unpackaing natives for Mac x86_64 : VTK

```
rm -rf vtkJavaNativesMacOSImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/*
wget -O target/vtk-Darwin-x86_64.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-x86_64-9.1.0-jdk11.zip
7z x target/vtk-Darwin-x86_64.zip -otarget
mv target/vtk-Darwin-x86_64/* vtkJavaNativesMacOSImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/
rm target/vtk-Darwin-x86_64.zip
rm -rf target/vtk-Darwin-x86_64
```

###### Unpacking natives for Mac x86_64 : Gluegen

The only difference with other platforms is the base folder

```
cd vtkJavaNativesMacOSImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../gluegenJar/lib/gluegen-rt-natives-macosx-universal.jar
cp natives/macosx-universal/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder


###### Unpacking natives for Mac x86_64 : JOGL

The only difference with other platforms is the base folder

```
cd vtkJavaNativesMacOSImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../joglJar/lib/jogl-all-natives-macosx-universal.jar
cp natives/macosx-universal/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder

###### Verify Mac x86_64 bundle works

* Run `TestVtkNativeLibrariesImplMacOS_x86_64`
* Run `DemoVtkNativeLibrariesImplMacOS_x86_64`




##### Unpackaing natives for Linux x86_64 : VTK

```
rm -rf vtkJavaNativesLinuxImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/*
wget -O target/vtk-Linux-x86_64.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Linux-x86_64-9.1.0-jdk11.zip
7z x target/vtk-Linux-x86_64.zip -otarget
mv target/vtk-Linux-x86_64/* vtkJavaNativesLinuxImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/
rm target/vtk-Linux-x86_64.zip
rm -rf target/vtk-Linux-x86_64
```

###### Unpacking natives for Linux x86_64 : Gluegen

The only difference with other platforms is the base folder and the jar to extract

```
cd vtkJavaNativesLinuxImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../gluegenJar/lib/gluegen-rt-natives-linux-amd64.jar
cp natives/linux-amd64/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder


###### Unpacking natives for Linux x86_64 : JOGL

The only difference with other platforms is the base folder and the jar to extract

```
cd vtkJavaNativesLinuxImpl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../joglJar/lib/jogl-all-natives-linux-amd64.jar
cp natives/linux-amd64/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder

###### Verify Linux x86_64 bundle works

* Run `TestVtkNativeLibrariesImplLinux_x86_64`


##### Unpacking natives for Windows x86_64 : VTK

```
rm -rf vtkJavaNativesWin64Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/*
wget -O target/vtk-Windows-x86_64.zip https://download.jzy3d.org/vtk/build/9.1.0/vtk-Windows-x86_64.zip
7z x target/vtk-Windows-x86_64.zip -otarget
mv target/vtk-Windows-x86_64/* vtkJavaNativesWin64Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl/
rm target/vtk-Windows-x86_64.zip
rm -rf target/vtk-Windows-x86_64
```

###### Unpacking natives for Windows x86_64 : Gluegen

The only difference with other platforms is the base folder and the jar to extract

```
cd vtkJavaNativesWin64Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../gluegenJar/lib/gluegen-rt-natives-windows-amd64.jar
cp natives/Windows-amd64/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder


###### Unpacking natives for Windows x86_64 : JOGL

The only difference with other platforms is the base folder and the jar to extract

```
cd vtkJavaNativesWin64Impl/src/main/resources/ch/unibas/cs/gravis/vtkjavanativelibs/impl
jar -xf ../../../../../../../../../../joglJar/lib/jogl-all-natives-windows-amd64.jar
cp natives/Windows-amd64/* ./
rm -rf natives
cd ../../../../../../../../../../
```

Then in IDE make src/main/resources/ a classpath folder

###### Verify Windows x86_64 bundle works

* Run `TestVtkNativeLibrariesImplWindows_x86_64`


##### TODO later
jar -xf ../../../../../../../../../joglJar/lib/jogl-all-natives-linux-amd64.jar
jar -xf ../../../lib/jogl-all-natives-linux-aarch64.jar
jar -xf ../../../lib/jogl-all-natives-linux-armv6hf.jar
jar -xf ../../../lib/jogl-all-natives-linux-i586.jar
jar -xf ../../../lib/jogl-all-natives-macosx-universal.jar
jar -xf ../../../lib/jogl-all-natives-windows-amd64.jar
jar -xf ../../../lib/jogl-all-natives-windows-i586.jar


Then in IDE make src/main/resources/ a classpath folder


#### List of VTK 9.1 libs

| OS      | OS Versions   | CPU          | Java       | VTK | Archive                        |
|---------|---------------|--------------|------------|-----|--------------------------------|
| Ubuntu  | 20            | Intel x86_64 | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Linux-x86_64-9.1.0-jdk11.zip">vtk-Linux-x86_64-9.1.0-jdk11</a> |                                       
| macOS   | 10.12, 10.15  | Intel x86_64 | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-x86_64-9.1.0-jdk11.zip">vtk-Darwin-x86_64-9.1.0-jdk11</a> |                                       
| macOS   | 11.4          | Apple M1     | JDK 11     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Darwin-arm64-9.1.0-jdk11.zip">vtk-Darwin-arm64-9.1.0-jdk11</a> |                                       
| Windows | 10            | Intel x86_64  | JDK 14     | 9.1 | <a href="https://download.jzy3d.org/vtk/build/9.1.0/vtk-Windows-x86_64.zip">vtk-Windows-x86_64</a> |   



#### Add more platform build

The binaries in this project are listed [here](https://github.com/jzy3d/vtk-java-wrapper).

#### Troubleshooting

Exception stating the following means that the detected architecture is ARM (e.g. Apple M1) but that you run on JVM for Intel x86_64 which won't be able to load libraries built for ARM.

```
mach-o, but wrong architecture
```

Solution 1 : use a JVM for ARM (Azul)
Solution 2 : force x86_64 at load time (`VtkNativeLibraries.initialize(new VtkNativeLibrariesImplMacOS_x86_64()`);

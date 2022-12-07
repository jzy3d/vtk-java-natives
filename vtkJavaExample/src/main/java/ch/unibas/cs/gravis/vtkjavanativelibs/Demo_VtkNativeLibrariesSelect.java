package ch.unibas.cs.gravis.vtkjavanativelibs;

import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MAJOR_VERSION;
import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MINOR_VERSION;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_M1;

/**
 * In case of exception with "mach-o, but wrong architecture", this means you try to load M1 libs on
 * a JVM for x86_64 architecture. Simply change to a Azul JVM suitable for M1 (Aarch64)
 * 
 * @author martin
 *
 */
public class Demo_VtkNativeLibrariesSelect {

  public static void main(String[] args) throws VtkJavaNativeLibraryException {
    System.out.println("vtk-native version: " + MAJOR_VERSION + "." + MINOR_VERSION);
    System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
    System.out.println("Java Version: " + System.getProperty("java.version"));
    System.out.println("Java Home: " + System.getProperty("java.home"));
    System.out.println("Current platform: " + Platform.getPlatform());

    VtkNativeLibraries.initialize();
    
    System.out.println("Run VTK to get its version : " + new vtk.vtkVersion().GetVTKVersion());

  }
}

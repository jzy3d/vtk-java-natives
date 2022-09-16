package ch.unibas.cs.gravis.vtkjavanativelibs;

import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MAJOR_VERSION;
import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MINOR_VERSION;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_x86_64;

/**
 * In case of exception with "mach-o, but wrong architecture", this means you try to load M1 libs on
 * a JVM for x86_64 architecture. Simply change to a Azul JVM suitable for M1 (Aarch64)
 * 
 * @author martin
 *
 */
public class Demo_VtkNativeLibrariesImplMacOS_x86_64 {

  public static void main(String[] args) throws VtkJavaNativeLibraryException {
    System.out.println("vtk-native version: " + MAJOR_VERSION + "." + MINOR_VERSION);
    System.out.println("Java version: " + System.getProperty("java.version"));
    System.out.println("Current platform: " + Platform.getPlatform());

    VtkNativeLibrariesImplMacOS_x86_64 impl = new VtkNativeLibrariesImplMacOS_x86_64();

    /*
     * for(URL url : impl.getJoglLibraries()) { System.out.println("JOGL Libraries : " + url); }
     */

    VtkNativeLibraries.initialize(impl);
    
    System.out.println("Run VTK to get its version : " + new vtk.vtkVersion().GetVTKVersion());

  }
}

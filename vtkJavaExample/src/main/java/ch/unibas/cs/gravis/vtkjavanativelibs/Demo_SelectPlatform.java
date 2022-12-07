package ch.unibas.cs.gravis.vtkjavanativelibs;

import java.io.File;
import java.util.ServiceLoader;

public class Demo_SelectPlatform {

  public static void main(String[] args) {
    ServiceLoader<VtkNativeLibrariesImpl> implLoader =
        ServiceLoader.load(VtkNativeLibrariesImpl.class);
    
    int k = 0;
    for (VtkNativeLibrariesImpl impl : implLoader) {
      k++;
      System.out.println(impl);
      System.out.println(impl.getJoglLibraries());
      //System.out.println(impl.getVtkLibraries());

    }
    
    System.out.println("Found " + k + " implementations");
    
    //System.out.println("Current -Djava.library.path=" + System.getProperty("java.library.path"));
    
    String classpath = System.getProperty("java.class.path");
    
    
    // Get all class path entries in a string array
    /*String[] classpathEntries = classpath.split(File.pathSeparator);
    for(String e: classpathEntries) {
      System.out.println(e);
    }*/
  }

}

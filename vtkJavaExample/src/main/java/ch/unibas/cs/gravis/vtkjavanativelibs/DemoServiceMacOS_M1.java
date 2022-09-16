package ch.unibas.cs.gravis.vtkjavanativelibs;

import java.net.URL;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_M1;

public class DemoServiceMacOS_M1 {

  public static void main(String[] args) throws VtkJavaNativeLibraryException {
    VtkNativeLibrariesImplMacOS_M1 impl = new VtkNativeLibrariesImplMacOS_M1();
    
    System.out.println("JOGL Libraries : " + impl.getJoglLibraries());
    
    URL url = impl.getClass().getResource("libjgluegen_rt.dylib");
    System.out.println(url);
    url = impl.getClass().getResource("natives/macosx-universal/libjogl_desktop.dylib");
    System.out.println(url);
    
  }

}

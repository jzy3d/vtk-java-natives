import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MAJOR_VERSION;
import static ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries.MINOR_VERSION;
import java.io.File;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import ch.unibas.cs.gravis.vtkjavanativelibs.Platform;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkJavaNativeLibraryException;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_M1;

public class TestVtkNativeLibrariesImplMacOS_M1 {
  @Test
  public void testJOGLLibrariesAvailable() {
    VtkNativeLibrariesImplMacOS_M1 impl = new VtkNativeLibrariesImplMacOS_M1();
    
    
    // Check VTK    
    Assert.assertEquals(222, impl.getVtkLibraries().size());

    int k = 0;
    for(URL url : impl.getVtkLibraries()) {
      //System.out.println("VTK Library " + k + " : " + url);
      Assert.assertNotNull(url);
      k++;
    }

    
    // Check JOGL
    Assert.assertEquals(6, impl.getJoglLibraries().size());
    
    k = 0;
    for(URL url : impl.getJoglLibraries()) {
      //System.out.println("JOGL Library " + k + " : " + url);
      Assert.assertNotNull(url);
      k++;
    }
    
  }
  
  @Test
  public void testLibResourceCanBeLoaded() throws VtkJavaNativeLibraryException {
    if(!Platform.isMacArm64()) {
      System.err.println("BYPASS TEST SINCE WE ARE NOT ON THE GOOD PLATFORM : " + Platform.getPlatform());
      return;
    }
    System.out.println("vtk-native version: " + MAJOR_VERSION + "." + MINOR_VERSION);
    System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
    System.out.println("Java Version: " + System.getProperty("java.version"));
    System.out.println("Java Home: " + System.getProperty("java.home"));
    System.out.println("Current platform: " + Platform.getPlatform());

    VtkNativeLibrariesImplMacOS_M1 impl = new VtkNativeLibrariesImplMacOS_M1();

    VtkNativeLibraries.initialize(new File("./target/natives/m1/"), impl);
    
    String version = new vtk.vtkVersion().GetVTKVersion();

    System.out.println("VTK version: " + version);

    Assert.assertEquals("9.1.0", version);
  }

}

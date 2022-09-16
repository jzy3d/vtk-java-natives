import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_x86_64;

public class TestVtkNativeLibrariesImplMacOS_x86_64 {
  @Test
  public void testLibResourceCanBeListed() {
    VtkNativeLibrariesImplMacOS_x86_64 impl = new VtkNativeLibrariesImplMacOS_x86_64();
    
    
    // Check VTK    
    Assert.assertEquals(222, impl.getVtkLibraries().size());

    int k = 0;
    for(URL url : impl.getVtkLibraries()) {
      System.out.println("VTK Library " + k + " : " + url);
      Assert.assertNotNull(url);
      k++;
    }

    
    // Check JOGL
    Assert.assertEquals(6, impl.getJoglLibraries().size());
    
    k = 0;
    for(URL url : impl.getJoglLibraries()) {
      System.out.println("JOGL Library " + k + " : " + url);
      Assert.assertNotNull(url);
      k++;
    }
    
  }
  
  // WEIRD PATH ISSUE WITH VTK
  /*@Test
  public void testLibResourceCanBeLoaded() throws VtkJavaNativeLibraryException {
    if(!Platform.isMacx86_64()) {
      System.err.println("BYPASS TEST SINCE WE ARE NOT ON THE GOOD PLATFORM : " + Platform.getPlatform());
      return;
    }
    System.out.println("vtk-native version: " + MAJOR_VERSION + "." + MINOR_VERSION);
    System.out.println("Java version: " + System.getProperty("java.version"));
    System.out.println("Current platform: " + Platform.getPlatform());

    VtkNativeLibrariesImplMacOS_x86_64 impl = new VtkNativeLibrariesImplMacOS_x86_64();

    VtkNativeLibraries.initialize(impl);
    
    String version = new vtk.vtkVersion().GetVTKVersion();
    Assert.assertEquals("9.1.0", version);

  }*/
}

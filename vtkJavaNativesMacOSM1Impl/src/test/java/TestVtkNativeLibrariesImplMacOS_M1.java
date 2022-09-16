import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import ch.unibas.cs.gravis.vtkjavanativelibs.impl.VtkNativeLibrariesImplMacOS_M1;

public class TestVtkNativeLibrariesImplMacOS_M1 {
  @Test
  public void testJOGLLibrariesAvailable() {
    VtkNativeLibrariesImplMacOS_M1 impl = new VtkNativeLibrariesImplMacOS_M1();
    
    
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
}

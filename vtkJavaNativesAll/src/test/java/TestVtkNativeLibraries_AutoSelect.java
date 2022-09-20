

import java.io.File;
import java.util.ServiceLoader;
import org.junit.Assert;
import org.junit.Test;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkJavaNativeLibraryException;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibraries;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibrariesImpl;

public class TestVtkNativeLibraries_AutoSelect {
  @Test
  public void testCanFindMultipleImplementations() throws VtkJavaNativeLibraryException {
    
    VtkNativeLibrariesImpl impl = VtkNativeLibraries.detectPlatform();
    
    System.out.println("Found implementation : " + impl);
    
    Assert.assertNotNull(impl);
    
    /*ServiceLoader<VtkNativeLibrariesImpl> implLoader =
        ServiceLoader.load(VtkNativeLibrariesImpl.class);
    
    int k = 0;
    for (VtkNativeLibrariesImpl impl : implLoader) {
      k++;
      System.out.println(impl);
    }
    
    System.out.println("Found " + k + " implementations");
    
    Assert.assertTrue("At least one implementation can be found in classpath", k>0);*/
  }

  @Test
  public void detect() throws VtkJavaNativeLibraryException {
    System.out.println("Found " + VtkNativeLibraries.detectPlatform().getClass().getSimpleName() + " implementations");
  }

}

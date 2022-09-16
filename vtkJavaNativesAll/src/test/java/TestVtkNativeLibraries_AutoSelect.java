

import java.util.ServiceLoader;
import org.junit.Assert;
import org.junit.Test;
import ch.unibas.cs.gravis.vtkjavanativelibs.VtkNativeLibrariesImpl;

public class TestVtkNativeLibraries_AutoSelect {
  @Test
  public void testCanFindMultipleImplementations() {
    ServiceLoader<VtkNativeLibrariesImpl> implLoader =
        ServiceLoader.load(VtkNativeLibrariesImpl.class);
    
    int k = 0;
    for (VtkNativeLibrariesImpl impl : implLoader) {
      k++;
      System.out.println(impl);
    }
    
    System.out.println("Found " + k + " implementations");
    
    Assert.assertTrue("At least one implementation can be found in classpath", k>0);
  }

}

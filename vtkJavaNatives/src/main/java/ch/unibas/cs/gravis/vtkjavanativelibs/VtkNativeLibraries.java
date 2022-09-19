/*
 * Copyright 2016 University of Basel, Graphics and Vision Research Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package ch.unibas.cs.gravis.vtkjavanativelibs;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ServiceLoader;
import com.jogamp.common.jvm.JNILibLoaderBase;


public class VtkNativeLibraries {
  /**
   * VERSION INFORMATION ===================
   */
  public static final int MAJOR_VERSION = 0;
  public static final int MINOR_VERSION = 1;
  
  public static boolean debug = true;


  /**
   * Initialize a native library bundle by detecting the one that match the platform architecture.
   *
   * @throws VtkJavaNativeLibraryException if anything goes wrong.
   */
  public static void initialize(File nativeLibraryBaseDirectory)
      throws VtkJavaNativeLibraryException {


    VtkNativeLibrariesImpl impl = detectPlatform();
    
    initialize(nativeLibraryBaseDirectory, impl);
  }

  public static VtkNativeLibrariesImpl detectPlatform() throws VtkJavaNativeLibraryException {
    String platform = Platform.getPlatform();

    if(debug)
      System.out.println("Running on platform : " + platform);
    
    VtkNativeLibrariesImpl impl = loadImplementation(platform);

    if (!impl.getSupportedPlatforms().contains(platform)) {
      throw new VtkJavaNativeLibraryException(
          "VtkNativeLibraries does not support platform " + platform);
    }
    return impl;
  }

  /**
   * Initialize a given native library bundle by extracting it in a given directory.
   *
   * @throws VtkJavaNativeLibraryException if anything goes wrong.
   */
  public static void initialize(File nativeLibraryBaseDirectory, VtkNativeLibrariesImpl impl)
      throws VtkJavaNativeLibraryException {
    
    if(debug)
      System.out.println("Using natives provided by " + impl.getClass().getSimpleName());
    
    // Create the target directory if it does not exist
    File nativeLibraryDir = Util.createNativeDirectory(nativeLibraryBaseDirectory);

    if(debug)
      System.out.println("Extract to " + nativeLibraryDir);


    // Loads mawt.so
    Toolkit.getDefaultToolkit();

    // Loads jawt.so - this seems to be required on some systems
    try {
      System.loadLibrary("jawt");
    } catch (UnsatisfiedLinkError ignored) {
    }

    // ---------------------------------
    // Copy and load JOGL libraries one by one

    for (URL libraryUrl : impl.getJoglLibraries()) {
      String nativeName = libraryUrl.getFile();
      File file = new File(nativeLibraryDir,
          nativeName.substring(nativeName.lastIndexOf('/') + 1, nativeName.length()));

      try {
        Util.copyUrlToFile(libraryUrl, file);
      } catch (IOException e) {
        throw new VtkJavaNativeLibraryException("Error while copying library " + nativeName, e);
      }

      Runtime.getRuntime().load(file.getAbsolutePath());

      // we register the library as loaded in jogl, as otherwise it will try to load it again.
      String joglName = getBasename(file);
      if (joglName.startsWith("lib")) {
        joglName = joglName.replace("lib", "");
      }
      JNILibLoaderBase.addLoaded(joglName);

      Runtime.getRuntime().load(file.getAbsolutePath());
    }

    // ---------------------------------
    // Copy and load VTK libraries one by one

    for (URL libraryUrl : impl.getVtkLibraries()) {
      String nativeName = libraryUrl.getFile();
      File file = new File(nativeLibraryDir,
          nativeName.substring(nativeName.lastIndexOf('/') + 1, nativeName.length()));

      try {
        Util.copyUrlToFile(libraryUrl, file);
      } catch (IOException e) {
        throw new VtkJavaNativeLibraryException("Error while copying library " + nativeName, e);
      }

      Runtime.getRuntime().load(file.getAbsolutePath());
    }


    // vtkNativeLibrary.DisableOutputWindow(null);
  }

  /**
   * Extract native libraries to the JVM defined temporary directory, retrieve through property
   * "java.io.tmpdir"
   * 
   * @return the temporary directory
   * @throws VtkJavaNativeLibraryException
   */
  public static File initialize() throws VtkJavaNativeLibraryException {
    File nativeDir = getTemporaryDirectory();
    initialize(nativeDir);
    return nativeDir;
  }

  public static File initialize(VtkNativeLibrariesImpl impl) throws VtkJavaNativeLibraryException {
    File nativeDir = getTemporaryDirectory();
    initialize(nativeDir, impl);
    return nativeDir;
  }

  private static File getTemporaryDirectory() {
    return new File(System.getProperty("java.io.tmpdir"));
  }



  private static String getBasename(File file) {
    String filename = file.getName().toString();
    String[] tokens = filename.split("\\.(?=[^\\.]+$)");
    return tokens[0];
  }

  /**
   * Select a platform matching the current platform and then load the binaries related to the
   * chosen platform, or fail if the current platform is not supported.
   * 
   * @param platform
   * @return
   * @throws VtkJavaNativeLibraryException
   */
  private static VtkNativeLibrariesImpl loadImplementation(String platform)
      throws VtkJavaNativeLibraryException {
    ServiceLoader<VtkNativeLibrariesImpl> implLoader =
        ServiceLoader.load(VtkNativeLibrariesImpl.class);

    int k = 0;
    for (VtkNativeLibrariesImpl impl : implLoader) {
      k++;
      if (impl.getSupportedPlatforms().contains(platform)) {
        return impl;
      }
    }
    // If we are still here, no module was found
    StringBuffer sb = new StringBuffer();
    sb.append("Could not load implementation for platform '" + platform + "' after evaluating " + k
        + " implementations : \n");

    for (VtkNativeLibrariesImpl impl : implLoader) {
      sb.append(impl.getClass().getSimpleName() + " supporting " + impl.getSupportedPlatforms());
    }
    throw new VtkJavaNativeLibraryException(sb.toString());

  }


}

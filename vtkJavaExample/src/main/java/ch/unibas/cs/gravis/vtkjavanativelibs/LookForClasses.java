package ch.unibas.cs.gravis.vtkjavanativelibs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class LookForClasses {

  public static void main(String[] args) throws ClassNotFoundException, IOException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {

    String path = "ch.unibas.cs.gravis.vtkjavanativelibs.impl";

    List<Class> implems = getClassesImplementing(VtkNativeLibrariesImpl.class, path);

    for (Class c : implems) {
      System.out.println("Implem : " + c);

      VtkNativeLibrariesImpl i = (VtkNativeLibrariesImpl) c.getDeclaredConstructor().newInstance();
      System.out.println(i.getSupportedPlatforms());
    }

  }

  /**
   * Despite we can seek all package by specifying "", we need to load the minimum amount of classes
   * for two reasons
   * <ul>
   * <li>speed
   * <li>avoid loading classes that should bind to native and hence would expect to know the path to
   * native libs.
   * </ul>
   * 
   * @param interfaze
   * @param packageName
   * @return
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public static List<Class> getClassesImplementing(Class interfaze, String packageName)
      throws ClassNotFoundException, IOException {
    List<Class> out = new ArrayList<>();

    Class[] cc = getClasses(packageName);

    for (Class clazz : cc) {
      for (Class interfazes : clazz.getInterfaces()) {
        if (interfazes.equals(interfaze)) {
          out.add(clazz);
          // System.out.println("Implem : " + clazz);
        }
      }
    }
    return out;
  }

  public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;

    String path = packageName.replace('.', '/');


    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<File>();

    while (resources.hasMoreElements()) {
      URL resource = resources.nextElement();
      dirs.add(new File(resource.getFile()));
    }

    ArrayList<Class> classes = new ArrayList<Class>();
    for (File directory : dirs) {
      classes.addAll(findClasses(directory, packageName));
    }
    return classes.toArray(new Class[classes.size()]);
  }

  /**
   * Recursive method used to find all classes in a given directory and subdirs.
   *
   * @param directory The base directory
   * @param packageName The package name for classes found inside the base directory
   * @return The classes
   * @throws ClassNotFoundException
   */
  private static List<Class> findClasses(File directory, String packageName)
      throws ClassNotFoundException {
    List<Class> classes = new ArrayList<Class>();

    if (!directory.exists()) {
      return classes;
    }

    File[] files = directory.listFiles();
    for (File file : files) {

      if (file.isDirectory()) {
        assert !file.getName().contains(".");

        // System.out.println(file.getName() + " in " + directory.getAbsolutePath());

        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        String name = file.getName().substring(0, file.getName().length() - 6);

        // name += ".class";
        String fullname = packageName + '.' + name;

        if (fullname.charAt(0) == '.') {
          fullname = fullname.substring(1);
        }

        // System.out.println(fullname);

        Class c = null;
        try {
          c = Class.forName(fullname);
        } catch (java.lang.NoClassDefFoundError e) {
        } catch (java.lang.UnsatisfiedLinkError e) {
          /* jogamp case */} catch (java.lang.InternalError e) {
          /* jogamp case: jogamp.nativewindow.drm.drmModeModeInfo */} catch (java.lang.ExceptionInInitializerError e) {
          /* jogamp case : X11SunJDKReflection */}

        if (c != null)
          classes.add(c);
      }
    }
    return classes;
  }
}

package SwingDebug;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author idanilov
 * 
 */
public class DebugHandler implements InvocationHandler {

	private Object obj;
	private StringConvertor stringConvertor;

	public static Class[] getAllInterfaces(Class clazz) {
		ArrayList<Class> list = new ArrayList<Class>();
		while (clazz != null) {
			list.addAll(Arrays.asList(clazz.getInterfaces()));
			clazz = clazz.getSuperclass();
		}
		return list.toArray(new Class[0]);
	}

	public static Object newInstance(Object obj, StringConvertor stringConvertor) {
		return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				getAllInterfaces(obj.getClass()), new DebugHandler(obj, stringConvertor));
	}

	private DebugHandler(Object obj, StringConvertor stringConvertor) {
		this.obj = obj;
		this.stringConvertor = stringConvertor;
	}

	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		Object result;
		try {
			System.out.print("[debug] " + m.getName() + "(");
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (i > 0) {
						System.out.print(", ");
					}
					System.out.print(toString(args[i]));
				}
			}
			System.out.print(")");
			result = m.invoke(obj, args);
			if (m.getReturnType() != Void.class)
				System.out.println(" returned " + toString(result));
			else
				System.out.println();
			System.out.println("----------------------------------------------------------");
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			System.out.flush();
		}
		return result;
	}

	public String toString(Object o) {
		if (o != null && o.getClass().isArray() && !o.getClass().getComponentType().isPrimitive()) {
			return Arrays.asList(o).toString();
		} else if (o != null && stringConvertor != null) {
			return stringConvertor.toString(o);
		} else {
			return String.valueOf(o);
		}
	}

	public interface StringConvertor {

		public String toString(Object obj);
		
	}

}

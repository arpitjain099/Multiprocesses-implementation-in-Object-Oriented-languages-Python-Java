
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Multimethod {
	
	private static Map<Signature, Method> dispatchCache = new HashMap<Signature, Method>();
	/*
	 <TRet> return type of the method to call
	 returnType return type of the method to call (an explicit class object is needed due to generic type erasure)
	 target object to call the method on
	 methodName name of the method to call
	 arguments to pass to the method
	 */
	public static <TRet> TRet call(Class<TRet> returnType, Object target,
			String methodName, Object... arguments) {
		try {
			return dispatch(returnType, target, methodName, arguments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	
	private static <TRet> TRet dispatch(Class<TRet> returnType, Object target,
			String methodName, Object... arguments)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method bestMatch = null; // will contain the method to call if one exists							    

		//create a list of all arguments
		List<Class<?>> argTypes = new ArrayList<Class<?>>();
		for (Object arg : arguments) {
			argTypes.add(arg == null ? null : arg.getClass());
		}

		// Is the call cached? if yes then no need to do all the processing
		Signature sig = new Signature(target.getClass(), methodName,
				returnType, argTypes);

		if (dispatchCache.containsKey(sig)) {
			bestMatch = dispatchCache.get(sig);
			return (TRet) bestMatch.invoke(target, arguments);
		}

		// Find all type-matching methods and store them
		Method[] methods = target.getClass().getMethods();
		ArrayList<Method> matchingMethods = new ArrayList<Method>();
		for (Method method : methods) {
			if (!method.getName().equals(methodName))
				continue;

			// Check for return type compatibility
			if (!isAssignableFrom(returnType, method.getReturnType()))
				continue;

			// Check for argument compatibility
			Class<?>[] parameters = method.getParameterTypes();
			if (arguments.length != parameters.length)
				continue;

			boolean paramsCompatible = true;
			for (int i = 0; i < parameters.length; i++) {
				if (arguments[i] == null)
					paramsCompatible = !parameters[i].isPrimitive(); // null can be assigned to any reference type
				else
					paramsCompatible = isAssignableFrom(parameters[i], argTypes.get(i));

				if (!paramsCompatible)
					break;
			}

			if (!paramsCompatible)
				continue;

			// we have a match!
			matchingMethods.add(method);
		}

		if (matchingMethods.size() == 0)
			throw new NoMatchingMethodException(sig);

		next_m1: for (Method m1 : matchingMethods) {
			for (Method m2 : matchingMethods) {
				if (m1 == m2)
					continue;
				if (!isMoreSpecific(m1, m2))
					continue next_m1;
			}
			bestMatch = m1;
			break;
		}
		// throw exception with all ambiguous signature
		if (bestMatch == null) {
			List<Signature> ambiguousSignatures = new ArrayList<Signature>();
			for (Method m : getAmbiguousMethods(matchingMethods))
				ambiguousSignatures.add(new Signature(m));

			for(Object arg : arguments)
			{
				System.out.println(arg);
			}
			throw new AmbiguousCallException(ambiguousSignatures);
		}

		dispatchCache.put(sig, bestMatch);

		return (TRet) bestMatch.invoke(target, arguments);
	}	

	private static boolean isAssignableFrom(Class<?> target, Class<?> src)
	{
		if(src == null)
			return false;
		else if(target.isAssignableFrom(src))
			return true;
		else if(target.equals(Object.class))
			return true;
		else {
			Class<?>[] boxedTypes = new Class<?>[] { 
					Integer.class, Double.class, Float.class, 
					Long.class, Short.class, Byte.class, 
					Character.class, Boolean.class };
			Class<?>[] unboxedTypes = new Class<?>[] {
					int.class, double.class, float.class,
					long.class, short.class, byte.class,
					char.class, boolean.class };

			for (int i = 0; i < boxedTypes.length; i++) {
				if ((target.equals(boxedTypes[i]) && src.equals(unboxedTypes[i]))
						|| (src.equals(boxedTypes[i]) && target.equals(unboxedTypes[i])))
					return true;
			}			
			return false;
		}
	}

	
	private static boolean isMoreSpecific(Method src, Method target) {
		// is return type at least as general?
		if (!isAssignableFrom(target.getReturnType(), src.getReturnType()))
			return false;

		Class<?>[] targetParams = target.getParameterTypes();
		Class<?>[] srcParams = src.getParameterTypes();

		// same number of parameters?
		if (srcParams.length != targetParams.length)
			return false;

		// are all parameters at least as specific?
		for (int i = 0; i < srcParams.length; i++) {
			if (!isAssignableFrom(targetParams[i], srcParams[i]))
				return false;
		}

		return true;
	}

	
	private static Collection<Method> getAmbiguousMethods(
			Collection<Method> methods) {
		Collection<Method> mostSpecific = new ArrayList<Method>();
		int maxSpecificity = -1; // number of methods less specific than the
								 // current most specific method
		                         // as determined by isMoreSpecific()

		for (Method m1 : methods) {
			int specificity = 0;
			for (Method m2 : methods) {
				if (m1 == m2)
					continue;

				specificity += isMoreSpecific(m1, m2) ? 1 : 0;
			}

			if (specificity > maxSpecificity) {
				mostSpecific.clear();
				mostSpecific.add(m1);
				maxSpecificity = specificity;
			} else if (specificity == maxSpecificity) {
				mostSpecific.add(m1);
			}
		}

		return mostSpecific;
	}
}
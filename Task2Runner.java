import javafx.scene.control.TextArea;

import java.lang.reflect.*;
import java.util.Arrays;
import HeroMethods.Repeat;

public class Task2Runner {
    public static void run(TextArea out) {
        try {
            Class<?> cls = Class.forName("HeroMethods.HeroMethods");
            Object target = cls.getDeclaredConstructor().newInstance();
            Method[] methods = cls.getDeclaredMethods();
            Arrays.sort(methods, (a,b)->a.getName().compareTo(b.getName()));
            for (Method m : methods) {
                Repeat repeatAnn = m.getAnnotation(Repeat.class);
                if (repeatAnn == null) continue;
                int times = repeatAnn.value();
                int mod = m.getModifiers();
                if (!Modifier.isProtected(mod) && !Modifier.isPrivate(mod)) continue;
                out.appendText("Invoking " + m.getName() + " " + times + " time(s)\n");
                m.setAccessible(true);
                Class<?>[] ptypes = m.getParameterTypes();
                Object[] args = new Object[ptypes.length];
                for (int i=0;i<ptypes.length;i++) args[i]=defaultValueFor(ptypes[i]);
                for (int t=0;t<times;t++) {
                    try {
                        Object r = m.invoke(target, args);
                        if (r!=null) out.appendText(" -> returned: " + r + "\n");
                    } catch (InvocationTargetException ite) {
                        out.appendText(" -> exception: " + ite.getTargetException() + "\n");
                    } catch (Exception e) {
                        out.appendText(" -> fail: " + e + "\n");
                    }
                }
                out.appendText("\n");
            }
        } catch (ClassNotFoundException e) {
            out.appendText("HeroMethods class not found. Put HeroMethods submodule in project.\n");
        } catch (Exception e) {
            out.appendText("Error: " + e + "\n");
        }
    }

    private static Object defaultValueFor(Class<?> type) {
        if (type.isPrimitive()) {
            if (type==boolean.class) return false;
            if (type==byte.class) return (byte)0;
            if (type==short.class) return (short)0;
            if (type==int.class) return 0;
            if (type==long.class) return 0L;
            if (type==float.class) return 0.0f;
            if (type==double.class) return 0.0d;
            if (type==char.class) return '\0';
            return 0;
        }
        if (type==String.class) return "";
        if (type.isArray()) return java.lang.reflect.Array.newInstance(type.getComponentType(), 0);
        if (type.isEnum()) {
            Object[] c = type.getEnumConstants();
            return c!=null && c.length>0 ? c[0] : null;
        }
        try {
            Constructor<?> ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) { return null; }
    }
}

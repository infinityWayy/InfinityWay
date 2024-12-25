package huix.infinity.util;

public class ReflectHelper {
    public static <T> T dyCast(Object from){
        return ((T) from);
    }

    public static <T> T dyCast(Class<T> to, Object from){
        return ((T) from);
    }
}

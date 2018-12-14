/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hy.netty.util;

import com.hy.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        }
        return ReflectionUtils.simpleClassName(o.getClass());
    }

    public static String simpleClassName(Class<?> clazz) {
        if (clazz == null) {
            return "null_class";
        }
        Package pkg = clazz.getPackage();
        if (pkg != null) {
            return clazz.getName().substring(pkg.getName().length() + 1);
        }
        return clazz.getName();
    }

    public static void setStaticFinalValue(Class clazz, String fieldName, Object newValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            ReflectionUtils.clearFieldFinalModifier(field);
            ReflectionUtils.makeAccessible(field).set(null, newValue);
            ReflectionUtils.setFieldFinalModifier(field);
        }
        catch (Exception e) {
            PlatformDependent.throwException(e);
        }
    }

    public static Object getStaticValue(Class clazz, String fieldName) {
        Object value = null;
        try {
            value = ReflectionUtils.makeAccessible(clazz.getDeclaredField(fieldName)).get(null);
        }
        catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        return value;
    }

    public static void setFinalValue(Object object, String fieldName, Object newValue) {
        ReflectionUtils.setValue(object, fieldName, newValue);
    }

    public static void setValue(Object object, String fieldName, Object newValue) {
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        try {
            ReflectionUtils.makeAccessible(field).set(object, newValue);
        }
        catch (IllegalAccessException e) {
            PlatformDependent.throwException(e);
        }
    }

    public static Object getValue(Object object, String fieldName) {
        Object value = null;
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        try {
            value = ReflectionUtils.makeAccessible(field).get(object);
        }
        catch (IllegalAccessException e) {
            PlatformDependent.throwException(e);
        }
        return value;
    }

    public static Object invokeMethod(Object object, String methodName) throws InvocationTargetException {
        Object ret = null;
        Method method = ReflectionUtils.getDeclaredMethod(object, methodName, new Class[0]);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        try {
            ret = ReflectionUtils.makeAccessible(method).invoke(object, new Object[0]);
        }
        catch (IllegalAccessException e) {
            PlatformDependent.throwException(e);
        }
        return ret;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws InvocationTargetException {
        Object ret = null;
        Method method = ReflectionUtils.getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        try {
            ret = ReflectionUtils.makeAccessible(method).invoke(object, parameters);
        }
        catch (IllegalAccessException e) {
            PlatformDependent.throwException(e);
        }
        return ret;
    }

    public static <T> Class<T> getSuperClassGenericType(Class<?> clazz) {
        return (Class<T>) ReflectionUtils.getSuperClassGenericType(clazz, 0);
    }

    public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            LOG.warn("{}'s superclass not ParameterizedType.", (Object)clazz.getSimpleName());
            return Object.class;
        }
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            LOG.warn("Index:{}, size of {}'s ParameterizedType:{}.", new Object[]{index, clazz.getSimpleName(), params.length});
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            LOG.warn("{} not set the actual class on superclass generic parameter.", (Object)clazz.getSimpleName());
            return Object.class;
        }
        return (Class)params[index];
    }

    public static Type[] getSuperClassGenericTypeArray(Class<?> clazz) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            LOG.warn("{}'s superclass not ParameterizedType.", (Object)clazz.getSimpleName());
            return new Type[]{Object.class};
        }
        return ((ParameterizedType)genType).getActualTypeArguments();
    }

    public static Class<?> getMethodGenericReturnType(Method method) {
        return ReflectionUtils.getMethodGenericReturnType(method, 0);
    }

    public static Class<?> getMethodGenericReturnType(Method method, int index) {
        Type genType = method.getGenericReturnType();
        if (!(genType instanceof ParameterizedType)) {
            LOG.warn("{}'s method not ParameterizedType.", (Object)method.getName());
            return Object.class;
        }
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if (index < 0 || index >= params.length) {
            LOG.warn("Index:{}, size of {}'s ParameterizedType:{}.", new Object[]{index, method.getName(), params.length});
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            LOG.warn("{} not set the actual class on method generic parameter.", (Object)method.getName());
            return Object.class;
        }
        return (Class)params[index];
    }

    public static List<Class<?>> getMethodGenericParameterTypes(Method method) {
        return ReflectionUtils.getMethodGenericParameterTypes(method, 0);
    }

    public static List<Class<?>> getMethodGenericParameterTypes(Method method, int index) {
        Type[] genTypes = method.getGenericParameterTypes();
        if (index < 0 || index >= genTypes.length) {
            LOG.warn("Index:{}, size of {}'s ParameterizedType:{}.", new Object[]{index, method.getName(), genTypes.length});
            return Collections.emptyList();
        }
        Type genType = genTypes[index];
        if (!(genType instanceof ParameterizedType)) {
            LOG.warn("{}'s method not ParameterizedType.", (Object)method.getName());
            return Collections.emptyList();
        }
        Type[] paramArgTypes = ((ParameterizedType)genType).getActualTypeArguments();
        ArrayList results = new ArrayList();
        for (Type paramArgType : paramArgTypes) {
            if (paramArgType instanceof Class) {
                results.add((Class)paramArgType);
                continue;
            }
            LOG.warn("{} not set the actual class on method generic parameter.", (Object)method.getName());
        }
        return results;
    }

    public static Class<?> getFieldGenericType(Field field) {
        return ReflectionUtils.getFieldGenericType(field, 0);
    }

    public static Class<?> getFieldGenericType(Field field, int index) {
        Type genType = field.getGenericType();
        if (!(genType instanceof ParameterizedType)) {
            LOG.warn("{}'s field not ParameterizedType.", (Object)field.getName());
            return Object.class;
        }
        Type[] fieldArgTypes = ((ParameterizedType)genType).getActualTypeArguments();
        if (index < 0 || index >= fieldArgTypes.length) {
            LOG.warn("Index:{}, size of {}'s ParameterizedType:{}.", new Object[]{index, field.getName(), fieldArgTypes.length});
            return Object.class;
        }
        if (!(fieldArgTypes[index] instanceof Class)) {
            LOG.warn("{} not set the actual class on field generic parameter.", (Object)field.getName());
            return Object.class;
        }
        return (Class)fieldArgTypes[index];
    }

    public static String[] getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            Parameter param = parameters[i];
            if (!param.isNamePresent()) {
                return null;
            }
            parameterNames[i] = param.getName();
        }
        return parameterNames;
    }

    public static String[] getParameterNames(Constructor<?> ctor) {
        Parameter[] parameters = ctor.getParameters();
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            Parameter param = parameters[i];
            if (!param.isNamePresent()) {
                return null;
            }
            parameterNames[i] = param.getName();
        }
        return parameterNames;
    }

    protected static Field clearFieldFinalModifier(Field field) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            ReflectionUtils.makeAccessible(modifiersField).setInt(field, field.getModifiers() & -17);
            field = ReflectionUtils.clearFieldAccessor(field);
        }
        catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        return field;
    }

    protected static Field setFieldFinalModifier(Field field) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            ReflectionUtils.makeAccessible(modifiersField).setInt(field, field.getModifiers() | 16);
            field = ReflectionUtils.clearFieldAccessor(field);
        }
        catch (Exception e) {
            PlatformDependent.throwException(e);
        }
        return field;
    }

    protected static Field clearFieldAccessor(Field field) {
        Object root;
        if (ReflectionUtils.getValue(field, "overrideFieldAccessor") != null) {
            ReflectionUtils.setValue(field, "overrideFieldAccessor", null);
        }
        if (ReflectionUtils.getValue(field, "fieldAccessor") != null) {
            ReflectionUtils.setValue(field, "fieldAccessor", null);
        }
        if ((root = ReflectionUtils.getValue(field, "root")) != null) {
            if (ReflectionUtils.getValue(root, "overrideFieldAccessor") != null) {
                ReflectionUtils.setValue(root, "overrideFieldAccessor", null);
            }
            if (ReflectionUtils.getValue(root, "fieldAccessor") != null) {
                ReflectionUtils.setValue(root, "fieldAccessor", null);
            }
        }
        return field;
    }

    protected static Field makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
        return field;
    }

    protected static Method makeAccessible(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method;
    }

    protected static Field getDeclaredField(Object object, String fieldName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                continue;
            }
        }
        return null;
    }

    protected static /* varargs */ Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                continue;
            }
        }
        return null;
    }
}


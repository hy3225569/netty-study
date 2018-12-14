/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hy.netty.util.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

final class PlatformDependent0 {
    private static final Logger LOG;
    private static final Unsafe UNSAFE;
    private static final boolean BIG_ENDIAN;
    private static final boolean UNALIGNED;
    private static final boolean CAN_USE_NATIVE_EPOLL;
    static final long ARRAY_BASE_OFFSET;

    PlatformDependent0() {
    }

    static boolean canUseNativeEpoll() {
        return CAN_USE_NATIVE_EPOLL;
    }

    static boolean hasUnsafe() {
        return UNSAFE != null;
    }

    static void throwException(Throwable t) {
        UNSAFE.throwException(t);
    }

    static long arrayBaseOffset() {
        return UNSAFE.arrayBaseOffset(byte[].class);
    }

    static Object getObject(Object object, long fieldOffset) {
        return UNSAFE.getObject(object, fieldOffset);
    }

    static Object getObjectVolatile(Object object, long fieldOffset) {
        return UNSAFE.getObjectVolatile(object, fieldOffset);
    }

    static int getInt(Object object, long fieldOffset) {
        return UNSAFE.getInt(object, fieldOffset);
    }

    static long getLong(Object object, long fieldOffset) {
        return UNSAFE.getLong(object, fieldOffset);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static byte getByte(long address) {
        return UNSAFE.getByte(address);
    }

    static short getShort(long address) {
        if (UNALIGNED) {
            return UNSAFE.getShort(address);
        }
        if (BIG_ENDIAN) {
            return (short)(PlatformDependent0.getByte(address) << 8 | PlatformDependent0.getByte(address + 1L) & 255);
        }
        return (short)(PlatformDependent0.getByte(address + 1L) << 8 | PlatformDependent0.getByte(address) & 255);
    }

    static int getInt(long address) {
        if (UNALIGNED) {
            return UNSAFE.getInt(address);
        }
        if (BIG_ENDIAN) {
            return PlatformDependent0.getByte(address) << 24 | (PlatformDependent0.getByte(address + 1L) & 255) << 16 | (PlatformDependent0.getByte(address + 2L) & 255) << 8 | PlatformDependent0.getByte(address + 3L) & 255;
        }
        return PlatformDependent0.getByte(address + 3L) << 24 | (PlatformDependent0.getByte(address + 2L) & 255) << 16 | (PlatformDependent0.getByte(address + 1L) & 255) << 8 | PlatformDependent0.getByte(address) & 255;
    }

    static long getLong(long address) {
        if (UNALIGNED) {
            return UNSAFE.getLong(address);
        }
        if (BIG_ENDIAN) {
            return (long)PlatformDependent0.getByte(address) << 56 | ((long)PlatformDependent0.getByte(address + 1L) & 255L) << 48 | ((long)PlatformDependent0.getByte(address + 2L) & 255L) << 40 | ((long)PlatformDependent0.getByte(address + 3L) & 255L) << 32 | ((long)PlatformDependent0.getByte(address + 4L) & 255L) << 24 | ((long)PlatformDependent0.getByte(address + 5L) & 255L) << 16 | ((long)PlatformDependent0.getByte(address + 6L) & 255L) << 8 | (long)PlatformDependent0.getByte(address + 7L) & 255L;
        }
        return (long)PlatformDependent0.getByte(address + 7L) << 56 | ((long)PlatformDependent0.getByte(address + 6L) & 255L) << 48 | ((long)PlatformDependent0.getByte(address + 5L) & 255L) << 40 | ((long)PlatformDependent0.getByte(address + 4L) & 255L) << 32 | ((long)PlatformDependent0.getByte(address + 3L) & 255L) << 24 | ((long)PlatformDependent0.getByte(address + 2L) & 255L) << 16 | ((long)PlatformDependent0.getByte(address + 1L) & 255L) << 8 | (long)PlatformDependent0.getByte(address) & 255L;
    }

    static void putByte(long address, byte value) {
        UNSAFE.putByte(address, value);
    }

    static void putOrderedObject(Object object, long address, Object value) {
        UNSAFE.putOrderedObject(object, address, value);
    }

    static void putShort(long address, short value) {
        if (UNALIGNED) {
            UNSAFE.putShort(address, value);
        } else if (BIG_ENDIAN) {
            PlatformDependent0.putByte(address, (byte)(value >>> 8));
            PlatformDependent0.putByte(address + 1L, (byte)value);
        } else {
            PlatformDependent0.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent0.putByte(address, (byte)value);
        }
    }

    static void putInt(long address, int value) {
        if (UNALIGNED) {
            UNSAFE.putInt(address, value);
        } else if (BIG_ENDIAN) {
            PlatformDependent0.putByte(address, (byte)(value >>> 24));
            PlatformDependent0.putByte(address + 1L, (byte)(value >>> 16));
            PlatformDependent0.putByte(address + 2L, (byte)(value >>> 8));
            PlatformDependent0.putByte(address + 3L, (byte)value);
        } else {
            PlatformDependent0.putByte(address + 3L, (byte)(value >>> 24));
            PlatformDependent0.putByte(address + 2L, (byte)(value >>> 16));
            PlatformDependent0.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent0.putByte(address, (byte)value);
        }
    }

    static void putLong(long address, long value) {
        if (UNALIGNED) {
            UNSAFE.putLong(address, value);
        } else if (BIG_ENDIAN) {
            PlatformDependent0.putByte(address, (byte)(value >>> 56));
            PlatformDependent0.putByte(address + 1L, (byte)(value >>> 48));
            PlatformDependent0.putByte(address + 2L, (byte)(value >>> 40));
            PlatformDependent0.putByte(address + 3L, (byte)(value >>> 32));
            PlatformDependent0.putByte(address + 4L, (byte)(value >>> 24));
            PlatformDependent0.putByte(address + 5L, (byte)(value >>> 16));
            PlatformDependent0.putByte(address + 6L, (byte)(value >>> 8));
            PlatformDependent0.putByte(address + 7L, (byte)value);
        } else {
            PlatformDependent0.putByte(address + 7L, (byte)(value >>> 56));
            PlatformDependent0.putByte(address + 6L, (byte)(value >>> 48));
            PlatformDependent0.putByte(address + 5L, (byte)(value >>> 40));
            PlatformDependent0.putByte(address + 4L, (byte)(value >>> 32));
            PlatformDependent0.putByte(address + 3L, (byte)(value >>> 24));
            PlatformDependent0.putByte(address + 2L, (byte)(value >>> 16));
            PlatformDependent0.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent0.putByte(address, (byte)value);
        }
    }

    static void copyMemory(long srcAddr, long dstAddr, long length) {
        UNSAFE.copyMemory(srcAddr, dstAddr, length);
    }

    static void copyMemory(Object src, long srcOffset, Object dst, long dstOffset, long length) {
        UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, length);
    }

    static long sizeOf(Object o) {
        HashSet<Field> fields = new HashSet<Field>();
        for (Class<?> c = o.getClass(); c != Object.class; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & 8) != 0) continue;
                fields.add(f);
            }
        }
        long maxSize = 0L;
        for (Field f : fields) {
            long offset = UNSAFE.objectFieldOffset(f);
            if (offset <= maxSize) continue;
            maxSize = offset;
        }
        return (maxSize / 8L + 1L) * 8L;
    }

    static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> tClass, String fieldName) throws Exception {
        return new UnsafeAtomicReferenceFieldUpdater(UNSAFE, tClass, fieldName);
    }

    static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<?> tClass, String fieldName) throws Exception {
        return new UnsafeAtomicIntegerFieldUpdater(UNSAFE, tClass, fieldName);
    }

    static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<?> tClass, String fieldName) throws Exception {
        return new UnsafeAtomicLongFieldUpdater(UNSAFE, tClass, fieldName);
    }

    static int getObjectAddress_32(Object o) {
        return UNSAFE.getInt((Object)new Object[]{o}, ARRAY_BASE_OFFSET);
    }

    static long getObjectAddress_64(Object o) {
        return UNSAFE.getLong((Object)new Object[]{o}, ARRAY_BASE_OFFSET);
    }

    static long normalize(int value) {
        if (value >= 0) {
            return value;
        }
        return 0xFFFFFFFFL & (long)value;
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            return AccessController.doPrivileged((PrivilegedAction<Unsafe>) () -> {
                Class<Unsafe> k = Unsafe.class;
                for (Field f : k.getDeclaredFields()) {
                    f.setAccessible(true);
                    Object x = null;
                    try {
                        x = f.get(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (!k.isInstance(x)) continue;
                    return (Unsafe)k.cast(x);
                }
                throw new NoSuchFieldError("the Unsafe");
            });
        }
    }

    static {
        boolean epoll;
        LOG = LoggerFactory.getLogger(PlatformDependent0.class);
        BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        try {
            Class.forName("io.netty.channel.epoll.Native");
            epoll = true;
        }
        catch (Throwable e) {
            epoll = false;
        }
        CAN_USE_NATIVE_EPOLL = epoll;
        Unsafe unsafe = null;
        try {
            unsafe = PlatformDependent0.getUnsafe();
        }
        catch (Exception exception) {
            // empty catch block
        }
        UNSAFE = unsafe;
        ARRAY_BASE_OFFSET = UNSAFE != null ? (long)UNSAFE.arrayBaseOffset(Object[].class) : 0L;
        if (unsafe == null) {
            UNALIGNED = false;
        } else {
            boolean unaligned;
            try {
                Class<?> bitsClass = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader());
                Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned", new Class[0]);
                unalignedMethod.setAccessible(true);
                unaligned = Boolean.TRUE.equals(unalignedMethod.invoke(null, new Object[0]));
            }
            catch (Throwable t) {
                String arch = System.getProperty("os.arch", "");
                unaligned = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
            }
            UNALIGNED = unaligned;
            LOG.debug("java.nio.Bits.unaligned:{}.", (Object)unaligned);
        }
    }
}


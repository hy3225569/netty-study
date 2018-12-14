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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PlatformDependent {
    private static final Logger LOG = LoggerFactory.getLogger(PlatformDependent.class);
    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
    private static final boolean IS_ANDROID = PlatformDependent.isAndroid0();
    private static final boolean IS_WINDOWS = PlatformDependent.isWindows0();
    private static final boolean IS_ROOT = PlatformDependent.isRoot0();
    private static final int JAVA_VERSION = PlatformDependent.javaVersion0();
    private static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe0();
    private static final long MAX_DIRECT_MEMORY = PlatformDependent.maxDirectMemory0();

    public static int javaVersion() {
        return JAVA_VERSION;
    }

    public static boolean hasUnsafe() {
        return HAS_UNSAFE;
    }

    public static long maxDirectMemory() {
        return MAX_DIRECT_MEMORY;
    }

    public static boolean isAndroid() {
        return IS_ANDROID;
    }

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    public static boolean isRoot() {
        return IS_ROOT;
    }

    public static boolean canUseNativeEpoll() {
        return PlatformDependent0.canUseNativeEpoll();
    }

    public static void throwException(Throwable t) {
        if (PlatformDependent.hasUnsafe()) {
            PlatformDependent0.throwException(t);
        } else {
            try {
                PlatformDependent.throwException0(t);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static long sizeOf(Object o) {
        return PlatformDependent0.sizeOf(o);
    }

    public static Object getObjectVolatile(Object object, long fieldOffset) {
        return PlatformDependent0.getObjectVolatile(object, fieldOffset);
    }

    public static Object getObject(Object object, long fieldOffset) {
        return PlatformDependent0.getObject(object, fieldOffset);
    }

    public static int getInt(Object object, long fieldOffset) {
        return PlatformDependent0.getInt(object, fieldOffset);
    }

    public static long getLong(Object object, long fieldOffset) {
        return PlatformDependent0.getLong(object, fieldOffset);
    }

    public static long objectFieldOffset(Field field) {
        return PlatformDependent0.objectFieldOffset(field);
    }

    public static byte getByte(long address) {
        return PlatformDependent0.getByte(address);
    }

    public static short getShort(long address) {
        return PlatformDependent0.getShort(address);
    }

    public static int getInt(long address) {
        return PlatformDependent0.getInt(address);
    }

    public static long getLong(long address) {
        return PlatformDependent0.getLong(address);
    }

    public static void putByte(long address, byte value) {
        PlatformDependent0.putByte(address, value);
    }

    public static void putOrderedObject(Object object, long address, Object value) {
        PlatformDependent0.putOrderedObject(object, address, value);
    }

    public static void putShort(long address, short value) {
        PlatformDependent0.putShort(address, value);
    }

    public static void putInt(long address, int value) {
        PlatformDependent0.putInt(address, value);
    }

    public static void putLong(long address, long value) {
        PlatformDependent0.putLong(address, value);
    }

    public static void copyMemory(long srcAddr, long dstAddr, long length) {
        PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
    }

    public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
        PlatformDependent0.copyMemory(src, PlatformDependent0.ARRAY_BASE_OFFSET + (long)srcIndex, null, dstAddr, length);
    }

    public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
        PlatformDependent0.copyMemory(null, srcAddr, dst, PlatformDependent0.ARRAY_BASE_OFFSET + (long)dstIndex, length);
    }

    public static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> tClass, String fieldName) {
        if (PlatformDependent.hasUnsafe()) {
            try {
                return PlatformDependent0.newAtomicReferenceFieldUpdater(tClass, fieldName);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    public static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<?> tClass, String fieldName) {
        if (PlatformDependent.hasUnsafe()) {
            try {
                return PlatformDependent0.newAtomicIntegerFieldUpdater(tClass, fieldName);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    public static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<?> tClass, String fieldName) {
        if (PlatformDependent.hasUnsafe()) {
            try {
                return PlatformDependent0.newAtomicLongFieldUpdater(tClass, fieldName);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    public static int getObjectAddress_32(Object o) {
        return PlatformDependent0.getObjectAddress_32(o);
    }

    public static long getObjectAddress_64(Object o) {
        return PlatformDependent0.getObjectAddress_64(o);
    }

    private static long maxDirectMemory0() {
        long maxDirectMemory;
        maxDirectMemory = 0L;
        try {
            Class<?> vmClass = Class.forName("sun.misc.VM", true, ClassLoader.getSystemClassLoader());
            Method m = vmClass.getDeclaredMethod("maxDirectMemory", new Class[0]);
            maxDirectMemory = ((Number)m.invoke(null, new Object[0])).longValue();
        }
        catch (Throwable vmClass) {
            // empty catch block
        }
        if (maxDirectMemory > 0L) {
            return maxDirectMemory;
        }
        try {
            Class<?> mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, ClassLoader.getSystemClassLoader());
            Class<?> runtimeClass = Class.forName("java.lang.management.RuntimeMXBean", true, ClassLoader.getSystemClassLoader());
            Object runtime = mgmtFactoryClass.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke(null, new Object[0]);
            List vmArgs = (List)runtimeClass.getDeclaredMethod("getInputArguments", new Class[0]).invoke(runtime, new Object[0]);
            for (int i = vmArgs.size() - 1; i >= 0; --i) {
                Matcher m = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher((CharSequence)vmArgs.get(i));
                if (!m.matches()) continue;
                maxDirectMemory = Long.parseLong(m.group(1));
                switch (m.group(2).charAt(0)) {
                    case 'K': 
                    case 'k': {
                        maxDirectMemory *= 1024L;
                        break;
                    }
                    case 'M': 
                    case 'm': {
                        maxDirectMemory *= 0x100000L;
                        break;
                    }
                    case 'G': 
                    case 'g': {
                        maxDirectMemory *= 0x40000000L;
                    }
                }
                break;
            }
        }
        catch (Throwable mgmtFactoryClass) {
            // empty catch block
        }
        if (maxDirectMemory <= 0L) {
            maxDirectMemory = Runtime.getRuntime().maxMemory();
            LOG.debug("maxDirectMemory: {} bytes (maybe).", (Object)maxDirectMemory);
        } else {
            LOG.debug("maxDirectMemory: {} bytes.", (Object)maxDirectMemory);
        }
        return maxDirectMemory;
    }

    private static <E extends Throwable> void throwException0(Throwable t) throws Throwable {
        throw t;
    }

    private static boolean isAndroid0() {
        boolean android;
        try {
            Class.forName("android.app.Application", false, ClassLoader.getSystemClassLoader());
            android = true;
        }
        catch (Exception e) {
            android = false;
        }
        if (android) {
            LOG.debug("Platform: Android.");
        }
        return android;
    }

    private static boolean isWindows0() {
        boolean windows = System.getProperty("os.name", "").toLowerCase(Locale.US).contains("win");
        if (windows) {
            LOG.debug("Platform: Windows.");
        }
        return windows;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean isRoot0() {
        if (PlatformDependent.isWindows()) {
            return false;
        }
        String[] ID_COMMANDS = new String[]{"/usr/bin/id", "/bin/id", "id", "/usr/xpg4/bin/id"};
        Pattern UID_PATTERN = Pattern.compile("^(?:0|[1-9][0-9]*)$");
        for (String idCmd : ID_COMMANDS) {
            String uid;
            Process p = null;
            BufferedReader in = null;
            uid = null;
            try {
                p = Runtime.getRuntime().exec(new String[]{idCmd, "-u"});
                in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("US-ASCII")));
                uid = in.readLine();
                do {
                    try {
                        int exitCode = p.waitFor();
                        if (exitCode != 0) {
                            uid = null;
                        }
                    }
                    catch (InterruptedException exitCode) {
                        continue;
                    }
                    break;
                } while (true);
            }
            catch (Exception e) {
                uid = null;
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException e) {}
                }
                if (p != null) {
                    try {
                        p.destroy();
                    }
                    catch (Exception e) {}
                }
            }
            if (uid == null || !UID_PATTERN.matcher(uid).matches()) continue;
            LOG.debug("UID: {}.", uid);
            return "0".equals(uid);
        }
        LOG.debug("Could not determine the current UID using /usr/bin/id; attempting to bind at privileged ports.");
        Pattern PERMISSION_DENIED = Pattern.compile(".*(?:denied|not.*permitted).*");
        for (int i = 1023; i > 0; --i) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket();
                ss.setReuseAddress(true);
                ss.bind(new InetSocketAddress(i));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("UID: 0 (succeeded to bind at port {}).", (Object)i);
                }
                boolean idCmd = true;
                return idCmd;
            }
            catch (Exception e) {
                String message = e.getMessage();
                if (message == null) {
                    message = "";
                }
                if (!PERMISSION_DENIED.matcher(message = message.toLowerCase()).matches()) continue;
                break;
            }
            finally {
                if (ss != null) {
                    try {
                        ss.close();
                    }
                    catch (Exception in) {}
                }
            }
        }
        LOG.debug("UID: non-root (failed to bind at any privileged ports).");
        return false;
    }

    private static int javaVersion0() {
        int javaVersion;
        try {
            Class.forName("java.time.Clock", false, Object.class.getClassLoader());
            javaVersion = 8;
        }
        catch (Exception exception) {
            try {
                Class.forName("java.util.concurrent.LinkedTransferQueue", false, BlockingQueue.class.getClassLoader());
                javaVersion = 7;
            }
            catch (Exception exception2) {
                javaVersion = 6;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Java version: {}.", (Object)javaVersion);
        }
        return javaVersion;
    }

    private static boolean hasUnsafe0() {
        return PlatformDependent0.hasUnsafe();
    }

    private PlatformDependent() {
    }
}


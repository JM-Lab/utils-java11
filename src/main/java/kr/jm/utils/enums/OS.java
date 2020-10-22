package kr.jm.utils.enums;

import kr.jm.utils.JMString;
import kr.jm.utils.exception.JMException;
import kr.jm.utils.helper.JMLog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * The enum Os.
 */
public enum OS {

    /**
     * Windows os.
     */
    WINDOWS,
    /**
     * Mac os.
     */
    MAC,
    /**
     * Linux os.
     */
    LINUX;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OS.class);

    /**
     * Gets file separator.
     *
     * @return the file separator
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * Gets line separator.
     *
     * @return the line separator
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * Gets os name.
     *
     * @return the os name
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets os version.
     *
     * @return the os version
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Gets user working dir.
     *
     * @return the user working dir
     */
    public static String getUserWorkingDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets user home dir.
     *
     * @return the user home dir
     */
    public static String getUserHomeDir() {
        return System.getProperty("user.home");
    }

    /**
     * Gets java io tmp dir.
     *
     * @return the java io tmp dir
     */
    public static String getJavaIoTmpDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Gets os.
     *
     * @return the os
     */
    public static OS getOS() {
        String os = getOsName().toLowerCase();
        if (os.contains("windows"))
            return WINDOWS;
        else if (os.contains("mac"))
            return MAC;
        else
            return LINUX;
    }

    /**
     * Add shutdown hook.
     *
     * @param runAfterShutdown the run after shutdown
     */
    public static void addShutdownHook(Runnable runAfterShutdown) {
        Runtime.getRuntime().addShutdownHook(new Thread(runAfterShutdown));
    }

    /**
     * Gets hostname.
     *
     * @return the hostname
     */
    public static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getHostname");
        }

    }

    /**
     * Gets ip.
     *
     * @return the ip
     */
    public static String getIp() {
        return Optional.ofNullable(getIpInfo()).map(InetAddress::getHostAddress).orElse(null);
    }

    /**
     * Gets ip list.
     *
     * @return the ip list
     */
    public static List<String> getIpList() {
        return getIpInfoList().stream().map(InetAddress::getHostAddress).collect(toList());
    }

    /**
     * Gets ip info.
     *
     * @return the ip info
     */
    public static InetAddress getIpInfo() {
        return getRealInetAddressInfoStream().findFirst().orElse(null);
    }

    /**
     * Gets real inet address info stream.
     *
     * @return the real inet address info stream
     */
    public static Stream<InetAddress> getRealInetAddressInfoStream() {
        return getAllInetAddressInfoStream().filter(InetAddress::isSiteLocalAddress)
                .filter(inetAddress -> !inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress());
    }

    /**
     * Gets ip info list.
     *
     * @return the ip info list
     */
    public static List<InetAddress> getIpInfoList() {
        return getRealInetAddressInfoStream().filter(OS::isIpv4Address).collect(toList());
    }

    private static boolean isIpv4Address(InetAddress inetAddress) {
        return inetAddress.getHostAddress().matches(JMString.IPV4Pattern);
    }

    /**
     * Gets all inet address info list.
     *
     * @return the all inet address info list
     */
    public static List<InetAddress> getAllInetAddressInfoList() {
        return getAllInetAddressInfoStream().collect(toList());
    }

    /**
     * Gets all inet address info stream.
     *
     * @return the all inet address info stream
     */
    public static Stream<InetAddress> getAllInetAddressInfoStream() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .flatMap(nic -> Collections.list(nic.getInetAddresses()).stream());
        } catch (SocketException e) {
            return JMException.handleExceptionAndReturnNull(log, e, "getAllInetAddressInfoStream");
        }
    }

    /**
     * Gets available local port.
     *
     * @return the available local port
     */
    public static int getAvailableLocalPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            return JMException.handleExceptionAndReturn(log, e,
                    "getAvailableLocalPort", () -> -1);
        }
    }

    /**
     * Gets available processors.
     *
     * @return the available processors
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Open boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean open(File file) {
        try {
            Desktop.getDesktop().open(file);
            return true;
        } catch (Exception e) {
            return openAlternatively(file);
        }
    }

    private static boolean openAlternatively(File file) {
        if (getOS() == OS.WINDOWS)
            return open("cmd /c ", file);
        return open("open ", file);
    }

    private static boolean open(String runCmd, File file) {
        try {
            String command = runCmd + file.getAbsolutePath();
            JMLog.info(log, "open", command);
            Runtime.getRuntime().exec(command);
            return true;
        } catch (Exception e) {
            return JMException.handleExceptionAndReturnFalse(log, e,
                    "open", runCmd, file.getAbsolutePath());
        }
    }

}

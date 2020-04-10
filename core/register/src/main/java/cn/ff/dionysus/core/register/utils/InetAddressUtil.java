package cn.ff.dionysus.core.register.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.commons.util.InetUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * _
 *
 * @author fengfan 2020/3/20
 */
public class InetAddressUtil {



    private static final Logger log = LoggerFactory.getLogger(InetUtils.class);
    private static String selfIp;
    private static boolean useOnlySiteLocalInterface = false;
    private static boolean preferHostnameOverIp = false;
    private static List<String> preferredNetworks = new ArrayList();
    private static List<String> ignoredInterfaces = new ArrayList();

    public InetAddressUtil() {
    }

    public static String getSelfIp() {
        return selfIp;
    }

    public static InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;

        try {
            int lowest = 2147483647;
            Enumeration nics = NetworkInterface.getNetworkInterfaces();

            label61:
            while(true) {
                NetworkInterface ifc;
                do {
                    while(true) {
                        do {
                            if (!nics.hasMoreElements()) {
                                break label61;
                            }

                            ifc = (NetworkInterface)nics.nextElement();
                        } while(!ifc.isUp());

                        log.info("Testing interface: " + ifc.getDisplayName());
                        if (ifc.getIndex() >= lowest && result != null) {
                            if (result != null) {
                                continue;
                            }
                            break;
                        }

                        lowest = ifc.getIndex();
                        break;
                    }
                } while(ignoreInterface(ifc.getDisplayName()));

                Enumeration addrs = ifc.getInetAddresses();

                while(addrs.hasMoreElements()) {
                    InetAddress address = (InetAddress)addrs.nextElement();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress() && isPreferredAddress(address)) {
                        log.info("Found non-loopback interface: " + ifc.getDisplayName());
                        result = address;
                    }
                }
            }
        } catch (IOException var7) {
            log.error("Cannot get first non-loopback address", var7);
        }

        if (result != null) {
            return result;
        } else {
            try {
                return InetAddress.getLocalHost();
            } catch (UnknownHostException var6) {
                log.warn("Unable to retrieve localhost");
                return null;
            }
        }
    }

    public static boolean isPreferredAddress(InetAddress address) {
        if (useOnlySiteLocalInterface) {
            boolean siteLocalAddress = address.isSiteLocalAddress();
            if (!siteLocalAddress) {
                log.info("Ignoring address: " + address.getHostAddress());
            }

            return siteLocalAddress;
        } else if (preferredNetworks.isEmpty()) {
            return true;
        } else {
            Iterator var1 = preferredNetworks.iterator();

            String regex;
            String hostAddress;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                regex = (String)var1.next();
                hostAddress = address.getHostAddress();
            } while(!hostAddress.matches(regex) && !hostAddress.startsWith(regex));

            return true;
        }
    }

    public static boolean ignoreInterface(String interfaceName) {
        Iterator var1 = ignoredInterfaces.iterator();

        String regex;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            regex = (String)var1.next();
        } while(!interfaceName.matches(regex));

        log.info("Ignoring interface: " + interfaceName);
        return true;
    }

    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    static {
        useOnlySiteLocalInterface = Boolean.valueOf(PropertyUtil.getProperty("nacos.inetutils.use-only-site-local-interfaces"));
        List<String> networks = PropertyUtil.getPropertyList("nacos.inetutils.preferred-networks");
        Iterator var1 = networks.iterator();

        String nacosIp;
        while(var1.hasNext()) {
            nacosIp = (String)var1.next();
            preferredNetworks.add(nacosIp);
        }

        List<String> interfaces = PropertyUtil.getPropertyList("nacos.inetutils.ignored-interfaces");
        Iterator var7 = interfaces.iterator();

        while(var7.hasNext()) {
            String ignored = (String)var7.next();
            ignoredInterfaces.add(ignored);
        }

        nacosIp = System.getProperty("nacos.server.ip");
        if (StringUtils.isBlank(nacosIp)) {
            nacosIp = PropertyUtil.getProperty("nacos.inetutils.ip-address");
        }

        if (!StringUtils.isBlank(nacosIp) && !isIP(nacosIp)) {
            throw new RuntimeException("nacos address " + nacosIp + " is not ip");
        } else {
            selfIp = nacosIp;
            if (StringUtils.isBlank(selfIp)) {
                preferHostnameOverIp = Boolean.getBoolean("nacos.preferHostnameOverIp");
                if (!preferHostnameOverIp) {
                    preferHostnameOverIp = Boolean.parseBoolean(PropertyUtil.getProperty("nacos.inetutils.prefer-hostname-over-ip"));
                }

                if (preferHostnameOverIp) {
                    InetAddress inetAddress = null;

                    try {
                        inetAddress = InetAddress.getLocalHost();
                    } catch (UnknownHostException var5) {
                    }

                    if (inetAddress.getHostName().equals(inetAddress.getCanonicalHostName())) {
                        selfIp = inetAddress.getHostName();
                    } else {
                        selfIp = inetAddress.getCanonicalHostName();
                    }
                } else {
                    selfIp = findFirstNonLoopbackAddress().getHostAddress();
                }
            }

        }
    }
}

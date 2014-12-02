package com.petzila.api.util;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by rylexr on 28/11/14.
 */
public final class Utils {
    private static final String IMAGE_BASE64_HEADER = "data:image/jpeg;base64,";

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(Utils.class.getResourceAsStream("/patt.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private Utils() {
    }

    public static String asBase64(String fileName) throws Exception {
        return IMAGE_BASE64_HEADER + Base64.encodeBase64String(Files.readAllBytes(Paths.get(Utils.class.getResource(fileName).toURI())));
    }

    public static byte[] asBinary(String fileName) throws Exception {
        return Files.readAllBytes(Paths.get(Utils.class.getResource(fileName).toURI()));
    }

    public static String asFilename(String resource) {
        return Utils.class.getResource(resource).getFile();
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }
}

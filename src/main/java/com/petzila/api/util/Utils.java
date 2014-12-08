package com.petzila.api.util;

import com.google.common.io.ByteStreams;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
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

    public static String asBase64(String resource) throws Exception {
        return IMAGE_BASE64_HEADER + Base64.encodeBase64String(asBinary(resource));
    }

    public static byte[] asBinary(String resource) throws Exception {
        return ByteStreams.toByteArray(Utils.class.getResourceAsStream(resource));
    }

    public static File asTempFile(String resource) throws Exception {
        File f = File.createTempFile(resource.substring(1, resource.indexOf('.')), resource.substring(resource.indexOf('.')));
        Files.write(Paths.get(f.toURI()), ByteStreams.toByteArray(Utils.class.getResourceAsStream(resource)));
        return f;
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }
}

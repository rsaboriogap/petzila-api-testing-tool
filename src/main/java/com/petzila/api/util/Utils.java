package com.petzila.api.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rylexr on 28/11/14.
 */
public final class Utils {
    private static final String IMAGE_BASE64_HEADER = "data:image/jpeg;base64,";

    private Utils() {
    }

    public static String asBase64(String fileName) throws Exception {
//        return Base64.encodeBase64String(Files.readAllBytes(Paths.get(Main.class.getResource(fileName).toURI())));
        return IMAGE_BASE64_HEADER + Base64.encodeBase64String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static byte[] asBinary(String fileName) throws Exception {
//        return Files.readAllBytes(Paths.get(Main.class.getResource(fileName).toURI()));
        return Files.readAllBytes(Paths.get(fileName));
    }

}

package com.pb.biser.conveyor.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by diver on 5/15/15.
 */
@Service
public class DebtPackService {

    public <T> T deserialize(String string) {
        byte[] decodedString = Base64.decodeBase64(string.getBytes());
        try(ByteArrayInputStream ba = new ByteArrayInputStream(decodedString);
            GZIPInputStream gz = new GZIPInputStream(ba);
            ObjectInputStream stream = new ObjectInputStream(gz)) {
            return (T) stream.readObject();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public <T> String serialize(T debtPack) {

        try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
             GZIPOutputStream gz = new GZIPOutputStream(bo);
             ObjectOutputStream so = new ObjectOutputStream(gz)) {
            so.writeObject(debtPack);
            so.flush();
            so.close();
            return Base64.encodeBase64String(bo.toByteArray());
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}

package cluster;

import java.io.*;

public class SerializationUtils {

    public static byte[] serialize(Object object) {
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput;
        try {
            objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(object);
            objectOutput.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            return new byte[]{};
        }
    }

    public static Object deserialize(byte[] data) {
        var byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInput objectInput;
        try {
            objectInput = new ObjectInputStream(byteArrayInputStream);
            return objectInput.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }
}

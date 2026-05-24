package cluster.serialization;

import java.io.*;

public class JavaSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T object) {
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

    @Override
    public <T> T deserialize(byte[] data) {
        var byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInput objectInput;
        try {
            objectInput = new ObjectInputStream(byteArrayInputStream);
            @SuppressWarnings("unchecked") T object = (T) objectInput.readObject();
            return object;
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

}

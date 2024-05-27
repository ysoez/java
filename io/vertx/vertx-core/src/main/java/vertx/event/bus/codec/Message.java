package vertx.event.bus.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public record Message(long timestamp, int statusCode, String content) {

    public static class Codec implements MessageCodec<Message, Message> {

        @Override
        public void encodeToWire(Buffer buffer, Message customMessage) {
            var jsonToEncode = new JsonObject();
            jsonToEncode.put("timestamp", customMessage.timestamp);
            jsonToEncode.put("statusCode", customMessage.statusCode);
            jsonToEncode.put("content", customMessage.content);
            String jsonString = jsonToEncode.encode();

            // ~ length of JSON (NOT characters count)
            int length = jsonString.getBytes().length;
            buffer.appendInt(length);
            buffer.appendString(jsonString);
        }

        @Override
        public Message decodeFromWire(int position, Buffer buffer) {
            // ~ message starting from this *position* of buffer
            int _pos = position;
            // ~ length of JSON
            int length = buffer.getInt(_pos);

            // ~ get JSON string by it`s length
            // ~ jump 4 because getInt() == 4 bytes
            String jsonStr = buffer.getString(_pos += 4, _pos += length);
            JsonObject jsonObject = new JsonObject(jsonStr);

            long timestamp = jsonObject.getLong("timestamp");
            int statusCode = jsonObject.getInteger("statusCode");
            String content = jsonObject.getString("content");

            return new Message(timestamp, statusCode, content);
        }

        @Override
        public Message transform(Message message) {
            // ~ if a message is sent *locally* across the event bus
            // ~ sends message just as is
            return message;
        }

        @Override
        public String name() {
            // ~ each codec must have a unique name.
            // ~ this is used to identify a codec when sending a message and for unregistering codecs.
            return this.getClass().getSimpleName();
        }

        @Override
        public byte systemCodecID() {
            // ~ always -1
            return -1;
        }
    }
}

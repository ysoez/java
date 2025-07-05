package proto;

import proto.generated.DayOfWeek;
import proto.generated.Request;

class ProtoBuilder {

    public static void main(String[] args) {
        var request = Request.newBuilder()
                .setContent("hello")
                .setWhen(DayOfWeek.MONDAY)
                .build();
        System.out.println(request);
    }

}

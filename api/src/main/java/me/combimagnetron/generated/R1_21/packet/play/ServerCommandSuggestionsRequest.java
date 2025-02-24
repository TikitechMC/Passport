// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import java.lang.Integer;
import java.lang.String;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ServerCommandSuggestionsRequest implements me.combimagnetron.passport.internal.network.packet.ServerPacket {
    private final static int ID = 0x0B;
    private final ByteBuffer byteBuffer;
    private final Integer transaction_id;
    private final String text;

    public static ServerCommandSuggestionsRequest commandSuggestionsRequest(Integer transaction_id, String text) {
        return new ServerCommandSuggestionsRequest(transaction_id, text);
    }

    private ServerCommandSuggestionsRequest(Integer transaction_id, String text) {
        this.byteBuffer = ByteBuffer.empty();
        this.transaction_id = transaction_id;
        this.text = text;
    }

    private ServerCommandSuggestionsRequest(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.transaction_id = byteBuffer.read(ByteBuffer.Adapter.VAR_INT);
        this.text = byteBuffer.read(ByteBuffer.Adapter.STRING);
    }

    public Integer transaction_id() {
        return transaction_id;
    }

    public String text() {
        return text;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, transaction_id);
        byteBuffer.write(ByteBuffer.Adapter.STRING, text);
        return byteBuffer.bytes();
    }
}
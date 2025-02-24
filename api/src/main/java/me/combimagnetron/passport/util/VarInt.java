package me.combimagnetron.passport.util;

import java.nio.ByteBuffer;

public class VarInt {
    private final int value;

    public static VarInt of(int value) {
        return new VarInt(value);
    }

    public static VarInt of(ByteBuffer input) {
        return new VarInt(input);
    }

    public VarInt(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public VarInt(ByteBuffer input) {
        int value = 0;
        int length = 0;
        byte currentByte;
        do {
            currentByte = input.get();
            value |= (currentByte & 0x7F) << (length * 7);
            length++;
            if (length > 5) {
                throw new RuntimeException("VarInt is too large. Must be smaller than 5 bytes.");
            }
        } while ((currentByte & 0x80) == 0x80);
        this.value = value;
    }

    public void write(ByteBuffer output) {
        if ((value & (0xFFFFFFFF << 7)) == 0) {
            output.put((byte) value);
        } else if ((value & (0xFFFFFFFF << 14)) == 0) {
            int w = (value & 0x7F | 0x80) << 8 | (value >>> 7);
            output.putShort((short) w);
        } else if ((value & (0xFFFFFFFF << 21)) == 0) {
            int w = (value & 0x7F | 0x80) << 16 | ((value >>> 7) & 0x7F | 0x80) << 8 | (value >>> 14);
            me.combimagnetron.passport.internal.network.ByteBuffer.Adapter.MEDIUM.write(output, w);
        } else if ((value & (0xFFFFFFFF << 28)) == 0) {
            int w = (value & 0x7F | 0x80) << 24 | (((value >>> 7) & 0x7F | 0x80) << 16)
                    | ((value >>> 14) & 0x7F | 0x80) << 8 | (value >>> 21);
            output.putInt(w);
        } else {
            int w = (value & 0x7F | 0x80) << 24 | ((value >>> 7) & 0x7F | 0x80) << 16
                    | ((value >>> 14) & 0x7F | 0x80) << 8 | ((value >>> 21) & 0x7F | 0x80);
            output.putInt(w);
            output.put((byte) ((byte) value >>> 28));
        }
    }

}

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
        int value = this.value;
        if (value < 0 || value >= (1 << 21)) {
            throw new IllegalArgumentException("Value out of range for 21-bit varint");
        }
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                output.put((byte) value);
                return;
            }

            output.put((byte) ((byte) value & 0x7F | 0x80));
            value >>>= 7;
        }
    }

}

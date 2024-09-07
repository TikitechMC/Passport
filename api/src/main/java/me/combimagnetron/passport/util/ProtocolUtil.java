package me.combimagnetron.passport.util;

import java.nio.ByteBuffer;

public class ProtocolUtil {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static int readVarInt(ByteBuffer input) {
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
        return value;
    }

    public static void writeVarInt(ByteBuffer output, int value) {
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

    public static long readVarLong(ByteBuffer input) {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = input.get();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public static void writeVarLong(ByteBuffer output, long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                output.put((byte) value);
                return;
            }

            output.put((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

}

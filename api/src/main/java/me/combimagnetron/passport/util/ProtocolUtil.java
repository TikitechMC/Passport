package me.combimagnetron.passport.util;

import java.nio.ByteBuffer;

public class ProtocolUtil {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

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

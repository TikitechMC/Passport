package me.combimagnetron.passport.internal.network.packet;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public interface Packet {

    ByteBuffer byteBuffer();

    byte[] write();

    default <T> T read(ByteBuffer.Adapter<T> type) {
        return byteBuffer().read(type);
    }

    default <T> Packet write(ByteBuffer.Adapter<T> type, T object) {
        byteBuffer().write(type, object);
        return this;
    }

    static Packet empty() {
        return new Impl();
    }

    class Impl implements Packet {
        private final ByteBuffer byteBuffer = ByteBuffer.empty();

        @Override
        public ByteBuffer byteBuffer() {
            return byteBuffer;
        }

        @Override
        public byte[] write() {
            return byteBuffer.bytes();
        }
    }

    interface Type<T extends Packet> {

        Class<T> clazz();

        interface Client {
            //Type<ClientSetScreenContent> SET_SCREEN_CONTENT = Impl.of(ClientSetScreenContent.class);
            //Type<ClientOpenScreen> OPEN_WINDOW = Impl.of(ClientOpenScreen.class);
        }

        interface Server {
            //Type<ServerClickContainer> CLICK_CONTAINER = Impl.of(ServerClickContainer.class);
            //Type<ServerCloseContainer> CLOSE_CONTAINER = Impl.of(ServerCloseContainer.class);
            //Type<ServerSetPlayerRotation> SET_PLAYER_ROTATION = Impl.of(ServerSetPlayerRotation.class);
        }

        record Impl<T extends Packet>(Class<T> clazz) implements Type<T> {
            static <T extends Packet> Impl<T> of(Class<T> clazz) {
                return new Impl<T>(clazz);
            }
        }


    }

}
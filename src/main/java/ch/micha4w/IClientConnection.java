package ch.micha4w;

import java.net.SocketAddress;

public interface IClientConnection {
    void respond (String msg);
    SocketAddress getAddress();

}

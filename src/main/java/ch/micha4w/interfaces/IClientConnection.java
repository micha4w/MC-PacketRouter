package ch.micha4w.interfaces;

import java.net.SocketAddress;

public interface IClientConnection {
    void respond (String msg);
    SocketAddress getAddress();
}

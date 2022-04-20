package ch.micha4w;

import ch.micha4w.interfaces.IClientConnection;
import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketRouter implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("m4w_pr");

	@Override
	public void onInitializeServer() {
		LOGGER.info("Packet router started!");
	}

	public static void onReceived(IClientConnection client, String msg) {
		client.respond(msg);
		LOGGER.info("Ad: " + client.getAddress());
	}
}

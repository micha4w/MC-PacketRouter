package ch.micha4w;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketRouter implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("m4w_pr");

	@Override
	public void onInitializeServer() {
		LOGGER.info("Packet forwarder started!");
	}

	public static void onReceived(IClientConnection client, String msg) {
		client.respond(msg);
		LOGGER.info("Ad: " + client.getAddress());
	}
}

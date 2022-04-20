package ch.micha4w.mixins;

import ch.micha4w.interfaces.IClientConnection;
import ch.micha4w.PacketRouter;
import io.netty.channel.ChannelHandlerContext;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements IClientConnection {

	@Shadow	public abstract void send(Packet<?> packet);
	@Shadow public abstract void disconnect(Text disconnectReason);

	@Shadow @Final private static Logger LOGGER;

	@Inject(at = @At("HEAD"), method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", cancellable = true)
	protected void onChannelRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
		if ( packet instanceof HandshakeC2SPacket && ((HandshakeC2SPacket) packet).getPort() == 0 ) {
			String msg = ((HandshakeC2SPacket) packet).getAddress();
			PacketRouter.onReceived(this, msg);
			this.disconnect(new LiteralText("Finished Routing"));
			ci.cancel();
		}
	}

	public void respond(String msg) {
		this.send(ServerPlayNetworking.createS2CPacket(new Identifier("m4w_pr"), PacketByteBufs.create().writeString(msg)));
	}

}

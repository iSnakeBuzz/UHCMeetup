package com.isnakebuzz.meetup.Utils.PacketHelper;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PacketHandler extends ChannelDuplexHandler {
    private Player p;

    public PacketHandler(final Player p) {
        this.p = p;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
        if (m.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInChat")) {
            String s = Reflection.getFieldValue(m, "a").toString();
            if (s.contains(p.getName())) {
                s.replaceAll(p.getName(), ChatColor.GREEN + p.getName());
            }

        } else {
            super.channelRead(c, m);
        }
    }
}
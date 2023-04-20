package me.hardstyles.blitz.utils;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BookUtility {

    /* Title and Author of the book */
    private String title, author;
    /* Pages of the book */
    private List<String> pages;

    /**
     * Set the Title
     */
    public BookUtility title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the Author
     */
    public BookUtility author(String author) {
        this.author = author;
        return this;
    }

    /**
     * Set the Pages
     */
    public BookUtility pages(List<String> pages) {
        this.pages = pages;
        return this;
    }

    /**
     * Build to an ItemStack
     */
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        if (author != null)
            bookMeta.setAuthor(author);
        if (title != null)
            bookMeta.setTitle(title);
        if (pages != null)
            pages.forEach(string -> bookMeta.addPage(ChatUtil.color("&r" + string)));
        book.setItemMeta(bookMeta);
        return book;
    }

    public void open(Player p) {
        try {
            int slot = p.getInventory().getHeldItemSlot();
            ItemStack old = p.getInventory().getItem(slot);
            p.getInventory().setItem(slot, build());

            ByteBuf buf = Unpooled.buffer(256);
            buf.setByte(0, (byte) 0);
            buf.writerIndex(1);


            Object packet = getNMSClass("PacketPlayOutCustomPayload")
                    .getConstructor(
                            String.class,
                            getNMSClass("PacketDataSerializer")
                    ).newInstance("MC|BOpen", getNMSClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(buf));
            Object craftPlayer = p.getClass().getMethod("getHandle").invoke(p);
            Field f = craftPlayer.getClass().getDeclaredField("playerConnection");
            f.setAccessible(true);
            Object playerConnection = f.get(craftPlayer);
            f.getType().getDeclaredMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            p.getInventory().setItem(slot, old);
        } catch (Exception e) {
            p.sendMessage("§cAn error occurred while trying to open the book. Please contact an administrator.");
        }

    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    private static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
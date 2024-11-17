package fr.dralexgon.shopasvillagerforplayers.database;

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public class ItemStackSerializer {
    private static final Gson gson = new Gson();

    /*
    public static String serialize(ItemStack item) {
        return gson.toJson(item.serialize());
    }

    public static ItemStack deserialize(String json) {
        Map<String, Object> map = gson.fromJson(json, Map.class);
        return ItemStack.deserialize(map);
    }
     */

    public static byte[] serializeBytes(ItemStack item) {
        ByteArrayOutputStream io = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(item);
            os.flush();
            return io.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack deserializeBytes(byte[] buf) {
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        try {
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


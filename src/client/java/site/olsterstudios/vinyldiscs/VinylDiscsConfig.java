package site.olsterstudios.vinyldiscs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VinylDiscsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("vinyl-discs.json");

    private static boolean highResolution;

    private VinylDiscsConfig() {
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            highResolution = false;
            return;
        }

        try {
            JsonObject json = JsonParser.parseString(Files.readString(FILE)).getAsJsonObject();
            highResolution = json.has("highResolution") && json.get("highResolution").getAsBoolean();
        } catch (Exception ignored) {
            highResolution = false;
        }
    }

    public static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("highResolution", highResolution);

        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(json));
        } catch (IOException ignored) {
        }
    }

    public static boolean isHighResolution() {
        return highResolution;
    }

    public static void setHighResolution(boolean enabled) {
        highResolution = enabled;
        save();
        VinylDiscsClient.applyTextureResolution();
    }
}

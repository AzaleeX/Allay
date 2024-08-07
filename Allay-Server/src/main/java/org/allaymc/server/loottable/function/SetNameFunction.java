package org.allaymc.server.loottable.function;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.allaymc.api.item.ItemStack;

/**
 * Allay Project 2024/7/15
 *
 * @author daoge_cmd
 */
@AllArgsConstructor
public class SetNameFunction implements Function {
    protected String name;

    public static FunctionDeserializer deserializer() {
        return new SetNameFunctionDeserializer();
    }

    @Override
    public void apply(ItemStack itemStack) {
        itemStack.setCustomName(name);
    }

    public static class SetNameFunctionDeserializer implements FunctionDeserializer {
        @Override
        public Function deserialize(JsonObject json) {
            var name = json.get("name").getAsString();
            return new SetNameFunction(name);
        }
    }
}

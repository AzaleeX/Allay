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
public class SetDataFunction implements Function {
    protected int data;

    public static FunctionDeserializer deserializer() {
        return new SetDataFunctionDeserializer();
    }

    @Override
    public void apply(ItemStack itemStack) {
        itemStack.setMeta(data);
    }

    public static class SetDataFunctionDeserializer implements FunctionDeserializer {
        @Override
        public Function deserialize(JsonObject json) {
            var data = json.get("data").getAsInt();
            return new SetDataFunction(data);
        }
    }
}

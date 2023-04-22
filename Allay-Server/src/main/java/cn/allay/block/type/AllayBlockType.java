package cn.allay.block.type;

import cn.allay.block.Block;
import cn.allay.block.component.BlockComponentImpl;
import cn.allay.block.component.impl.attribute.BlockAttributeComponentImpl;
import cn.allay.block.component.impl.attribute.VanillaBlockAttributeRegistry;
import cn.allay.block.component.impl.base.BlockBaseComponentImpl;
import cn.allay.block.component.injector.AllayBlockComponentInjector;
import cn.allay.block.component.impl.position.BlockPositionComponentImpl;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.property.type.BlockPropertyType;
import cn.allay.component.interfaces.ComponentImpl;
import cn.allay.component.interfaces.ComponentInitInfo;
import cn.allay.component.interfaces.ComponentProvider;
import cn.allay.identifier.Identifier;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/4/15 <br>
 * Allay Project <br>
 */
@Getter
public class AllayBlockType<T extends Block> implements BlockType<T> {
    protected Class<T> blockClass;
    protected Class<T> injectedClass;
    protected Constructor<T> constructor;
    protected List<ComponentProvider<? extends BlockComponentImpl>> componentProviders;
    protected List<BlockPropertyType<?>> properties;
    protected Map<String, BlockPropertyType<?>> mappedProperties;
    protected Identifier namespaceId;

    protected AllayBlockType(Class<T> blockClass,
                             List<ComponentProvider<? extends BlockComponentImpl>> componentProviders,
                             List<BlockPropertyType<?>> properties,
                             Identifier namespaceId) {
        this.blockClass = blockClass;
        this.componentProviders = componentProviders;
        this.properties = properties;
        this.namespaceId = namespaceId;

        mappedProperties = properties.stream().collect(Collectors.toMap(BlockPropertyType::getName, Function.identity()));
    }

    @SneakyThrows
    protected void complete() {
        try {
            injectedClass = new AllayBlockComponentInjector<>(this)
                    .parentClass(blockClass)
                    .component(convertList(componentProviders))
                    .inject();
        } catch (Exception e) {
            throw new BlockTypeBuildException("Failed to create block type", e);
        }
        //Cache constructor
        constructor = injectedClass.getConstructor(ComponentInitInfo.class);
    }

    public static <T extends Block> BlockTypeBuilder<T> builder(Class<T> blockClass) {
        return new Builder<>(blockClass);
    }

    //TODO: Check it
    protected static <T extends ComponentImpl> List<ComponentProvider<? extends ComponentImpl>> convertList(List<ComponentProvider<? extends T>> inputList) {
        return new ArrayList<>(inputList);
    }

    @SneakyThrows
    public T createBlock(BlockInitInfo info) {
        return constructor.newInstance(info);
    }

    public static class Builder<T extends Block> implements BlockTypeBuilder<T> {
        protected Class<T> blockClass;
        protected List<ComponentProvider<? extends BlockComponentImpl>> componentProviders;
        protected List<BlockPropertyType<?>> properties;
        protected Identifier namespaceId;
        protected VanillaBlockId vanillaBlockId;
        protected boolean isVanillaBlock;

        public Builder(Class<T> blockClass) {
            if (blockClass == null)
                throw new BlockTypeBuildException("Block class cannot be null");
            this.blockClass = blockClass;
        }

        public Builder<T> namespaceId(Identifier namespaceId) {
            this.namespaceId = namespaceId;
            return this;
        }

        public Builder<T> namespaceId(String namespaceId) {
            this.namespaceId = new Identifier(namespaceId);
            return this;
        }

        public Builder<T> vanillaBlock(VanillaBlockId vanillaBlockId) {
            this.vanillaBlockId = vanillaBlockId;
            this.namespaceId = vanillaBlockId.getNamespaceId();
            isVanillaBlock = true;
            return this;
        }

        public Builder<T> property(BlockPropertyType<?>... properties) {
            this.properties = List.of(properties);
            return this;
        }

        public Builder<T> property(List<BlockPropertyType<?>> properties) {
            this.properties = properties;
            return this;
        }

        public Builder<T> component(List<ComponentProvider<? extends BlockComponentImpl>> componentProviders) {
            this.componentProviders = componentProviders;
            return this;
        }

        public AllayBlockType<T> build() {
            if (namespaceId == null)
                throw new BlockTypeBuildException("Identifier cannot be null");
            if (properties == null)
                throw new BlockTypeBuildException("Properties cannot be null");
            if (componentProviders == null)
                throw new BlockTypeBuildException("Component providers cannot be null");
            //Make sure it's not an immutable list, to add the default component
            componentProviders = new ArrayList<>(componentProviders);
            var type = new AllayBlockType<>(blockClass, componentProviders, properties, namespaceId);
            //TODO: 这边应该检查用户是否提供了自己的默认组件，若提供了就不应该再添加默认组件
            componentProviders.add(ComponentProvider.of(() -> new BlockBaseComponentImpl(type), BlockBaseComponentImpl.class));
            componentProviders.add(ComponentProvider.of(info -> new BlockPositionComponentImpl(((BlockInitInfo) info).position()), BlockPositionComponentImpl.class));
            BlockAttributeComponentImpl attributeComponent;
            if (isVanillaBlock) {
                attributeComponent = VanillaBlockAttributeRegistry.getRegistry().get(vanillaBlockId);
                if (attributeComponent == null)
                    throw new BlockTypeBuildException("Cannot find vanilla block attribute component for " + vanillaBlockId + " from vanilla block attribute registry");
            }
            //default attribute component
            else attributeComponent = BlockAttributeComponentImpl.builder().build();
            //TODO: 对于所有Block实例，只持有一个BlockAttributeComponentImpl实例，需要确认后续是否需要改进
            componentProviders.add(ComponentProvider.ofSingleton(attributeComponent));
            type.complete();
            return type;
        }
    }
}

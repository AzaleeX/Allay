package org.allaymc.codegen;

import com.squareup.javapoet.*;
import lombok.SneakyThrows;
import org.allaymc.dependence.BlockId;

import javax.lang.model.element.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static org.allaymc.codegen.BlockIdEnumGen.MAPPED_BLOCK_PALETTE_NBT;
import static org.allaymc.codegen.BlockPropertyTypeGen.BLOCK_PROPERTY_TYPE_INFO_FILE;

/**
 * @author daoge_cmd | Cool_Loong
 */
public class BlockInterfaceGen extends BaseInterfaceGen {

    public static final TypeSpec.Builder BLOCK_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER =
            TypeSpec.classBuilder(ClassNames.BLOCK_TYPE_DEFAULT_INITIALIZER)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    public static Map<Pattern, String> SUB_PACKAGE_GROUPERS = new LinkedHashMap<>();

    public static void main(String[] args) {
        // NOTICE: Please run BlockIdEnumGen.generate() first before running this method
        BlockPropertyTypeGen.generate();
        generate();
    }

    @SneakyThrows
    public static void generate() {
        registerSubPackages();
        var interfaceDir = Path.of("Allay-API/src/main/java/org/allaymc/api/block/interfaces");
        if (!Files.exists(interfaceDir)) Files.createDirectories(interfaceDir);
        var typesClass = TypeSpec.classBuilder(ClassNames.BLOCK_TYPES).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        for (var id : BlockId.values()) {
            typesClass.addField(
                    FieldSpec.builder(ParameterizedTypeName.get(ClassNames.BLOCK_TYPE, generateClassFullName(id)), id.name())
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .build()
            );
            var blockClassSimpleName = generateClassSimpleName(id);
            var blockClassFullName = generateClassFullName(id);
            var folderName = tryFindSpecifiedFolderName(blockClassSimpleName);
            var folderPath = folderName != null ? interfaceDir.resolve(folderName) : interfaceDir;
            var path = folderPath.resolve(blockClassSimpleName + ".java");
            if (!Files.exists(path)) {
                System.out.println("Generating " + blockClassSimpleName + "...");
                if (!Files.exists(folderPath))
                    Files.createDirectories(folderPath);
                generateClass(ClassNames.BLOCK_BEHAVIOR, blockClassFullName, path);
            }
            addDefaultBlockTypeInitializer(id, blockClassFullName);
        }
        generateDefaultBlockTypeInitializer();
        var javaFile = JavaFile.builder(ClassNames.BLOCK_TYPES.packageName(), typesClass.build())
                .indent(CodeGenConstants.INDENT)
                .skipJavaLangImports(true)
                .build();
        System.out.println("Generating " + ClassNames.BLOCK_TYPES.simpleName() + ".java ...");
        Files.writeString(Path.of("Allay-API/src/main/java/org/allaymc/api/block/type/" + ClassNames.BLOCK_TYPES.simpleName() + ".java"), javaFile.toString());
    }

    private static void addDefaultBlockTypeInitializer(BlockId id, ClassName blockClassName) {
        var initializer = CodeBlock.builder();
        initializer
                .add("$T.$N = $T\n", ClassNames.BLOCK_TYPES, id.name(), ClassNames.ALLAY_BLOCK_TYPE)
                .add("        .builder($T.class)\n", blockClassName)
                .add("        .vanillaBlock($T.$N)\n", ClassNames.BLOCK_ID, id.name());
        var blockPaletteData = MAPPED_BLOCK_PALETTE_NBT.get(id.getIdentifier().toString());
        var states = blockPaletteData.getCompound("states");
        if (!states.isEmpty()) {
            initializer.add("        .setProperties(");
            AtomicInteger count = new AtomicInteger();
            states.forEach((name, value) -> {
                var propertyName = BLOCK_PROPERTY_TYPE_INFO_FILE.differentSizePropertyTypes.contains(name.replaceAll(":", "_")) && BLOCK_PROPERTY_TYPE_INFO_FILE.specialBlockTypes.containsKey(id.getIdentifier().toString()) ?
                        BLOCK_PROPERTY_TYPE_INFO_FILE.specialBlockTypes.get(id.getIdentifier().toString()).get(name.replaceAll(":", "_")).toUpperCase() : name.replaceAll(":", "_").toUpperCase();
                initializer.add("$T.$N" + (states.size() == count.incrementAndGet() ? "" : ", "), ClassNames.BLOCK_PROPERTY_TYPES, propertyName);
            });
            initializer.add(")\n");
        }
        initializer.add("        .build();");
        BLOCK_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER
                .addMethod(
                        MethodSpec.methodBuilder(generateInitializerMethodName(id))
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .addStatement("if ($T.$N != null) return", ClassNames.BLOCK_TYPES, id.name())
                                .addCode(initializer.build())
                                .build()
                );
    }

    @SneakyThrows
    private static void generateDefaultBlockTypeInitializer() {
        var filePath = Path.of("Allay-Server/src/main/java/org/allaymc/server/block/type/BlockTypeDefaultInitializer.java");
        Files.deleteIfExists(filePath);
        var folderPath = filePath.getParent();
        if (!Files.exists(folderPath))
            Files.createDirectories(folderPath);
        var javaFile = JavaFile.builder(ClassNames.BLOCK_TYPE_DEFAULT_INITIALIZER.packageName(), BLOCK_TYPE_DEFAULT_INITIALIZER_CLASS_BUILDER.build())
                .indent(CodeGenConstants.INDENT)
                .skipJavaLangImports(true)
                .build();
        System.out.println("Generating " + ClassNames.BLOCK_TYPE_DEFAULT_INITIALIZER.simpleName() + ".java ...");
        Files.writeString(filePath, javaFile.toString());
    }

    private static ClassName generateClassFullName(BlockId id) {
        var simpleName = generateClassSimpleName(id);
        var folderName = tryFindSpecifiedFolderName(simpleName);
        return ClassName.get("org.allaymc.api.block.interfaces" + (folderName != null ? "." + folderName : ""), simpleName);
    }

    private static String generateClassSimpleName(BlockId id) {
        return "Block" + Utils.convertToPascalCase(id.getIdentifier().path()) + "Behavior";
    }

    private static String generateInitializerMethodName(BlockId id) {
        return "init" + Utils.convertToPascalCase(id.getIdentifier().path());
    }

    private static String tryFindSpecifiedFolderName(String blockClassSimpleName) {
        for (var entry : SUB_PACKAGE_GROUPERS.entrySet()) {
            var pattern = entry.getKey();
            if (pattern.matcher(blockClassSimpleName).find()) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static void registerSubPackage(Pattern regex, String packageName) {
        SUB_PACKAGE_GROUPERS.put(regex, packageName);
    }

    private static void registerSubPackages() {
        registerSubPackage(Pattern.compile(".*Slab\\d?Behavior"), "slab");
        registerSubPackage(Pattern.compile(".*StairsBehavior"), "stairs");
        registerSubPackage(Pattern.compile(".*DoorBehavior"), "door");
        registerSubPackage(Pattern.compile(".*StandingSignBehavior"), "standingsign");
        registerSubPackage(Pattern.compile(".*HangingSignBehavior"), "hangingsign");
        registerSubPackage(Pattern.compile(".*WallSignBehavior"), "wallsign");
        registerSubPackage(Pattern.compile(".*WallBehavior"), "wall");
        registerSubPackage(Pattern.compile("BlockElement.*"), "element");
        registerSubPackage(Pattern.compile(".*CoralBehavior"), "coral");
        registerSubPackage(Pattern.compile(".*CoralBlockBehavior"), "coralblock");
        registerSubPackage(Pattern.compile(".*CoralFan.*"), "coralfan");
        registerSubPackage(Pattern.compile(".*BricksBehavior"), "bricks");
        registerSubPackage(Pattern.compile(".*WoolBehavior"), "wool");
        registerSubPackage(Pattern.compile(".*ButtonBehavior"), "button");
        registerSubPackage(Pattern.compile(".*PlanksBehavior"), "planks");
        registerSubPackage(Pattern.compile(".*TrapdoorBehavior"), "trapdoor");
        registerSubPackage(Pattern.compile(".*CandleBehavior"), "candle");
        registerSubPackage(Pattern.compile(".*CandleCakeBehavior"), "candlecake");
        registerSubPackage(Pattern.compile(".*ConcreteBehavior"), "concrete");
        registerSubPackage(Pattern.compile(".*ConcretePowderBehavior"), "concretepowder");
        registerSubPackage(Pattern.compile(".*TerracottaBehavior"), "terracotta");
        registerSubPackage(Pattern.compile(".*ShulkerBoxBehavior"), "shulkerbox");
        registerSubPackage(Pattern.compile(".*CarpetBehavior"), "carpet");
        registerSubPackage(Pattern.compile(".*WoodBehavior"), "wood");
        registerSubPackage(Pattern.compile(".*(Leaves\\d?|LeavesFlowered)Behavior"), "leaves");
        registerSubPackage(Pattern.compile(".*FenceBehavior"), "fence");
        registerSubPackage(Pattern.compile(".*FenceGateBehavior"), "fencegate");
        registerSubPackage(Pattern.compile(".*LogBehavior"), "log");
        registerSubPackage(Pattern.compile(".*CopperBehavior"), "copper");
        registerSubPackage(Pattern.compile(".*SaplingBehavior"), "sapling");
        registerSubPackage(Pattern.compile(".*(?:Water|Lava)Behavior"), "liquid");
        registerSubPackage(Pattern.compile("BlockPiston.*"), "piston");
        registerSubPackage(Pattern.compile("BlockStickyPiston.*"), "piston");
        registerSubPackage(Pattern.compile(".*StainedGlassBehavior"), "stainedglass");
        registerSubPackage(Pattern.compile(".*StainedGlassPaneBehavior"), "stainedglasspane");
        registerSubPackage(Pattern.compile(".*GlassBehavior"), "glass");
        registerSubPackage(Pattern.compile(".*GlassPaneBehavior"), "glasspane");
        registerSubPackage(Pattern.compile(".*AmethystBudBehavior"), "amethystbud");
        registerSubPackage(Pattern.compile(".*Torchflower.*Behavior"), "torchflower");
        registerSubPackage(Pattern.compile(".*Torch.*Behavior"), "torch");
        registerSubPackage(Pattern.compile(".*LightBlock.*Behavior"), "lightblock");
        registerSubPackage(Pattern.compile(".*CoralWallFanBehavior"), "coralwallfan");
        registerSubPackage(Pattern.compile(".*AnvilBehavior"), "anvil");
        registerSubPackage(Pattern.compile(".*DirtBehavior"), "dirt");
        registerSubPackage(Pattern.compile(".*SandBehavior"), "sand");
        registerSubPackage(Pattern.compile(".*SandstoneBehavior"), "sandstone");
        registerSubPackage(Pattern.compile("BlockPurpur.*"), "purpur");
        registerSubPackage(Pattern.compile(".*SpongeBehavior"), "sponge");
        registerSubPackage(Pattern.compile(".*TntBehavior"), "tnt");
    }
}

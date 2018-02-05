package de.maxhenkel.advancedtools.render.base;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class UniversalModel implements ICustomModelLoader {

    private IAdvancedModel wrapper;
    private UniversalIModel model;

    public UniversalModel(IAdvancedModel wrapper) {
        this.wrapper = wrapper;
        model = new UniversalIModel(wrapper);
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return wrapper.isValid(modelLocation);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return model;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    public class UniversalIModel implements IModel {
        private ImmutableList<ResourceLocation> textures;
        private IAdvancedModel wrapper;
        public UniversalIModel(IAdvancedModel wrapper) {
            this.wrapper=wrapper;
            textures = ImmutableList.of();
        }

        @Override
        public IModel retexture(ImmutableMap<String, String> textures) {
            return this;
        }

        @Override
        public IModel process(ImmutableMap<String, String> customData) {
            return this;
        }

        public IModel inject(ItemStack stack) {
            textures = wrapper.getTextures(stack);
            return this;
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            return ImmutableList.of();
        }

        @Override
        public Collection<ResourceLocation> getTextures() {
            return wrapper.getAllTextures();
        }

        @Override
        public IBakedModel bake(IModelState state, VertexFormat format, java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

            ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

            IBakedModel model = (new ItemLayerModel(textures)).bake(state, format, bakedTextureGetter);
            builder.addAll(model.getQuads(null, null, 0));

            return new BakedUniversalModel(this, builder.build(), format, Maps.immutableEnumMap(transformMap), Maps.<String, IBakedModel>newHashMap());
        }

        @Override
        public IModelState getDefaultState() {
            return TRSRTransformation.identity();
        }
    }

    private static final class BakedUniversalModel extends BakedToolModel {

        public BakedUniversalModel(UniversalIModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
                                   ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                   Map<String, IBakedModel> cache) {
            super(parent, quads, format, transforms, cache);
        }

        @Override
        public ItemOverrideList getOverrides() {
            return BakedUniversalOverrideHandler.INSTANCE;
        }

    }

    private static final class BakedUniversalOverrideHandler extends ItemOverrideList {
        public static final BakedUniversalOverrideHandler INSTANCE = new BakedUniversalOverrideHandler();

        private BakedUniversalOverrideHandler() {
            super(ImmutableList.<ItemOverride>of());
        }

        @Override
        @Nonnull
        public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {

            if (!(stack.getItem() instanceof AbstractTool)) {
                return originalModel;
            }

            BakedUniversalModel model = (BakedUniversalModel) originalModel;

            IModel parent = ((UniversalIModel) model.parent).inject(stack);
            Function<ResourceLocation, TextureAtlasSprite> textureGetter;
            textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
                public TextureAtlasSprite apply(ResourceLocation location) {
                    return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
                }
            };
            IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format,
                    textureGetter);
            return bakedModel;
        }
    }

}

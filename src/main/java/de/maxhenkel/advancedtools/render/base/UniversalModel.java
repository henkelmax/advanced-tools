package de.maxhenkel.advancedtools.render.base;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nullable;
import java.util.*;

public class UniversalModel implements ICustomModelLoader {

    private IAdvancedModel wrapper;
    private UnbakedUniversalIModel model;

    public UniversalModel(IAdvancedModel wrapper) {
        this.wrapper = wrapper;
        this.model = new UnbakedUniversalIModel(wrapper);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return wrapper.isValid(modelLocation);
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception {
        return model;
    }

    public class UnbakedUniversalIModel implements IUnbakedModel {
        private ImmutableList<ResourceLocation> textures;
        private IAdvancedModel wrapper;

        public UnbakedUniversalIModel(IAdvancedModel wrapper) {
            this.wrapper = wrapper;
            this.textures = ImmutableList.of();
        }

        @Override
        public IUnbakedModel retexture(ImmutableMap<String, String> textures) {
            return this;//new UnbakedUniversalIModel(textures);
        }

        @Override
        public IUnbakedModel process(ImmutableMap<String, String> customData) {
            return this; //new UnbakedUniversalIModel(textures);
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
        public Collection<ResourceLocation> getTextures(java.util.function.Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
            return wrapper.getAllTextures();
        }

        @Nullable
        @Override
        public IBakedModel func_217641_a(ModelBakery bakery, java.util.function.Function<ResourceLocation, TextureAtlasSprite> spriteFunction, ISprite sprite) {
            return null; //TODO ??
        }

        @Nullable
        @Override
        public IBakedModel bake(ModelBakery bakery, java.util.function.Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
            IBakedModel model = (new ItemLayerModel(textures)).bake(bakery, spriteGetter, sprite, format);

            ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

            builder.addAll(model.getQuads(null, null, new Random()));

            return new BakedUniversalModel(this, builder.build(), format, Maps.newHashMap());
        }

        @Override
        public IModelState getDefaultState() {
            return TRSRTransformation.identity();
        }
    }

    private static final class BakedUniversalModel extends BakedToolModel {


        public BakedUniversalModel(IModel parent, ImmutableList<BakedQuad> quads, VertexFormat format, Map<String, IBakedModel> cache) {
            super(parent, quads, format, cache);
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
            return quads;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return BakedUniversalOverrideHandler.INSTANCE;
        }

    }

    private static final class BakedUniversalOverrideHandler extends ItemOverrideList {
        public static final BakedUniversalOverrideHandler INSTANCE = new BakedUniversalOverrideHandler();

        public BakedUniversalOverrideHandler(){

        }

        @Nullable
        @Override
        public IBakedModel getModelWithOverrides(IBakedModel originalModel, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
            if (!(stack.getItem() instanceof AbstractTool)) {
                return originalModel;
            }

            BakedUniversalModel model = (BakedUniversalModel) originalModel;

            IModel parent = ((UnbakedUniversalIModel) model.parent).inject(stack);
            Function<ResourceLocation, TextureAtlasSprite> textureGetter;
            textureGetter = location -> Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString());


            ModelLoader modelbakery = new ModelLoader(Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getTextureMap(), Minecraft.getInstance().getProfiler());

            IBakedModel bakedModel = parent.bake(modelbakery, textureGetter, new SimpleModelState(model.transforms), model.format);
            return bakedModel;
        }
    }

}

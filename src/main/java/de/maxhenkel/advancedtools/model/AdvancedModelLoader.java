package de.maxhenkel.advancedtools.model;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.block.BlockState;
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
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ItemLayerModel;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class AdvancedModelLoader implements ICustomModelLoader {

    public AdvancedModelLoader() {

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getNamespace().equals(Main.MODID);
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception {
        return new UnbakedModel();
    }

    private class UnbakedModel implements IUnbakedModel {

        private ItemLayerModel layerModel;

        public UnbakedModel() {
            layerModel = new ItemLayerModel(ImmutableList.of());
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            return layerModel.getDependencies();
        }

        @Override
        public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
            return layerModel.getTextures(modelGetter, missingTextureErrors);
        }

        @Nullable
        @Override
        public IBakedModel bake(ModelBakery modelBakery, Function<ResourceLocation, TextureAtlasSprite> function, ISprite iSprite, VertexFormat vertexFormat) {
            IBakedModel model = layerModel.bake(modelBakery, function, iSprite, vertexFormat);
            return new IBakedModel() {
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random) {
                    return model.getQuads(blockState, direction, random);
                }

                @Override
                public boolean isAmbientOcclusion() {
                    return model.isAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return model.isGui3d();
                }

                @Override
                public boolean isBuiltInRenderer() {
                    return model.isBuiltInRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleTexture() {
                    return model.getParticleTexture();
                }

                @Override
                public ItemOverrideList getOverrides() {
                    return new ItemOverrideList() {
                        @Nullable
                        @Override
                        public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World world, @Nullable LivingEntity livingEntity) {
                            ImmutableList<ResourceLocation> list = ImmutableList.<ResourceLocation>builder().addAll(StackUtils.getMaterial(stack).getPartTextures().values()).build();
                            ItemLayerModel model1 = new ItemLayerModel(list);
                            return model1.bake(modelBakery, function, iSprite, vertexFormat);
                        }
                    };
                }
            };
        }
    }
}

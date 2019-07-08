package de.maxhenkel.advancedtools.render.base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class BakedToolModel implements IBakedModel {

    protected final IModel parent;
    protected final Map<String, IBakedModel> cache;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected final ImmutableList<BakedQuad> quads;
    protected final VertexFormat format;

    public BakedToolModel(IModel parent, ImmutableList<BakedQuad> quads, VertexFormat format, Map<String, IBakedModel> cache) {
        this.quads = quads;
        this.format = format;
        this.parent = parent;
        this.transforms = itemTransforms();
        this.cache = cache;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
    }

    protected ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> itemTransforms() {
        ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
        builder.put(ItemCameraTransforms.TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
        builder.put(ItemCameraTransforms.TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
        builder.put(ItemCameraTransforms.TransformType.FIXED, get(0, 0, 0, 0, 180, 0, 1));
        builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 4, 0.5f, 0, -90, 55, 0.85f));
        builder.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, get(0, 4, 0.5f, 0, 90, -55, 0.85f));
        builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
        builder.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
        return builder.build();
    }

    protected TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                new Vector3f(tx / 16, ty / 16, tz / 16),
                TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null));
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if (side == null)
            return quads;
        return ImmutableList.of();
    }


    public boolean isAmbientOcclusion() {
        return true;
    }

    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

}
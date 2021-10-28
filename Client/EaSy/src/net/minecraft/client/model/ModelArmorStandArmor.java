/*
 * Decompiled with CFR 0.0.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.Rotations;

public class ModelArmorStandArmor
extends ModelBiped {
    public ModelArmorStandArmor() {
        this(0.0f);
    }

    public ModelArmorStandArmor(float modelSize) {
        this(modelSize, 64, 32);
    }

    protected ModelArmorStandArmor(float modelSize, int textureWidthIn, int textureHeightIn) {
        super(modelSize, 0.0f, textureWidthIn, textureHeightIn);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        if (entityIn instanceof EntityArmorStand) {
            EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
            this.bipedHead.rotateAngleX = 0.017453292f * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292f * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292f * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0f, 1.0f, 0.0f);
            this.bipedBody.rotateAngleX = 0.017453292f * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292f * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292f * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292f * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292f * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292f * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292f * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292f * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292f * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292f * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292f * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292f * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9f, 11.0f, 0.0f);
            this.bipedRightLeg.rotateAngleX = 0.017453292f * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292f * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292f * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9f, 11.0f, 0.0f);
            ModelArmorStandArmor.copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
    }
}


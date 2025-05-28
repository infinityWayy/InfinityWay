package huix.infinity.common.world.entity.render.hound;

import huix.infinity.common.world.entity.monster.Hellhound;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class HellhoundModel extends HierarchicalModel<Hellhound> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart upperBody;

    public HellhoundModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.upperBody = root.getChild("upper_body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition head = partDefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F),
                PartPose.offset(-1.0F, 13.5F, -7.0F));

        head.addOrReplaceChild("nose",
                CubeListBuilder.create()
                        .texOffs(0, 10)
                        .addBox(-1.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F),
                PartPose.ZERO);

        head.addOrReplaceChild("right_ear",
                CubeListBuilder.create()
                        .texOffs(16, 14)
                        .addBox(-3.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.ZERO);

        head.addOrReplaceChild("left_ear",
                CubeListBuilder.create()
                        .texOffs(16, 14)
                        .addBox(1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.ZERO);

        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(18, 14)
                        .addBox(-4.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("upper_body",
                CubeListBuilder.create()
                        .texOffs(21, 0)
                        .addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(-2.5F, 16.0F, 7.0F));

        partDefinition.addOrReplaceChild("left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(0.5F, 16.0F, 7.0F));

        partDefinition.addOrReplaceChild("right_front_leg",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(-2.5F, 16.0F, -4.0F));

        partDefinition.addOrReplaceChild("left_front_leg",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(0.5F, 16.0F, -4.0F));

        partDefinition.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, -0.3F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    public void prepareMobModel(Hellhound hellhound, float limbSwing, float limbSwingAmount, float partialTick) {

        if (hellhound.isInSittingPose()) {

            this.upperBody.setPos(-1.0F, 16.0F, -3.0F);
            this.upperBody.xRot = 1.2566371F; // 约72度
            this.upperBody.yRot = 0.0F;

            this.body.setPos(0.0F, 18.0F, 0.0F);
            this.body.xRot = ((float)Math.PI / 4F); // 45度

            this.tail.setPos(-1.0F, 21.0F, 6.0F);

            this.rightHindLeg.setPos(-2.5F, 22.7F, 2.0F);
            this.rightHindLeg.xRot = ((float)Math.PI * 1.5F); // 270度
            this.leftHindLeg.setPos(0.5F, 22.7F, 2.0F);
            this.leftHindLeg.xRot = ((float)Math.PI * 1.5F); // 270度

            this.rightFrontLeg.xRot = 5.811947F; // 约333度
            this.rightFrontLeg.setPos(-2.49F, 17.0F, -4.0F);
            this.leftFrontLeg.xRot = 5.811947F; // 约333度
            this.leftFrontLeg.setPos(0.51F, 17.0F, -4.0F);
        } else {

            this.body.setPos(0.0F, 14.0F, 2.0F);
            this.body.xRot = ((float)Math.PI / 2F);
            this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
            this.upperBody.xRot = this.body.xRot;

            this.tail.setPos(-1.0F, 12.0F, 8.0F);

            this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
            this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
            this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
            this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);

            this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }
    }

    @Override
    public void setupAnim(Hellhound hellhound, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.prepareMobModel(hellhound, limbSwing, limbSwingAmount, 0);

        // 头部旋转
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        if (hellhound.isInSittingPose()) {

            if (hellhound.isAngry()) {
                this.tail.yRot = 0.0F;

                this.tail.xRot = hellhound.getTailAngle() + 0.2F;
            } else {
                this.tail.yRot = 0.0F;

                this.tail.xRot = hellhound.getTailAngle();
            }
        } else {

            this.tail.xRot = 1.5F;
            this.tail.yRot = 0.0F;
            this.tail.zRot = 0.0F;

            if (hellhound.getTarget() != null) {
                this.head.yRot += (float)Math.sin(ageInTicks * 0.4F) * 0.1F;
                this.upperBody.xRot = this.upperBody.xRot - 0.1F;
            }
        }
    }
}
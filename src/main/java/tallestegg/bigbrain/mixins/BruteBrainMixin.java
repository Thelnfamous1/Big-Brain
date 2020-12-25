package tallestegg.bigbrain.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.FindNewAttackTargetTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.SupplementedTask;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteBrain;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import tallestegg.bigbrain.entity.ai.tasks.ChargeTask;
import tallestegg.bigbrain.items.BucklerItem;

//This is also where the M.A.G.I.C happens.
//I'm probably using way too many mixins.
@Mixin(PiglinBruteBrain.class)
public class BruteBrainMixin {
    @Overwrite
    private static void func_242364_d(PiglinBruteEntity brute, Brain<PiglinBruteEntity> brain) {
        brain.registerActivity(Activity.FIGHT, 10, ImmutableList.of(new FindNewAttackTargetTask<>((p_242361_1_) -> {
            return !func_242350_a(brute, p_242361_1_);
        }), new SupplementedTask<>(BruteBrainMixin::isBruteHoldingBuckler, new ChargeTask<>()), new MoveToTargetTask(1.0F), new AttackTargetTask(20)), MemoryModuleType.ATTACK_TARGET);
    }
    
    private static boolean isBruteHoldingBuckler(PiglinBruteEntity brute) {
        return brute.getHeldItemOffhand().getItem() instanceof BucklerItem;
    }

    @Shadow
    private static boolean func_242350_a(AbstractPiglinEntity p_242350_0_, LivingEntity p_242350_1_) {
        return false;
    }
}

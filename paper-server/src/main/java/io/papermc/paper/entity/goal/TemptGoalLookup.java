package io.papermc.paper.entity.goal;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TemptGoalLookup {
    private static int registeredPredicateCounter = 0;

    public static final TemptGoalPredicate BEE_TEMPT = register(stack -> stack.is(ItemTags.BEE_FOOD));
    public static final TemptGoalPredicate CHICKEN_TEMPT = register(stack -> stack.is(ItemTags.CHICKEN_FOOD));
    public static final TemptGoalPredicate COW_TEMPT = register(stack -> stack.is(ItemTags.COW_FOOD));
    public static final TemptGoalPredicate PANDA_TEMPT = register(stack -> stack.is(ItemTags.PANDA_FOOD));
    public static final TemptGoalPredicate PIG_TEMPT_STICK = register(stack -> stack.is(Items.CARROT_ON_A_STICK));
    public static final TemptGoalPredicate PIG_TEMPT = register(stack -> stack.is(ItemTags.PIG_FOOD));
    public static final TemptGoalPredicate RABBIT_TEMPT = register(stack -> stack.is(ItemTags.RABBIT_FOOD));
    public static final TemptGoalPredicate SHEEP_TEMPT = register(stack -> stack.is(ItemTags.SHEEP_FOOD));
    public static final TemptGoalPredicate TURTLE_TEMPT = register(stack -> stack.is(ItemTags.TURTLE_FOOD));
    public static final TemptGoalPredicate HORSE_TEMPT = register(stack -> stack.is(ItemTags.HORSE_TEMPT_ITEMS));
    public static final TemptGoalPredicate LLAMA_TEMPT = register(stack -> stack.is(ItemTags.LLAMA_TEMPT_ITEMS));
    public static final TemptGoalPredicate STRIDER_TEMPT = register(stack -> stack.is(ItemTags.STRIDER_TEMPT_ITEMS));

    public record TemptGoalPredicate(int index, Predicate<ItemStack> predicate) {
    }

    private static TemptGoalPredicate register(final Predicate<ItemStack> predicate) {
        final TemptGoalPredicate val = new TemptGoalPredicate(registeredPredicateCounter, predicate);
        registeredPredicateCounter++;
        return val;
    }

    private final List<BitSet> precalculatedTemptItems = new ArrayList<>();
    private final BitSet calculatedThisTick = new java.util.BitSet();

    {
        for (int i = 0; i < registeredPredicateCounter; i++) {
            this.precalculatedTemptItems.add(new BitSet());
        }
    }

    public void reset() {
        for (int i = 0; i < registeredPredicateCounter; i++) {
            this.precalculatedTemptItems.get(i).clear();
        }
        this.calculatedThisTick.clear();
    }

    public boolean isCalculated(int index) {
        return this.calculatedThisTick.get(index);
    }

    public void setCalculated(int index) {
        this.calculatedThisTick.set(index);
    }

    public BitSet getBitSet(int index) {
        return this.precalculatedTemptItems.get(index);
    }
}
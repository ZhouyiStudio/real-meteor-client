package worst.modules.impl.render;

import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.SliderSettings;

public class ChunkAnimator extends ModuleStructure {
    private static ChunkAnimator instance;

    private final SliderSettings speed = new SliderSettings("Скорость", "").range(1, 20).setValue(10);

    public ChunkAnimator() {
        super("Chunk Animator", "Анимирует появляющиеся чанки", ModuleCategory.RENDER);
        instance = this;
        settings(speed);
    }

    public static ChunkAnimator getInstance() {
        return instance;
    }

    public float getSpeed() {
        return speed.getValue();
    }
}
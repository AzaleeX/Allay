package cn.allay.server.world.generator;

import cn.allay.api.world.chunk.ChunkService;
import cn.allay.api.world.generator.LimitedWorldRegion;
import cn.allay.api.world.generator.WorldGenerationService;
import cn.allay.api.world.generator.WorldGenerator;
import lombok.Getter;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Allay Project 2023/7/1
 *
 * @author daoge_cmd
 */
public class AllayWorldGenerationService implements WorldGenerationService {

    private final ForkJoinPool executorService;
    @Getter
    private final ChunkService chunkService;
    @Getter
    private final WorldGenerator worldGenerator;

    public AllayWorldGenerationService(ForkJoinPool executorService, ChunkService chunkService, WorldGenerator worldGenerator) {
        this.executorService = executorService;
        this.chunkService = chunkService;
        this.worldGenerator = worldGenerator;
    }

    public <T extends LimitedWorldRegion> void submitGenerationTask(T limitedWorldRegion, FinishCallback<T> finishCallback) {
        WorldGenerationTask<T> worldGenerationTask = new WorldGenerationTask<>(limitedWorldRegion, finishCallback);
        executorService.submit(worldGenerationTask);
    }

    public class WorldGenerationTask<T> extends RecursiveAction {

        private final LimitedWorldRegion limitedWorldRegion;
        private final FinishCallback<T> finishCallback;

        public WorldGenerationTask(LimitedWorldRegion limitedWorldRegion, FinishCallback<T> finishCallback) {
            this.limitedWorldRegion = limitedWorldRegion;
            this.finishCallback = finishCallback;
        }

        @Override
        protected void compute() {
            worldGenerator.generate(limitedWorldRegion);
            finishCallback.onFinish(limitedWorldRegion);
        }
    }
}

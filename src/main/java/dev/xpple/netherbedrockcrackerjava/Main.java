package dev.xpple.netherbedrockcrackerjava;

import com.github.netherbedrockcracker.Block;
import com.github.netherbedrockcracker.NetherBedrockCracker;
import com.github.netherbedrockcracker.VecI64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

    static {
        String libraryName = System.mapLibraryName("bedrock_cracker");
        String extension = FilenameUtils.getExtension(libraryName);
        libraryName = "bedrock_cracker" + '.' + extension;
        URL libraryPath = Main.class.getClassLoader().getResource(libraryName);
        Path libcubiomes;
        try {
            libcubiomes = Files.createTempFile("bedrock_cracker", '.' + extension);
            IOUtils.copy(libraryPath, libcubiomes.toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.load(libcubiomes.toAbsolutePath().toString());
    }

    private static final String DATA = """
        -1 123 -7 Bedrock
        -1 123 -9 Bedrock
        -2 123 -11 Bedrock
        -3 123 -10 Bedrock
        -5 123 -10 Bedrock
        -5 123 -9 Bedrock
        -5 123 -8 Bedrock
        -5 123 -6 Bedrock
        -6 123 -6 Bedrock
        -6 123 -4 Bedrock
        -6 123 -9 Bedrock
        -7 123 -12 Bedrock
        -7 123 -8 Bedrock
        -7 123 -7 Bedrock
        -8 123 -7 Bedrock
        -8 123 -4 Bedrock
        -9 123 -13 Bedrock
        -9 123 -8 Bedrock
        -9 123 -6 Bedrock
        -9 123 -4 Bedrock
        -10 123 -5 Bedrock
        -11 123 -6 Bedrock
        -12 123 -13 Bedrock
        -12 123 -12 Bedrock
        -12 123 -11 Bedrock
        -12 123 -2 Bedrock
        -12 123 0 Bedrock
        -13 123 -10 Bedrock
        -13 123 -9 Bedrock
        -13 123 -7 Bedrock
        -13 123 -6 Bedrock
        -13 123 0 Bedrock
        23 4 -92 Bedrock
        24 4 -92 Bedrock
        25 4 -92 Bedrock
        25 4 -93 Bedrock""";

    private static final long SEED = 765906787396911863L;

    private record Pos(int x, int y, int z) {
    }

    public static void main(String[] args) {
        List<Pos> positions = DATA.lines()
            .map(pos -> Arrays.stream(pos.split(" "))
                .limit(3)
                .map(Integer::parseInt)
                .toList())
            .map(l -> new Pos(l.get(0), l.get(1), l.get(2)))
            .toList();

        long len = positions.size();

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment blockArray = Block.allocateArray(len, arena);
            for (int i = 0; i < len; i++) {
                MemorySegment block = Block.asSlice(blockArray, i);
                Pos pos = positions.get(i);
                Block.x(block, pos.x);
                Block.y(block, pos.y);
                Block.z(block, pos.z);
                Block.block_type(block, NetherBedrockCracker.BEDROCK());
            }

            MemorySegment vecI64 = NetherBedrockCracker.crack(arena, blockArray, len, 11, NetherBedrockCracker.Normal(), NetherBedrockCracker.WorldSeed());
            long seedsLen = VecI64.len(vecI64);
            MemorySegment seedsPtr = VecI64.ptr(vecI64);

            System.out.println("Expecting: " + SEED);
            for (int i = 0; i < seedsLen; i++) {
                long seed = seedsPtr.getAtIndex(NetherBedrockCracker.C_LONG_LONG, i);
                System.out.println("Found: " + seed);
            }
            NetherBedrockCracker.free_vec(vecI64);
        }
    }
}

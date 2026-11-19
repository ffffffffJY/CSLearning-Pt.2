import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        List<Integer> hashes = new ArrayList<>();
        int len = oomages.size();
        for (int i = 0; i <= M; i++) {
            hashes.add(0);
        }
        for (Oomage o : oomages) {
            int bucketNum = Math.floorMod(o.hashCode(), M);
            hashes.set(bucketNum, 1 + hashes.get(bucketNum));
        }
        for (int i = 0; i < M; i++) {
            if (hashes.get(i) < len / 50 ||  hashes.get(i) > len / 2.5) {
                return false;
            }
        }
        return true;
    }
}

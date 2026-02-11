import java.io.*;
import java.util.*;

public class Main {

    static long l;
    static int n, k;
    static final Queue<long[]> bfsQ = new ArrayDeque<>();
    static final Set<Long> visited = new HashSet<>();
    static final List<Long> darkness = new ArrayList<>();

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    /**
     * 32069 가로등
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        l = Long.parseLong(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            long coordinate = Long.parseLong(st.nextToken());
           bfsQ.add(new long[]{ coordinate, 0 });
           visited.add(coordinate);
        }

        while (!bfsQ.isEmpty() && darkness.size() < k) {
            long[] axis = bfsQ.poll();
            long coordinate = axis[0];
            long distance = axis[1];

            darkness.add(distance);

            if (coordinate < l && !visited.contains(coordinate + 1)) {
                bfsQ.offer(new long[]{ coordinate + 1, distance + 1});
                visited.add(coordinate + 1);
            }
            if (coordinate > 0 && !visited.contains(coordinate - 1)) {
                bfsQ.offer(new long[]{ coordinate - 1, distance + 1});
                visited.add(coordinate - 1);
            }
        }

        for (long dark : darkness) {
            sb.append(dark).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}
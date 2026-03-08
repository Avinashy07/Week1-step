import java.util.*;

// 1️⃣ Username Availability Checker
class UsernameChecker {

    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();

    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    public void register(String username, int userId) {
        users.put(username, userId);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> list = new ArrayList<>();
        list.add(username + "1");
        list.add(username + "2");
        list.add(username.replace("_", "."));
        return list;
    }

    public String getMostAttempted() {
        String maxUser = "";
        int max = 0;

        for (String u : attempts.keySet()) {
            if (attempts.get(u) > max) {
                max = attempts.get(u);
                maxUser = u;
            }
        }
        return maxUser;
    }
}

// 2️⃣ Flash Sale Inventory Manager
class InventoryManager {

    private HashMap<String, Integer> stock = new HashMap<>();
    private LinkedHashMap<Integer, String> waitingList = new LinkedHashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
    }

    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    public synchronized String purchaseItem(String productId, int userId) {

        int current = stock.getOrDefault(productId, 0);

        if (current > 0) {
            stock.put(productId, current - 1);
            return "Success. Remaining stock: " + (current - 1);
        } else {
            waitingList.put(userId, productId);
            return "Added to waiting list";
        }
    }
}

// 3️⃣ DNS Cache with TTL
class DNSCache {

    class Entry {
        String ip;
        long expiry;

        Entry(String ip, long ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl;
        }
    }

    private HashMap<String, Entry> cache = new HashMap<>();
    private Random random = new Random();

    public String resolve(String domain) {

        if (cache.containsKey(domain)) {
            Entry e = cache.get(domain);

            if (System.currentTimeMillis() < e.expiry) {
                return "Cache HIT → " + e.ip;
            }
        }

        String newIp = "172.217.14." + random.nextInt(255);
        cache.put(domain, new Entry(newIp, 300000));

        return "Cache MISS → " + newIp;
    }
}

// 4️⃣ Plagiarism Detection System
class PlagiarismDetector {

    HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    public void addDocument(String docId, String text) {

        String[] words = text.split(" ");

        for (int i = 0; i < words.length - 4; i++) {

            String gram = words[i] + " " + words[i + 1] + " " + words[i + 2] + " " + words[i + 3] + " " + words[i + 4];

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    public int checkSimilarity(String text) {

        String[] words = text.split(" ");
        int matches = 0;

        for (int i = 0; i < words.length - 4; i++) {

            String gram = words[i] + " " + words[i + 1] + " " + words[i + 2] + " " + words[i + 3] + " " + words[i + 4];

            if (ngramIndex.containsKey(gram))
                matches++;
        }

        return matches;
    }
}

// 5️⃣ Real-Time Website Analytics
class AnalyticsDashboard {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> sources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        sources.put(source, sources.getOrDefault(source, 0) + 1);
    }

    public void getTopPages() {

        for (String page : pageViews.keySet())
            System.out.println(page + " views: " + pageViews.get(page));
    }
}

// 6️⃣ API Rate Limiter
class RateLimiter {

    class Bucket {
        int tokens;

        Bucket(int limit) {
            tokens = limit;
        }
    }

    HashMap<String, Bucket> clients = new HashMap<>();
    int limit = 1000;

    public boolean checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new Bucket(limit));
        Bucket b = clients.get(clientId);

        if (b.tokens > 0) {
            b.tokens--;
            return true;
        }

        return false;
    }
}

// 7️⃣ Search Autocomplete
class AutocompleteSystem {

    HashMap<String, Integer> queries = new HashMap<>();

    public void addQuery(String q) {
        queries.put(q, queries.getOrDefault(q, 0) + 1);
    }

    public List<String> search(String prefix) {

        List<String> result = new ArrayList<>();

        for (String q : queries.keySet()) {
            if (q.startsWith(prefix))
                result.add(q);
        }

        return result;
    }
}

// 8️⃣ Parking Lot
class ParkingLot {

    String[] spots = new String[500];

    int hash(String plate) {
        return Math.abs(plate.hashCode()) % spots.length;
    }

    public int parkVehicle(String plate) {

        int index = hash(plate);

        while (spots[index] != null) {
            index = (index + 1) % spots.length;
        }

        spots[index] = plate;
        return index;
    }

    public void exitVehicle(String plate) {

        for (int i = 0; i < spots.length; i++) {

            if (spots[i] != null && spots[i].equals(plate)) {
                spots[i] = null;
                break;
            }
        }
    }
}

// 9️⃣ Two-Sum Fraud Detection
class TwoSumDetector {

    public void findTwoSum(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int complement = target - nums[i];

            if (map.containsKey(complement))
                System.out.println("Pair: " + nums[i] + " " + complement);

            map.put(nums[i], i);
        }
    }
}

// 🔟 Multi-Level Cache
class MultiLevelCache {

    LinkedHashMap<String, String> L1 = new LinkedHashMap<>();
    HashMap<String, String> L2 = new HashMap<>();

    public String getVideo(String id) {

        if (L1.containsKey(id))
            return "L1 HIT";

        if (L2.containsKey(id)) {
            L1.put(id, L2.get(id));
            return "L2 HIT → Promoted to L1";
        }

        L2.put(id, "VideoData");
        return "L3 DB HIT";
    }
}

// Main Class
public class Main {

    public static void main(String[] args) {

        UsernameChecker userSys = new UsernameChecker();
        userSys.register("john_doe", 1);
        System.out.println(userSys.checkAvailability("john_doe"));
        System.out.println(userSys.suggestAlternatives("john_doe"));

        InventoryManager inv = new InventoryManager();
        inv.addProduct("IPHONE15", 100);
        System.out.println(inv.purchaseItem("IPHONE15", 123));

        DNSCache dns = new DNSCache();
        System.out.println(dns.resolve("google.com"));

        PlagiarismDetector pd = new PlagiarismDetector();
        pd.addDocument("doc1", "this is a sample document for plagiarism detection system");

        AnalyticsDashboard ad = new AnalyticsDashboard();
        ad.processEvent("/home", "user1", "google");
        ad.processEvent("/home", "user2", "facebook");
        ad.getTopPages();

        RateLimiter rl = new RateLimiter();
        System.out.println(rl.checkRateLimit("client1"));

        AutocompleteSystem ac = new AutocompleteSystem();
        ac.addQuery("java tutorial");
        ac.addQuery("javascript course");
        System.out.println(ac.search("jav"));

        ParkingLot lot = new ParkingLot();
        int spot = lot.parkVehicle("ABC123");
        System.out.println("Parked at: " + spot);

        TwoSumDetector ts = new TwoSumDetector();
        ts.findTwoSum(new int[]{500, 300, 200}, 500);

        MultiLevelCache cache = new MultiLevelCache();
        System.out.println(cache.getVideo("video1"));
    }
}
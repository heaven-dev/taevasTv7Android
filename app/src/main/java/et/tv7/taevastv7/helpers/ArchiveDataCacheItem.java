package et.tv7.taevastv7.helpers;

import org.json.JSONArray;

import static et.tv7.taevastv7.helpers.Constants.CACHE_EXPIRATION_TIME;

public class ArchiveDataCacheItem {
    private JSONArray data = null;
    private long timestamp = 0;

    public ArchiveDataCacheItem(JSONArray data) {
        this.data = data;
        this.timestamp = et.tv7.taevastv7.helpers.Utils.getTimeInMilliseconds();
    }

    public JSONArray getData() {
        return data;
    }

    public boolean isCacheValid() {
        return et.tv7.taevastv7.helpers.Utils.getTimeInMilliseconds() < timestamp + CACHE_EXPIRATION_TIME;
    }

    public boolean isDataInIndex(int index) {
        return data != null && data.length() - 1 >= index;
    }
}

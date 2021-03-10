package et.tv7.taevastv7.interfaces;

/**
 * Epg data load interface.
 */
public interface EpgDataLoadedListener {
    void onEpgDataLoaded();
    void onEpgDataLoadError(String message);
    void onNoNetwork();
}

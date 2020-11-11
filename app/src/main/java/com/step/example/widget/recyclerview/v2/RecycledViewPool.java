package com.step.example.widget.recyclerview.v2;

import android.util.SparseArray;

import java.util.ArrayList;

public class RecycledViewPool {
    static class ScrapData {
      ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();
    }
    SparseArray<ScrapData> mScrap = new SparseArray<>();
    public void clear() {
        for (int i = 0; i < mScrap.size(); i++) {
            ScrapData data = mScrap.valueAt(i);
            data.mScrapHeap.clear();
        }
    }
    //打造一个回收池
    public RecycledViewPool() {
    }
    private ScrapData getScrapDataForType(int viewType) {
        ScrapData scrapData = mScrap.get(viewType);
        if (scrapData == null) {
            scrapData = new ScrapData();
            mScrap.put(viewType, scrapData);
        }
        return scrapData;
    }
    public ViewHolder getRecycledView(int viewType) {
        final ScrapData scrapData = mScrap.get(viewType);
        if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
            final ArrayList<ViewHolder> scrapHeap = scrapData.mScrapHeap;
            for (int i = scrapHeap.size() - 1; i >= 0; i--) {
                return scrapHeap.remove(i);
            }
        }
        return null;
    }
    public void putRecycledView(ViewHolder scrap, int viewType) {
        ArrayList<ViewHolder> scrapHeap = getScrapDataForType(viewType).mScrapHeap;
        scrapHeap.add(scrap);
    }
}

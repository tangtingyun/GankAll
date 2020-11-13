package com.step.example.widget.recyclerview.v2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView  extends ViewGroup {
    private static final String TAG = "david";
    private Adapter adapter;
    private VelocityTracker velocityTracker;
    private Flinger flinger;

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            recycler = new RecycledViewPool();
        }
        scrollY = 0;
        firstRow = 0;
        needRelayout = true;
        requestLayout();
    }

//y偏移量      内容偏移量
    private int scrollY;
//当前显示的View
    private List<ViewHolder> viewList;
    //当前滑动的y值
    private int currentY;
//行数
    private int rowCount;
//初始化
    private boolean needRelayout;
//    当前reclerView的宽度
    private int width;

    private int height;

    private int[] heights;
    RecycledViewPool recycler;
    //view的弟一行  是占内容的几行
    private int firstRow;
    //最小滑动距离
    private int touchSlop;
    private  int maximumVelocity;

    private  int minimumVelocity;
    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context context, AttributeSet attrs) {
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
//    点击    28 -40  滑动
        this.flinger = new Flinger(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.minimumVelocity = configuration.getScaledMinimumFlingVelocity();
    }

//onLayout  1     n
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null) {
//                    摆放子控件
                width = r - l;
                height = b - t;
                int left, top = 0, right, bottom;
//                第一行不是从0开始
                top = -scrollY;
                this.rowCount = adapter.getItemCount();
                heights = new int[rowCount];
                for (int i=0;i<heights.length;i++) {
                    heights[i]=adapter.getHeight(i);
                }
//                rowCount  内容的item数量 1000           height 当前控件的高度
                for (int i=0;i<rowCount&&top<height;i++) {
                    bottom = top + heights[i];
                //生成View
                    ViewHolder viewHolder= makeAndStep(i, 0, top, width, bottom);
                    viewList.add(viewHolder);
                    top = bottom;
                }
            }
        }

    }

    private ViewHolder makeAndStep(int row, int left, int top, int right, int bottom) {
//        实例化一个有宽度  高度的View
        ViewHolder viewHolder = obtainView(row, right - left, bottom - top);
//        设置位置
        viewHolder.itemView.layout(left, top, right, bottom);
        return viewHolder;
    }

    private ViewHolder obtainView(int row, int width, int height) {
        int itemType=adapter.getItemViewType(row);
        //根据这个类型 返回相应View  （布局）
//        初始化的时候 取不到
        ViewHolder reclyView=recycler.getRecycledView(itemType);
        Log.i(TAG, "obtainView: "+reclyView==null?"是空的":"不是空的");
        if (reclyView == null) {
            reclyView=adapter.onCreateViewHolder(this,itemType);
        }
        adapter.onBindViewHolder(reclyView, row);
        //View不可能为空
//
        reclyView.setItemViewType(itemType);
//        测量
//VIEW 打tag   row    type
        reclyView.getItemView().measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        addView(reclyView.getItemView(),0);

        return reclyView ;
    }

    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

//拦截 滑动事件  预处理 事件的过程
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int y2 = Math.abs(currentY - (int) event.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
                break;
            }
        }
        return intercept;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_UP:{
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);

                int velocityY = (int) velocityTracker.getYVelocity();

                int initY = scrollY + sumArray(heights, 1, firstRow);
                int maxY = Math.max(0, sumArray(heights, 0, heights.length) - height);
//                判断是否开启 惯性滑动
                if (Math.abs( velocityY) > minimumVelocity) {
//                        线程  ---》自己看线程
                    flinger.start(0,initY,0,velocityY,0,maxY);
                }else {

                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }

                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
//                移动的距离   y方向
                int y2= (int) event.getRawY();
                //   diffX>0    往左划
                int diffY=currentY-y2;
                scrollBy(0, diffY);
                break;
            }

        }
        return super.onTouchEvent(event);
    }
//scrollBy
    @Override
    public void scrollBy(int x, int y) {
        scrollY+=y;
//     scrollY取值   0 ---- 屏幕 的高度   0---无限大   2
//修正一下  内容的总高度 是他的边界值
        scrollY = scrollBounds(scrollY, firstRow, heights, height);
        if (scrollY > 0) {
            //            往上滑
            while (heights[firstRow] < scrollY) {
//              remove  item完全移出去了  应该 1  不应该2
                if (!viewList.isEmpty()) {
                    removeView(viewList.remove(0));
                }
                scrollY -= heights[firstRow];
                firstRow++;
            }
            //            scrollY=0

            while (getFilledHeight()<height){
                int dataIndex = firstRow + viewList.size();
                ViewHolder view = obtainView(dataIndex , width,
                        heights[dataIndex]);
                viewList.add(viewList.size(),view);
            }

        } else if (scrollY < 0) {
            //            往下滑
            while (!viewList.isEmpty() && getFilledHeight() - heights[firstRow + viewList.size()-1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }

            while (0 > scrollY) {
                ViewHolder viewHolder = obtainView(firstRow - 1, width, heights[0]);
                viewList.add(0,viewHolder);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
        }
//        重新对一个子控件进行重新layout
        repositionViews();
    }

    private void repositionViews() {
        int left, top, right, bottom, i;
        top =  - scrollY;
        i = firstRow;
        for (ViewHolder viewHolder   : viewList) {
            bottom = top + heights[i++];
            viewHolder.itemView.layout(0, top, width, bottom);
            top = bottom;
        }
    }
    private int getFilledHeight() {
        return   sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private int scrollBounds(int scrollY, int firstRow, int sizes[], int viewSize) {
        if (scrollY > 0) {
            Log.i(TAG, " 上滑 scrollBounds: scrollY  " + scrollY + "  各项之和  " + sumArray(sizes, firstRow, sizes.length - firstRow) + "  receryView高度  " + viewSize);
            //            往上滑  bug +
            if (sumArray(sizes, firstRow, sizes.length - firstRow) - scrollY >viewSize ) {
                scrollY = scrollY;
            }else {
                scrollY = sumArray(sizes, firstRow, sizes.length - firstRow) - viewSize;
            }
        }else {
            //            往下滑  y  firstRow= 0    -
          scrollY = Math.max(scrollY, -sumArray(sizes,0,firstRow));  //=0
//            scrollY = Math.max(scrollY, 0);  //=
            Log.i(TAG, "下滑  scrollBounds: scrollY  " + scrollY + "  各项之和  " + (-sumArray(sizes,0,firstRow)) );
        }
        return scrollY;
    }
    public void removeView(ViewHolder viewHolder) {
        int typeView =   viewHolder.getItemViewType();
        recycler.putRecycledView(viewHolder,typeView);
        removeView(viewHolder.getItemView());
    }
    public interface Adapter<VH extends ViewHolder> {
        VH onCreateViewHolder(ViewGroup parent, int viewType);
        VH  onBindViewHolder(VH viewHodler, int position);
        //Item的类型
        int getItemViewType(int position);
        int getItemCount();
        public int getHeight(int index);
    }

    class Flinger implements Runnable {
        //        scrollBy   （移动的偏移量)  而不是速度
        private Scroller scroller;
        //
        private int initY;

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY){
            scroller.fling(initX, initY, initialVelocityX
                    , initialVelocityY, 0, maxX, 0, maxY);
            this.initY = initY;
            post(this);
        }
        Flinger(Context context) {
            scroller = new Scroller(context);

        }
        @Override
        public void run() {
            if (scroller.isFinished()) {
                return;
            }

            boolean more=scroller.computeScrollOffset();
//
            int y = scroller.getCurrY();
            int diffY = initY - y;
            if (diffY != 0) {
                scrollBy(0, diffY);
                initY = y;
            }
            if (more) {
                post(this);
            }
        }

        boolean isFinished() {
            return scroller.isFinished();
        }
    }
}
